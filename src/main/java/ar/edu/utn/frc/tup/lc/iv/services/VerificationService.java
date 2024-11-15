package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz VerificationService,
 * contiene la lógica de verificación.
 */
@Service
@RequiredArgsConstructor
public class VerificationService {

    /**
     * Repositorio para manejar los datos de los usuarios.
     */
    private final UserRepository userRepository;

    /**
     * Servicio para manejar el restTemplate de contactos.
     */
    private final RestContact restContact;

    /**
     * Verifica si el nombre de usuario es único.
     *
     * @param username Nombre de usuario a verificar.
     * @return {@code true} si el nombre de usuario es único, {@code false} en caso contrario.
     */
    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    /**
     * Verifica si el correo electrónico es único.
     *
     * @param email Correo electrónico a verificar.
     * @return {@code true} si el correo electrónico es único, {@code false} en caso contrario.
     */
    public boolean isEmailUnique(String email) {
        List<String> emails = restContact.getAllEmails();
        return !emails.contains(email);
    }

    /**
     * Verifica si el número de DNI es único.
     *
     * @param dni Número de DNI a verificar.
     * @return {@code true} si el número de DNI es único, {@code false} en caso contrario.
     */
    public boolean isDniUnique(String dni) {
        return !userRepository.existsByDni(dni);
    }
}
