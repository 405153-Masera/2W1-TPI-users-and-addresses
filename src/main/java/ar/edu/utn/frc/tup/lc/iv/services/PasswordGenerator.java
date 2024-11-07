package ar.edu.utn.frc.tup.lc.iv.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}
