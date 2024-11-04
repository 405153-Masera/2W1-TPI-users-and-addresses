package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import lombok.Data;

/**
 * DTO que representa la solicitud para actualizar un acceso.
 */
@Data
public class AccessPut {

    /**
     * Documento actualizado del acceso.
     */
    private String document;
}
