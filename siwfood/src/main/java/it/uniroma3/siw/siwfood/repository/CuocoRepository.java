package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwfood.model.Cuoco;

@Repository
public interface CuocoRepository extends CrudRepository<Cuoco, Long> {

    // ordinati rispetto al cognome
    public Iterable<Cuoco> findByNomeOrderByCognomeAsc(String nome);

    public Cuoco findByNomeAndCognome(String nome, String cognome);

    public boolean existsByNomeAndCognome(String nome, String cognome);

}
