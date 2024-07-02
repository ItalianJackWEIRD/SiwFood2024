package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwfood.model.Ingrediente;

@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

    public boolean existsByNome(String nome);

}
