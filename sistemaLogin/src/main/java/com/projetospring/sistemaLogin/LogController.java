package com.projetospring.sistemaLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projetospring.sistemaLogin.models.Usuario;
import com.projetospring.sistemaLogin.repository.UserRep;

import jakarta.validation.Valid;

@Controller
public class LogController {
    @Autowired
    private UserRep repositorioUser;
    @GetMapping("/login")//responde a esse caminho
    
    public String login(){
        return "login";//retorna a pagina de login
    }

    @GetMapping("/register")
    public String cadastro(){
        return "register";//retorna a pagina de registro
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)//responde a metodo post
    
    public String cadastroUser(@Valid @ModelAttribute Usuario user, BindingResult result){
        if(result.hasErrors()){
            System.err.println(result.getAllErrors());
            return "redirect:/register";
        }
        repositorioUser.save(user);
        return "redirect:/login";
    }
}
