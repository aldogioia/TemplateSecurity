package org.aldogioia.templatesecurity.security.authentication;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.entities.User;
import org.aldogioia.templatesecurity.data.enumerators.TokenType;
import org.aldogioia.templatesecurity.security.exception.customException.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtHandler {
    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        return createToken(user, 15, ChronoUnit.MINUTES, TokenType.ACCESS);
    }

    public String generateRefreshToken(User user) {
        return createToken(user, 1, ChronoUnit.MONTHS, TokenType.REFRESH);
    }

    private String createToken(User user, long amountToAdd, ChronoUnit unit, TokenType type) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getPhoneNumber())
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(issuedAt.plus(amountToAdd, unit)))
                .claim("role", user.getRole().name())
                .claim("type", type)
                .build();

        try{
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(new MACSigner(secret.getBytes()));
            return type.name().equals(TokenType.ACCESS.name()) ? "Bearer " + signedJWT.serialize() : signedJWT.serialize();
        } catch (Exception e) {
            throw new TokenException("Errore durante la generazione del " + type.name().toLowerCase() + " token");
        }
    }

    public boolean isValidAccessToken(String token) {
        return isValidToken(token, TokenType.ACCESS);
    }
    public boolean isValidRefreshToken(String token) {
        return isValidToken(token, TokenType.REFRESH);
    }

    private Boolean isValidToken(String token, TokenType type) {
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (!signedJWT.verify(new MACVerifier(secret.getBytes()))) return false;

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            Instant expirationTime = claimsSet.getExpirationTime().toInstant();
            String typeInClaims = claimsSet.getStringClaim("type");

            return type.name().equals(typeInClaims) && Instant.now().isBefore(expirationTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccessToken(HttpServletRequest request) {
        return getJwtFromRequest(request, TokenType.ACCESS);
    }

    public String getRefreshToken(HttpServletRequest request) {
        return getJwtFromRequest(request, TokenType.REFRESH);
    }

    public String getJwtFromRequest(HttpServletRequest request, TokenType token) {
        String starts = "";
        String header = "";

        if (token == TokenType.ACCESS ) {
            header = request.getHeader(HttpHeaders.AUTHORIZATION);
            starts = "Bearer ";
        }
        else if (token == TokenType.REFRESH) {
            header = request.getHeader("X-Refresh-Token");
        }

        if (header != null && header.startsWith(starts)) {
            return header.replace(starts, "");
        }

        return "invalid";
    }

    public String getPhoneNumberFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new TokenException("Token non valido");
        }
    }

    public static Date getExpirationTime(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (Exception e) {
            throw new TokenException("Token non valido");
        }
    }
}
