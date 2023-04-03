package com.nisum.demo.security;

import com.nisum.demo.services.UserAuthenticationDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${app.secret}")
    private String secret;

    @Value("${app.expiration}")
    private int expiration;

    public String generateJwtToken(final Authentication authentication) {
        final UserAuthenticationDetailsImpl userPrincipal = (UserAuthenticationDetailsImpl) authentication.getPrincipal();
        final Map data = new HashMap<>();
        data.put("email", userPrincipal.getEmail());
        data.put("roles", userPrincipal.getAuthorities());
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .addClaims(data)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromJwtToken(final String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(final String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (final Exception e) {
            logger.error("Token invalido: {}", e.getMessage());
        }

        return false;
    }

}