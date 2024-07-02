package it.uniroma3.siw.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.Images;
import it.uniroma3.siw.siwfood.repository.ImagesRepository;
import jakarta.transaction.Transactional;

@Service
public class ImagesService {
    
    @Autowired
    private ImagesRepository ImagesRepository;

    public Images findById(Long id){
        return this.ImagesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Images Images){
        this.ImagesRepository.delete(Images);
    }

    @Transactional
    public Images save(Images Images){
        return this.ImagesRepository.save(Images);
    }
}
