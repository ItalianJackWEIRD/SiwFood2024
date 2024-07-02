package it.uniroma3.siw.siwfood.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.Cuoco;
import it.uniroma3.siw.siwfood.repository.CuocoRepository;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;
    

    public Cuoco findById(Long id){
        return cuocoRepository.findById(id).get();
    }

    public Iterable<Cuoco> findAll(){
        return cuocoRepository.findAll();
    }

    public Iterable<Cuoco> findByNome(String nome){
        return cuocoRepository.findByNomeOrderByCognomeAsc(nome);
    }

    public Cuoco saveCuoco (Cuoco cuoco){
        return cuocoRepository.save(cuoco);
    }


    public void deleteCuoco (Long id){
        cuocoRepository.deleteById(id);
    }

}
