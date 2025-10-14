package com.projetospring.sistemaLogin.repository;

import org.springframework.data.repository.CrudRepository;

import com.projetospring.sistemaLogin.models.Usuario;


public interface UserRep extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);//encontrar o usuario pelo email
}
