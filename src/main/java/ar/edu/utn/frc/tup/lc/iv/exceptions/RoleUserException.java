package ar.edu.utn.frc.tup.lc.iv.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoleUserException extends RuntimeException{
    private final HttpStatus status;

    public RoleUserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
