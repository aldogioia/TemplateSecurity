package org.aldogioia.templatesecurity.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtEntryPoint implements AuthenticationEntryPoint {
    /**
     * Gestisce le richieste non autorizzate impostando lo status HTTP 401
     * e restituendo un messaggio di errore.
     *
     * @param request        la richiesta HTTP
     * @param response       la risposta HTTP
     * @param authException  l'eccezione di autenticazione sollevata
     * @throws IOException   in caso di errore di scrittura sulla risposta
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Authentication token is missing or invalid");
        response.getWriter().flush();
    }
}
