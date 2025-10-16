package com.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class CookieService {
    public static void setCookie(HttpServletResponse resp, String key, String valor, int segundos) throws UnsupportedEncodingException{
        
            Cookie cookie = new Cookie(key, URLEncoder.encode(valor, "UTF-8")); // Criando um cookie passando a chave e o valor em formato UTF-8
            cookie.setMaxAge(segundos); // Tempo que o cookie ficará ativo
            resp.addCookie(cookie); // adiciona o cookie no corpo da resposta
            cookie.setPath("/"); // garante que o cookie seja enviado para todas as rotas do domínio

         
        
    }
    public static String getCookie(HttpServletRequest request, String key){
        
          if (request.getCookies() == null) return null;

            return Arrays.stream(request.getCookies())
                .filter(cookie -> key.equals(cookie.getName())) // filtra os cookies com a key indicada
                .findFirst() // acha o primeiro
                .map(cookie -> {
                    try {
                        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        System.out.println("ERRO ao decodificar cookie: " + e.getMessage());
                        return null;
                    }
                })
                .orElse(null);

    }
}
