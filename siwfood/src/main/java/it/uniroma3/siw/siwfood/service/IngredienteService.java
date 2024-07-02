package it.uniroma3.siw.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.repository.IngredienteRepository;

@Service
public class IngredienteService {
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Ingrediente findById(Long id){
        return this.ingredienteRepository.findById(id).orElse(null);
    }

    public Iterable<Ingrediente> findAll(){
        return this.ingredienteRepository.findAll();
    }

    public Ingrediente saveIngrediente(Ingrediente Ingrediente){
        return this.ingredienteRepository.save(Ingrediente);
    }

    public void deleteIngrediente(Long id){
        this.ingredienteRepository.deleteById(id);
    }
}
