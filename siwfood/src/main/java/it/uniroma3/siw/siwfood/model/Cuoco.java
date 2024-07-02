package it.uniroma3.siw.siwfood.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Cuoco {

    /* ATTRIBUTI CUOCO */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;

    @PastOrPresent
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascita;

    @ElementCollection
    private List<Images> immagini;

    @OneToMany(mappedBy = "cuoco", cascade = CascadeType.ALL)
    private List<Ricetta> ricette;
    /* FINE ATTRIBUTI */

    /* COSTRUTTORI */
    public Cuoco() {

    }

    public Cuoco(String nome, String cognome, List<Images> immagini, LocalDate dataNascita, List<Ricetta> ricette) {
        this.nome = nome;
        this.cognome = cognome;
        this.immagini = immagini;
        this.dataNascita = dataNascita;
        this.ricette = ricette;
    }
    /* FINE COSTRUTTORI */

    /* EQUALS & HASHCODE */
    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, dataNascita);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cuoco other = (Cuoco) obj;
        return Objects.equals(nome, other.nome) &&
                Objects.equals(cognome, other.cognome) &&
                Objects.equals(dataNascita, other.dataNascita);
    }

    @Override
    public String toString() {
        return this.nome + " " + this.cognome;
    }
    /* FINE EQUALS & HASHCODE */

    /* METODI PER LE IMMAGINI */
    public Images getFirstImmagine() {
        return this.immagini.get(0);
    }

    public List<Images> getImmaginiDopoFirst() {
        try {
            return this.immagini.subList(1, this.immagini.size());
        } catch (Exception e) {
            return null;
        }
    }
    /* METODI PER LE IMMAGINI */

    /* GETTER & SETTER */
    // id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // cognome
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    // dataNascita
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    // ricette
    public void setRicette(List<Ricetta> ricette) {
        this.ricette = ricette;
    }

    public List<Ricetta> getRicette() {
        return this.ricette;
    }

    // immagine
    public List<Images> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<Images> immagine) {
        this.immagini = immagine;
    }
    /* FINE GETTER & SETTER */

}