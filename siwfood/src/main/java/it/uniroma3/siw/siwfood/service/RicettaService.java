package it.uniroma3.siw.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.Cuoco;
import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.repository.RicettaRepository;

@Service
public class RicettaService {

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private IngredienteService ingredienteService;

    public Ricetta findById(Long id) {
        return this.ricettaRepository.findById(id).orElse(null);
    }

    public Iterable<Ricetta> findAll() {
        return this.ricettaRepository.findAll();
    }

    public Iterable<Ricetta> findByNome(String nome) {
        return this.ricettaRepository.findByNome(nome);
    }

    public Iterable<Ricetta> findByIngredienteNome(String nomeIngrediente) {
        return this.findByIngredienteNome(nomeIngrediente);
    }

    public Ricetta saveRicetta(Ricetta ricetta) {
        return this.ricettaRepository.save(ricetta);
    }

    public void deleteRicetta(Long id) {
        this.ricettaRepository.deleteById(id);
    }

    public void saveIngredienteToRicetta(Long idRicetta, Long idIngr, String nome, String quantita) {

        Ricetta ricetta = this.findById(idRicetta);
        // Ingrediente ingrediente = this.ingredienteService.findById(idIngr);

        Ingrediente ingrediente = new Ingrediente(nome, quantita, ricetta);

        if (ricetta.getIngredienti().contains(ingrediente)) {
            throw new RuntimeException("ingrediente già presente nella ricetta");
        }

        ricetta.getIngredienti().add(ingrediente);
        this.ingredienteService.saveIngrediente(ingrediente);
        this.saveRicetta(ricetta);

    }

    public void saveCuocoToRicetta(Long idRicetta, Long idCuoco) {

        Ricetta ricetta = this.findById(idRicetta);
        Cuoco cuoco = this.cuocoService.findById(idCuoco);

        ricetta.setCuoco(cuoco);
        cuoco.getRicette().add(ricetta);
        this.cuocoService.saveCuoco(cuoco);
        this.saveRicetta(ricetta);

    }

}
