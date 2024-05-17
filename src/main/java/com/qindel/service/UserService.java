package com.qindel.service;

import com.qindel.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    // Simula un repositorio de usuarios en memoria
    private static final Map<String, String> userCredentials = new HashMap<>();

    static {
        // Agrega un usuario de ejemplo (a modo de demostración)
        userCredentials.put("usuarioEjemplo", "$2a$10$T5rbJX0cUGhh3PBN3X9fF.EFg2mmUDN45OD7Dih5dEEXpOzBwvGn6"); // Contraseña encriptada "password"
    }

    public User findByUsername(String username) {
        // Simula la búsqueda de un usuario en la base de datos
        if (userCredentials.containsKey(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(userCredentials.get(username));
            return user;
        }
        return null;
    }
}
