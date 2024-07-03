package it.uniroma3.siw.siwfood.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siwfood.model.auth.Credential;

public interface CredentialRepository extends CrudRepository<Credential, Long> {

    public Credential findByUsername(String username);
}
