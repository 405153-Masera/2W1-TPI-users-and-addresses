package ar.edu.utn.frc.tup.lc.iv.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * Clase que genera contraseñas aleatorias para su cambio.
 */
@Service
public class PasswordGenerator {

    /**
     * Caracteres que se pueden utilizar para generar la contraseña.
     */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%&";

    /**
     * Longitud de la contraseña generada.
     */
    private static final int PASSWORD_LENGTH = 6;

    /**
     * Generador de números aleatorios.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Genera una contraseña aleatoria.
     *
     * @return una contraseña aleatoria.
     */
    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}
