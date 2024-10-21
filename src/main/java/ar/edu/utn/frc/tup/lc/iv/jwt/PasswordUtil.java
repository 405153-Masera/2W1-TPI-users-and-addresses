package ar.edu.utn.frc.tup.lc.iv.jwt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase para el manejo de constraseñas.
 */
public class PasswordUtil {

    /**
     * Metodo para hashear la contraseña.
     * @param password una contraseña.
     * @return contraseña hasheada.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Clase para verificar la contraseña.
     * @param password una contraseña.
     * @param hashed contraseña hasheada.
     * @return checkeo de contraseña.
     */
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}

