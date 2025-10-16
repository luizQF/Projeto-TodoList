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
import com.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LogController {

    @Autowired
    private UserRep repositorioUser;

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
        return "index";
    }


    // Cadastro do usuário
    @PostMapping("/register")
    public String cadastroUser(@Valid @ModelAttribute Usuario user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            System.err.println(result.getErrorCount());
            return "register";
        }

        // Verifica se o email já existe no repositório
        if (repositorioUser.findByEmail(user.getEmail()) != null) {
            model.addAttribute("errorEmail", "E-mail já cadastrado no banco de dados");
            return "register";
        }

        repositorioUser.save(user); // salva o usuário
        return "redirect:/login";
    }

    // Área que possui a validação do usuário
    @PostMapping("/login")
    public String loginUsuario(@Valid @ModelAttribute Usuario user, BindingResult result, Model model,
            HttpServletResponse response) throws UnsupportedEncodingException {

        if (result.hasErrors()) {
            return "login";
        }

        // Busca o usuário pelo email
        Usuario usuarioExiste = repositorioUser.findByEmail(user.getEmail());

        if (usuarioExiste == null) {
            model.addAttribute("errorLogin", "E-mail não cadastrado");
            return "login";
        }

        if (!usuarioExiste.getSenha().equals(user.getSenha())) {
            model.addAttribute("errorLogin", "Senha incorreta");
            return "login";
        }

        CookieService.setCookie(response, "UsuarioEmail", String.valueOf(usuarioExiste.getEmail()), 10000);
        return "redirect:/";

    }
}
