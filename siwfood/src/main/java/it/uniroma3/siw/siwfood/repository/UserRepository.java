package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siwfood.model.auth.User;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByName(String name);
}
