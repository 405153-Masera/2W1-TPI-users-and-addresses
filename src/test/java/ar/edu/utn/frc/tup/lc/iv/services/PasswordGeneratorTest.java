package ar.edu.utn.frc.tup.lc.iv.services;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void shouldGeneratePasswordOfCorrectLength() {

        String password = PasswordGenerator.generateRandomPassword();
        assertEquals(6, password.length(), "Password length should be 6");
    }

    @Test
    void shouldGeneratePasswordWithValidCharacters() {

        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%&";

        String password = PasswordGenerator.generateRandomPassword();

        for (char c : password.toCharArray()) {
            assertTrue(validCharacters.indexOf(c) >= 0, "Password contains invalid character: " + c);
        }
    }

    @RepeatedTest(10)
    void shouldGenerateRandomPasswords() {
        // Act
        String password1 = PasswordGenerator.generateRandomPassword();
        String password2 = PasswordGenerator.generateRandomPassword();

        // Assert
        assertNotEquals(password1, password2, "Passwords should be different");
    }
}