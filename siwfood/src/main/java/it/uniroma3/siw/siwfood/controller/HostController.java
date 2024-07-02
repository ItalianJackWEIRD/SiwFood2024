package it.uniroma3.siw.siwfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwfood.model.Host;
import it.uniroma3.siw.siwfood.service.HostService;

@Controller
public class HostController {

	@Autowired
	private HostService hostService;

	@GetMapping("/host/{id}")
	public String getHost(@PathVariable("id") Long id, Model model) {
		model.addAttribute("host", this.hostService.findById(id));
		return "host.html";
	}

	@GetMapping("/host") // SENZA S
	public String getHosts(Model model) {
		model.addAttribute("hosts", this.hostService.findAll());
		return "hosts.html";
	}

	@GetMapping("/formNewHost")
	public String formNewHost(Model model) {
		model.addAttribute("host", new Host());
		return "formNewHost.html";
	}

	@PostMapping("/host")
	public String newHost(@ModelAttribute("host") Host host, Model model) {
		this.hostService.save(host);
		model.addAttribute("host", host);
		return "redirect:/host/" + host.getId();
	}
}
