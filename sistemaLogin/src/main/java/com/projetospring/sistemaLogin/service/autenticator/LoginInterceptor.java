package com.projetospring.sistemaLogin.service.autenticator; //Intercepta o acesso da pagina principal sem uma sessão válida

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.projetospring.sistemaLogin.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws IOException{
        if(CookieService.getCookie(request, "UsuarioEmail") != null){
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
