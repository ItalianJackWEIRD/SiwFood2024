package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwfood.model.Cuoco;
import it.uniroma3.siw.siwfood.model.Ricetta;

@Repository
public interface RicettaRepository extends CrudRepository<Ricetta, Long> {

    public Iterable<Ricetta> findByNome(String nome);

    public boolean existsByNomeAndCuoco(String nome, Cuoco cuoco);

    // Metodo per trovare le ricette che contengono un determinato ingrediente
    @Query("SELECT r FROM Ricetta r JOIN r.ingredienti i WHERE i.nome = :nomeIngrediente")
    public Iterable<Ricetta> findByIngredienteNome(@Param("nomeIngrediente") String nomeIngrediente);
}