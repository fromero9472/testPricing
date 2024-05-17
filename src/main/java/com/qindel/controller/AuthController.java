package com.qindel.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@Api(tags = "Authentication", description = "Endpoints para generar tokens de autenticación")
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.user}")
    private String jwtUser;

    @Value("${jwt.password}")
    private String jwtPassword;

    @PostMapping("/generate-token")
    @ApiOperation(value = "Generar token de autenticación", notes = "Genera un token de autenticación válido por 24 horas.")
    public ResponseEntity<String> generateToken() {
        // Verificar las credenciales (puedes personalizar esto según tu lógica de autenticación)
        if (isValidCredentials()) {

            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            String token = Jwts.builder()
                    .setSubject(jwtUser)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    private boolean isValidCredentials() {
        return "usuario".equals(jwtUser) && "contraseña".equals(jwtPassword);
    }
}
