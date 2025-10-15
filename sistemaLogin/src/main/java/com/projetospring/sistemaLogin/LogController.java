package com.projetospring.sistemaLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.projetospring.sistemaLogin.models.Usuario;
import com.projetospring.sistemaLogin.repository.UserRep;

import jakarta.validation.Valid;



@Controller
public class LogController {
    @Autowired

    //responde ao metodo get
    private UserRep repositorioUser;
    @GetMapping("/login")//responde a esse caminho

    public String login(Model model){
        model.addAttribute("usuario", new Usuario());
        return "login";//retorna a pagina de login
    }

    @GetMapping("/register")
    public String cadastro(){
        return "register";//retorna a pagina de registro
    }

    @GetMapping("/")
    public String sessao() {
        return "index";
    }
    



    //responde a metodo post

    @PostMapping("/register")

    public String cadastroUser(@Valid @ModelAttribute Usuario user, BindingResult result, Model model){
        if(result.hasErrors()){
            System.err.println(result.getErrorCount());
            return "register";
        }
        if(repositorioUser.findByEmail(user.getEmail()) != null){//verificando se o email do usuario que fez a requisição ja existe no repositorio
            model.addAttribute("errorEmail", "E-mail já cadastrado no banco de dados");//modelo para o thymeleaf (errorEmail serve como chave para o atributo)
            return "register";
        }
        repositorioUser.save(user);//salvando o repositorio
        return "redirect:/login";
    }

    @PostMapping("/login")

    public String loginUsuario(@Valid @ModelAttribute Usuario user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "login";
        }

        Usuario usuarioExiste = repositorioUser.findByEmail(user.getEmail());


        if(usuarioExiste == null){
            model.addAttribute("errorLogin", "E-mail não cadastrado");
            return "login";
        }
        if(!usuarioExiste.getSenha().equals(user.getSenha())){
            model.addAttribute("errorLogin", "Senha incorreta");
            return "login";
        } 

        return "redirect:/index";
    }
}
