package com.PrimeFaces.demo.security.jwt;

import com.PrimeFaces.demo.entity.SpringUser;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_KEY = "userid";
    private static final String secretAuthKey = "IToRe6jfqqV413NKDo3PufgS1rKr0wq8";
    private static final String secretResetKey = "G2klCXg181pUcrWFEmEGFyeYw6IjSfAL";

    public String generateToken(Authentication authentication) {

        SpringUser springUser = (SpringUser) authentication.getPrincipal();
        String authorities = springUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretAuthKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretAuthKey)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        String auth = claims.get(AUTHORITIES_KEY).toString();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(auth.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        SpringUser springUser = SpringUser.of(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(springUser, "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretAuthKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public boolean validateResetToken(String resetToken) {
        try {
            Jwts.parser().setSigningKey(secretResetKey).parseClaimsJws(resetToken);
            return true;
        } catch (SignatureException ex) {
            throw new RuntimeException("Invalid JWT signature"); //logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Invalid JWT token"); //logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Expired JWT token"); //logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("Unsupported JWT token"); //logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("JWT claims string is empty.");  //logger.error("JWT claims string is empty.");
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected token");  //logger.error("JWT claims string is empty.");
        }
    }
}
