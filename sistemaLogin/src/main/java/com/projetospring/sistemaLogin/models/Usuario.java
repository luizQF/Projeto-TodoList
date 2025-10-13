package com.projetospring.sistemaLogin.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
@Entity
public class Usuario {
    
    @Id//Define o id como PK
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String senha;
    @NotEmpty
    private String email;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


}
