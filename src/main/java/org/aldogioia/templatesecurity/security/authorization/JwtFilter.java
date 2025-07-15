package org.aldogioia.templatesecurity.security.authorization;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.enumerators.TokenType;
import org.aldogioia.templatesecurity.security.CustomUserDetails;
import org.aldogioia.templatesecurity.security.authentication.JwtHandler;
import org.aldogioia.templatesecurity.service.interfaces.BlacklistService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@NonNullApi
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtHandler jwtHandler;
    private final BlacklistService blacklistService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtHandler.getJwtFromRequest(request, TokenType.ACCESS);
        if (!jwt.equals("invalid") && jwtHandler.isValidAccessToken(jwt) && blacklistService.isTokenBlacklisted(jwt)) {
            String phoneNumberFromToken = jwtHandler.getPhoneNumberFromToken(jwt);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(phoneNumberFromToken);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
