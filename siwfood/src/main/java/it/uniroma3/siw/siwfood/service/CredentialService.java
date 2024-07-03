package it.uniroma3.siw.siwfood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.auth.Credential;
import it.uniroma3.siw.siwfood.repository.CredentialRepository;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Credential getCredentialById(Long id) {
        return this.credentialRepository.findById(id).get();
    }

    public Credential getCredentialByUsername(String username) {
        return this.credentialRepository.findByUsername(username);
    }

    public void save(Credential credenziali) {
        credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        this.credentialRepository.save(credenziali);
    }

    public void deleteCredentials(Credential credenziali) {
        this.credentialRepository.delete(credenziali);
    }

}
