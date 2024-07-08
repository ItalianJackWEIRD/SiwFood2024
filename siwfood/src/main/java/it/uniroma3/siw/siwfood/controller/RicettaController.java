package it.uniroma3.siw.siwfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.model.auth.User;
import it.uniroma3.siw.siwfood.service.CuocoService;
import it.uniroma3.siw.siwfood.service.IngredienteService;
import it.uniroma3.siw.siwfood.service.RicettaService;

@Controller
public class RicettaController extends GlobalController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private CuocoService cuocoService;

    @GetMapping("/ricette")
    public String getRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "ricette.html";
    }

    @GetMapping("/ricette/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "ricetta.html";
    }

    @GetMapping("/ricette/search")
    public String getFormSearchRicetta() {
        return "formSearchRicetta.html";
    }

    @PostMapping("/ricette/byNome")
    public String postRicetteByNome(@RequestParam String nome, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByNome(nome));
        return "ricette.html";
    }

    @PostMapping("/ricette/byIngrediente")
    public String postRicetteByIngrediente(@RequestParam String ingr, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByIngredienteNome(ingr));
        return "ricette.html";
    }

    @GetMapping("/admin/addRicetta/{cuoco_id}")
    public String getFormNewRicetta(@PathVariable("cuoco_id") Long id, Model model) {
        User user = getCredential().getUser();
        if (!getCredential().isAdmin()
                && cuocoService.findbyNomeCognome(user.getName(), user.getSurname()).getId() != id) {
            return "redirect:/error";
        }
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        model.addAttribute("ricetta", new Ricetta());
        return "formNewRicetta.html";
    }

    @PostMapping("/admin/addRicetta/{cuoco_id}")
    public String postNewRicetta(@PathVariable("cuoco_id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setCuoco(this.cuocoService.findById(id));
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/cuochi/" + id;
    }

    @GetMapping("/admin/editRicetta/{id}")
    public String getFormEditRicetta(@PathVariable("id") Long id, Model model) {
        Ricetta ricetta = this.ricettaService.findById(id);
        User user = getCredential().getUser();
        if (!getCredential().isAdmin()
                && cuocoService.findbyNomeCognome(user.getName(), user.getSurname()).getId() != ricetta.getCuoco()
                        .getId()) {
            return "redirect:/error";
        }
        model.addAttribute("ricetta", ricetta);
        model.addAttribute("ingredienti", this.ricettaService.findById(id).getIngredienti());
        model.addAttribute("nuovoIngrediente", new Ingrediente());
        return "formEditRicetta.html";
    }

    @PostMapping("/admin/editRicetta/{id}")
    public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute Ricetta ricetta,
            @ModelAttribute List<Ingrediente> ingredienti) {
        ricetta.setId(id);
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }

    @GetMapping("/admin/deleteRicetta/{id}")
    public String deleteRicetteById(@PathVariable("id") Long id) {
        Ricetta ricetta = this.ricettaService.findById(id);
        User user = getCredential().getUser();
        if (!getCredential().isAdmin()
                && cuocoService.findbyNomeCognome(user.getName(), user.getSurname()).getId() != ricetta.getCuoco()
                        .getId())
            return "redirect:/error";
        this.ricettaService.deleteRicetta(id);
        return "redirect:/ricette";
    }

    @PostMapping("/admin/aggiungiIngrediente/{id}")
    public String aggiungiIngrediente(@PathVariable("id") Long id, @ModelAttribute Ingrediente ingrediente) {
        Ricetta ricetta = ricettaService.findById(id);
        ingrediente.setRicetta(ricetta);
        this.ingredienteService.saveIngrediente(ingrediente);
        return "redirect:/admin/editRicetta/" + id;
    }

    @GetMapping("/admin/eliminaIngrediente/{id}")
    public String eliminaIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ingredienteService.findById(id));
        Long ricettaId = this.ingredienteService.findById(id).getRicetta().getId();
        this.ingredienteService.deleteIngrediente(id);
        return "redirect:/admin/editRicetta/" + ricettaId;
    }

}
