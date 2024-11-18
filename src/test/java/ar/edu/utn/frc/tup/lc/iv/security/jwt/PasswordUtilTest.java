package ar.edu.utn.frc.tup.lc.iv.security.jwt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashPassword() {
        String password = "myPassword";
        String hashedPassword = PasswordUtil.hashPassword(password);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void checkPassword() {
        String password = "myPassword";
        String hashedPassword = PasswordUtil.hashPassword(password);

        assertTrue(PasswordUtil.checkPassword(password, hashedPassword));
        assertFalse(PasswordUtil.checkPassword("wrongPassword", hashedPassword));
    }
}