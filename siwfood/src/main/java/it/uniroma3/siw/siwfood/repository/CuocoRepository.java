package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwfood.model.Cuoco;
import java.time.LocalDate;

@Repository
public interface CuocoRepository extends CrudRepository<Cuoco, Long> {

    // ordinati rispetto al cognome
    public Iterable<Cuoco> findByNomeOrderByCognomeAsc(String nome);

    // tutti i cuochi nati dopo una certa data
    public Iterable<Cuoco> findByDataNascitaAfter(LocalDate dataNascita);

    public boolean existsByNomeAndCognome(String nome, String cognome);

}