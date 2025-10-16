package com.service.autenticator; //Intercepta o acesso da pagina principal sem uma sessão válida

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws IOException{
        if(CookieService.getCookie(request, "UsuarioID") != null){
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
