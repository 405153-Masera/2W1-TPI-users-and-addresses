package ar.edu.utn.frc.tup.lc.iv.jwt;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Para hashear la contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Para verificar la contraseña
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}

