package com.projetospring.sistemaLogin;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.projetospring.sistemaLogin.models.Usuario;
import com.projetospring.sistemaLogin.repository.UserRep;
import com.projetospring.sistemaLogin.service.CookieService;
import com.projetospring.sistemaLogin.service.validations.ValidacaoUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@Controller
public class LogController {

    @Autowired
    private UserRep repositorioUser;
    @Autowired 
    ValidacaoUser validacao;
    // Responde ao método GET /login
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; // retorna a página de login
    }

    // Responde ao método GET /register
    @GetMapping("/register")
    public String cadastro() {
        return "register"; // retorna a página de registro
    }

    // Responde ao método GET /
    @GetMapping("/")
    public String sessao(HttpServletRequest request) {
        if (CookieService.getCookie(request, "UsuarioEmail") == null) {
            return "redirect:/login";
        }
        return "hero";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) throws UnsupportedEncodingException {
        CookieService.setCookie(response, "UsuarioEmail", "", 0);
        return "redirect:/login";
    }

    // Cadastro do usuário
    @PostMapping("/register")
    public String validar(@Valid @ModelAttribute Usuario user,BindingResult result, Model model){
        
        if(validacao.cadastroUser(user, result, model)){
            repositorioUser.save(user);
            return "login";
        }else{
            return "register";
        }

    }
    


    // Área que possui a validação do usuário
    @PostMapping("/login")
    public String loginUsuario(@Valid @ModelAttribute Usuario user, BindingResult result, Model model, HttpServletResponse response) throws UnsupportedEncodingException {

        if (result.hasErrors()) {
            return "login";
        }

        // Busca o usuário pelo email
        Usuario usuarioExiste = repositorioUser.findByEmail(user.getEmail());

        if(validacao.loginUser(user, usuarioExiste, model)){
            CookieService.setCookie(response, "UsuarioEmail", String.valueOf(usuarioExiste.getEmail()), 10000);
            return "redirect:/";
        }

        return "login";

        

    }

    
    
}
