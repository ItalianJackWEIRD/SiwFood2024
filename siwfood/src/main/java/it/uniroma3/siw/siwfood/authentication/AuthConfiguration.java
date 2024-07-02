package it.uniroma3.siw.siwfood.authentication;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credential WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credential WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF se necessario, o configuralo come richiesto
                .cors(cors -> cors.disable()) // Disabilita CORS se necessario, o configuralo come richiesto
                // AUTORIZZAZIONE: qui definiamo chi può accedere a cosa
                .authorizeHttpRequests(authorize -> authorize
                        // chiunque (autenticato o no) può accedere alle pagine index, login, register,
                        // ai css e alle immagini
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/images/**",
                                "favicon.ico", "/struttura", "/struttura/**", "/strutture",
                                "/formSearchStrutture", "/foundStrutture", "/host", "/hosts", "/host/**", "/error")
                        .permitAll()
                        // chiunque (autenticato o no) può mandare richieste POST al punto di accesso
                        // per login e register
                        .requestMatchers(HttpMethod.POST, "/register", "/login", "/struttura",
                                "/struttura/**", "/strutture", "/formSearchStrutture", "/foundStrutture", "/host",
                                "/hosts", "/host/**")
                        .permitAll()
                        // solo gli utenti autenticati con ruolo HOST possono accedere a risorse con
                        // path /formNewStruttura
                        .requestMatchers(HttpMethod.GET, "/formNewStruttura").hasAnyAuthority("HOST")
                        .requestMatchers(HttpMethod.POST, "/formNewStruttura").hasAnyAuthority("HOST")
                        // tutti gli utenti autenticati possono accere alle pag
                        .anyRequest().authenticated())

                // LOGIN: qui definiamo come è gestita l'autenticazione
                // usiamo il protocollo formlogin
                .formLogin(formLogin -> formLogin
                        // la pagina di login si trova a /login
                        .loginPage("/login")
                        .permitAll()
                        // se il login ha successo, si viene rediretti al path /default
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true"))

                // LOGOUT: qui definiamo il logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll());

        return httpSecurity.build();
    }
}