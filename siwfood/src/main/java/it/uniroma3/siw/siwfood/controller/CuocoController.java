package it.uniroma3.siw.siwfood.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.siwfood.model.Cuoco;
import it.uniroma3.siw.siwfood.model.Images;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.model.auth.User;
import it.uniroma3.siw.siwfood.service.CuocoService;
import it.uniroma3.siw.siwfood.service.ImagesService;

@Controller
public class CuocoController extends GlobalController {

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private ImagesService immagineService;

    @GetMapping("/cuochi")
    public String getCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "cuochi.html";
    }

    @GetMapping("/cuochi/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }

    @GetMapping("/cuochi/search")
    public String getFormSearchCuoco() {
        return "formSearchCuoco.html";
    }

    @PostMapping("/cuochi/byNome")
    public String postCuochiByNome(@RequestParam String nome, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByNome(nome));
        return "cuochi.html";
    }

    @GetMapping("/admin/addCuoco")
    public String getFormNewCuoco(Model model) {
        if (!getCredential().isAdmin()) {
            return "redirect:/error";
        }
        model.addAttribute("cuoco", new Cuoco());
        return "formNewCuoco.html";
    }

    @PostMapping("/admin/addCuoco")
    public String postNewCuoco(@ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile immagine)
            throws IOException {

        if (!immagine.isEmpty()) {
            Images img = new Images();
            img.setFileName(immagine.getOriginalFilename());
            img.setImageData(immagine.getBytes());
            if (cuoco.getImmagini() == null) {
                cuoco.setImmagini(new ArrayList<>());
            }
            cuoco.getImmagini().add(img);
            immagineService.save(img);
        }
        cuocoService.saveCuoco(cuoco);
        return "redirect:/cuochi/" + cuoco.getId();
    }

    @GetMapping("/admin/editCuoco/{id}")
    public String getFormEditCuoco(@PathVariable("id") Long id, Model model) {
        User user = getCredential().getUser();
        if (!getCredential().isAdmin()
                && cuocoService.findbyNomeCognome(user.getName(), user.getSurname()).getId() != id) {
            return "redirect:/error";
        }
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "formEditCuoco.html";
    }

    @PostMapping("/admin/editCuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id, @ModelAttribute Cuoco cuoco,
            @RequestParam("immagine") MultipartFile immagine)
            throws IOException {
        cuoco.setId(id);

        if (!immagine.isEmpty()) {
            Images img = new Images();
            img.setFileName(immagine.getOriginalFilename());
            img.setImageData(immagine.getBytes());
            if (cuoco.getImmagini().isEmpty()) {
                cuoco.getImmagini().add(img);
            } else {
                cuoco.getImmagini().clear();
                cuoco.getImmagini().add(img);
            }
            immagineService.save(img);
        }

        this.cuocoService.saveCuoco(cuoco);
        return "redirect:/cuochi/" + cuoco.getId();
    }

    @GetMapping("/admin/deleteCuoco/{id}")
    public String deleteCuocoById(@PathVariable("id") Long id) {
        if (!getCredential().isAdmin()) {
            return "redirect:/error";
        }
        cuocoService.deleteCuoco(id);
        return "redirect:/cuochi";
    }

}
