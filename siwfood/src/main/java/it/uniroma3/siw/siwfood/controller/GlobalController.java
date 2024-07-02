package it.uniroma3.siw.siwfood.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.siwfood.model.auth.Credential;
import it.uniroma3.siw.siwfood.service.CredentialService;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private CredentialService credentialsService;

    @ModelAttribute("userDetails")
    public UserDetails getUser() {
        UserDetails user = null;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }

    @ModelAttribute("credenziali")
    public Optional<Credential> getCredential() {
        UserDetails user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Credential> credentials = credentialsService.getCredentialByUsername(user.getUsername());
            return credentials;
        }
        return null;
    }
}