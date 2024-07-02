package it.uniroma3.siw.siwfood.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siwfood.model.Prenotazione;
import it.uniroma3.siw.siwfood.model.Struttura;
import it.uniroma3.siw.siwfood.repository.StrutturaRepository;
import it.uniroma3.siw.siwfood.service.HostService;
import it.uniroma3.siw.siwfood.service.PrenotazioneService;
import it.uniroma3.siw.siwfood.service.StrutturaService;

@Controller
public class StrutturaController {

	@Autowired
	private StrutturaService strutturaService;

	@Autowired
	private HostService hostService;

	@Autowired
	private StrutturaRepository strutturaRepository;

	@Autowired
	private GlobalController globalController;

	@Autowired
	private PrenotazioneService prenotazioneService;

	@GetMapping("/struttura/{id}")
	public String getStruttura(@PathVariable("id") Long id, Model model) {
		model.addAttribute("struttura", this.strutturaService.findById(id));
		return "struttura.html";
	}

	@GetMapping("/struttura") // SENZA S
	public String getStrutture(Model model) {
		model.addAttribute("strutture", this.strutturaService.findAll());
		return "strutture.html";
	}

	@GetMapping("/formNewStruttura")
	public String formNewstruttura(Model model) {
		model.addAttribute("struttura", new Struttura());
		return "formNewStruttura.html";
	}

	@PostMapping("/struttura")
	public String newStruttura(@ModelAttribute("struttura") Struttura struttura, Model model) {
		if (!strutturaRepository.existsByNameAndCity(struttura.getName(), struttura.getCity())) {
			String name = globalController.getCredential().get().getUser().getName();
			String surname = globalController.getCredential().get().getUser().getSurname();
			struttura.setHost(hostService.findByNameAndSurname(name, surname));
			this.strutturaService.save(struttura);
			model.addAttribute("struttura", struttura);
			return "redirect:/struttura/" + struttura.getId();
		} else {
			model.addAttribute("messaggioErrore", "Questo film esiste già");
			return "formNewstruttura.html";
		}
	}

	@GetMapping("/formSearchStrutture")
	public String formSearchStrutture() {
		return "formSearchStrutture.html";
	}

	@PostMapping("/searchStrutture")
	public String searchStrutture(Model model, @RequestParam String city) {
		model.addAttribute("strutture", this.strutturaService.findByCity(city));
		return "foundStrutture.html";
	}

	@GetMapping("/struttura/{id}/prenota")
	public String prenotaStruttura(@PathVariable("id") Long id, Model model) {
		Struttura struttura = this.strutturaService.findById(id);
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("struttura", struttura);
		return "formPrenotaStruttura.html";
	}

	@PostMapping("/struttura/{id}/prenota")
	public String prenotaStruttura(@PathVariable("id") Long id,
			@ModelAttribute("prenotazione") Prenotazione prenotazione, Model model) {
		Struttura struttura = this.strutturaService.findById(id);
		prenotazione.setStruttura(struttura);
		prenotazione.setNomeCasa(struttura.getName());
		prenotazione.setDataPrenotazione(LocalDate.now());
		prenotazione = prenotazioneService.save(prenotazione);
		model.addAttribute("prenotazione", prenotazione);
		// struttura.addPrenotazione(prenotazione);

		return "redirect:/struttura/" + struttura.getId() + "/prenotazione/" + prenotazione.getId();

	}

	@GetMapping("/struttura/{strutturaId}/prenotazione/{prenotazioneId}")
	public String visualizzaPrenotazione(@PathVariable("strutturaId") Long strutturaId,
			@PathVariable("prenotazioneId") Long prenotazioneId, Model model) {
		Prenotazione prenotazione = this.prenotazioneService.findById(prenotazioneId);
		model.addAttribute("prenotazione", prenotazione);
		return "prenotazioneStruttura.html";
	}

}
