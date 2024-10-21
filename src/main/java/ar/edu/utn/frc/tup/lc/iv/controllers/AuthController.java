package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.jwt.JwtUtil;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para manejar operaciones de Autenticación.
 *
 * Expone Enpoints para gestionar un inicio de sesión utilizando JWT.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /** Servicio para manejar la lógica de usuarios. */
    @Autowired
    private UserService userService;


    /**
     * Devuelve la confirmación de un inicio de sesión exitoso.
     *
     * @param credentials credenciales del logueo.
     * @throws IllegalArgumentException excepción credenciales invalidas.
     * @return respuesta de confirmación de logueo.
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody PostLoginDto credentials) {
        GetUserDto user = userService.verifyLogin(credentials);

        if (user != null) {
            // Crear el mapa de claims con todos los campos de GetUserDto
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("name", user.getName());
            claims.put("lastname", user.getLastname());
            claims.put("username", user.getUsername());
            claims.put("email", user.getEmail());
            claims.put("phone_number", user.getPhone_number());
            claims.put("dni", user.getDni());
            claims.put("active", user.getActive());
            claims.put("avatar_url", user.getAvatar_url());
            claims.put("datebirth", user.getDatebirth().toString());  // Convertir LocalDate a String
            claims.put("roles", user.getRoles());
            claims.put("plot_id", user.getPlot_id());
            claims.put("telegram_id", user.getTelegram_id());

            // Generar el token
            String token = JwtUtil.generateToken(user.getEmail(), claims);

            // Devolver el token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
    }

}

