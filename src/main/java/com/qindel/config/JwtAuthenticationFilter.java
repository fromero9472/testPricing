package com.qindel.config;

import com.qindel.model.User;
import com.qindel.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro para la autenticación basada en JWT.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserService userService;

    /**
     * Método para realizar la lógica de filtrado interno en cada solicitud.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Extraer el token JWT de la solicitud
            String jwt = extractJwtFromRequest(request);

            // Validar el token y autenticar al usuario
            if (jwt != null && validateToken(jwt)) {
                Authentication authentication = getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException | SignatureException e) {
            // Manejar excepciones relacionadas con el token JWT
            handleJwtException(response, e);
            return;
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Extraer el token JWT de la solicitud.
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Validar el token JWT.
     */
    private boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtener la autenticación a partir del token JWT.
     */
    private Authentication getAuthentication(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();
        String username = claims.getSubject();

        // Buscar el usuario en el servicio de usuarios
        User user = userService.findByUsername(username);

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, null);
        }

        return null;
    }

    /**
     * Manejar excepciones relacionadas con el token JWT.
     */
    private void handleJwtException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token expirado o no válido");
    }
}
