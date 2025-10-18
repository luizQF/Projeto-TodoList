package com.projetospring.sistemaLogin.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.projetospring.sistemaLogin.models.Usuario;
import com.projetospring.sistemaLogin.repository.UserRep;

import jakarta.validation.Valid;
@Service
public class ValidacaoUser {

    @Autowired
    private UserRep repositorioUser;

    public boolean cadastroUser(@Valid @ModelAttribute Usuario user, BindingResult result, Model model) {
        

        if (result.hasErrors()) {
            System.err.println(result.getErrorCount());
            return false;
        }

        // Verifica se o email já existe no repositório
        if (repositorioUser.findByEmail(user.getEmail()) != null) {
            model.addAttribute("errorEmail", "E-mail já cadastrado no banco de dados");
            return false;
        }

        return true;

        
    }

    public boolean loginUser(Usuario user, Usuario usuarioExiste, Model model){

        if (usuarioExiste == null) {
            model.addAttribute("errorLogin", "E-mail não cadastrado");
            return false;
        }

        if (!usuarioExiste.getSenha().equals(user.getSenha())) {
            model.addAttribute("errorLogin", "Senha incorreta");
            return false;
        }

        return true;
    }
}
