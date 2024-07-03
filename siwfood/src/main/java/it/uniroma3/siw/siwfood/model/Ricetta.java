package it.uniroma3.siw.siwfood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ricetta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descrizione;

    @ElementCollection
    private List<Images> immagini = new ArrayList<Images>();;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL)
    private List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();

    @ManyToOne
    @JoinColumn(name = "cuoco_id")
    private Cuoco cuoco;

    public Ricetta() {

    }

    public Ricetta(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public Ricetta(String nome, String descr, List<Images> immagini, List<Ingrediente> ingr, Cuoco cuoco) {
        this.nome = nome;
        this.descrizione = descr;
        this.immagini = immagini;
        this.ingredienti = ingr;
        this.cuoco = cuoco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descrizione, ingredienti, cuoco);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ricetta other = (Ricetta) obj;
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        if (ingredienti == null) {
            if (other.ingredienti != null)
                return false;
        } else if (!ingredienti.equals(other.ingredienti))
            return false;
        if (cuoco == null) {
            if (other.cuoco != null)
                return false;
        } else if (!cuoco.equals(other.cuoco))
            return false;
        return true;
    }

    public Images getFirstImages() {
        return this.immagini.get(0);
    }

    public List<Images> getImmaginiDopoFirst() {
        try {
            return this.immagini.subList(1, this.immagini.size());
        } catch (Exception e) {
            return null;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void ListId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void ListNome(String titolo) {
        this.nome = titolo;
    }

    public List<Images> getImmagini() {
        return immagini;
    }

    public void ListImmagini(List<Images> immagini) {
        this.immagini = immagini;
    }

    public List<Ingrediente> getIngredienti() {
        return this.ingredienti;
    }

    public void ListIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public void setCuoco(Cuoco cuoco) {
        this.cuoco = cuoco;
    }

    public Cuoco getCuoco() {
        return cuoco;
    }

    public void ListCuoco(Cuoco autore) {
        this.cuoco = autore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void ListDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
