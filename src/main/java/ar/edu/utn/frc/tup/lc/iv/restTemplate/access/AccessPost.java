package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import lombok.Data;

/**
 * DTO que representa la solicitud para crear un acceso.
 */
@Data
public class AccessPost {

    /**
     * Documento del usuario.
     */
    private String document;

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String last_name;

    /**
     * Tipo de documento para el acceso.
     */
    private AccessDocumentType documentType;

    /**
     * Tipo de acceso.
     */
    private AccessUserAllowedType user_allowed_Type;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Identificador del usuario que registra el acceso.
     */
    private Integer userId;
}
