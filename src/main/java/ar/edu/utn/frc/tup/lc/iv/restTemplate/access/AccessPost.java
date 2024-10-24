package ar.edu.utn.frc.tup.lc.iv.restTemplate.access;

import lombok.Data;

@Data
public class AccessPost {
    private String document;
    private String name;
    private String last_name;
    private AccessDocumentType documentType;
    private AccessUserAllowedType user_allowed_Type;
    private String email;
}
