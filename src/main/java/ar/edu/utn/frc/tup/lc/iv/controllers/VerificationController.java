package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador Rest para la verificación de datos únicos.
 * Se encarga de verificar si los datos ingresados son únicos en la base de datos.
 */
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    /**
     * Servicio de verificación de datos unicos.
     */
    private final VerificationService verificationService;

    /**
     * Verifica si el nombre de usuario es único.
     *
     * @param username Nombre de usuario a verificar.
     * @return Respuesta con el resultado de la verificación.
     */
    @GetMapping("/username")
    public ResponseEntity<Map<String, Boolean>> isUsernameUnique(@RequestParam String username) {
        boolean isUnique = verificationService.isUsernameUnique(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica si el correo electrónico es único.
     *
     * @param email Correo electrónico a verificar.
     * @return Respuesta con el resultado de la verificación.
     */
    @GetMapping("/email")
    public ResponseEntity<Map<String, Boolean>> isEmailUnique(@RequestParam String email) {
        boolean isUnique = verificationService.isEmailUnique(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica si el número de DNI es único.
     *
     * @param dni Número de DNI a verificar.
     * @return Respuesta con el resultado de la verificación.
     */
    @GetMapping("/dni")
    public ResponseEntity<Map<String, Boolean>> isDniUnique(@RequestParam String dni) {
        boolean isUnique = verificationService.isDniUnique(dni);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }
}
