package it.uniroma3.siw.siwfood.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwfood.model.Host;
import it.uniroma3.siw.siwfood.model.auth.Credential;
import it.uniroma3.siw.siwfood.model.auth.User;
import it.uniroma3.siw.siwfood.service.CredentialService;
import it.uniroma3.siw.siwfood.service.HostService;
import it.uniroma3.siw.siwfood.service.UserService;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostService hostService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("host", new Host());
        model.addAttribute("credentials", new Credential());
        return "register.html";
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model) {
        return "login.html";
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication(); //diooooo

        return "index.html";
    }

    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Credential> credentials = credentialService.getCredentialByUsername(userDetails.getUsername());

        return "index.html";
    }

    @PostMapping(value = { "/register" })
    public String registerUser(@ModelAttribute("user") User user,
            BindingResult userBindingResult,

            @ModelAttribute("credential") Credential credentials,
            BindingResult credentialsBindingResult,
            Model model,

            @ModelAttribute("host") Host host) {

        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.save(user);
            credentials.setUser(user);
            host.setName(user.getName());
            host.setSurname(user.getSurname());
            host.setUrlImage(user.getUrlImage());
            hostService.save(host);
            // credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
            //
            credentials.setRuolo(Credential.UTENTE_HOST);
            credentialService.save(credentials);
            model.addAttribute("user", user);

            return "redirect:/";
        }
        return "register.html";
    }
}