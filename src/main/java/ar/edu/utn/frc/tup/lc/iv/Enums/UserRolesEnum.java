package ar.edu.utn.frc.tup.lc.iv.Enums;

public enum UserRolesEnum {
    SUPER_ADMIN("Super Admin"),
    ADMIN("Admin"),
    OWNER("Propietario"),
    ADULT("Familiar Adulto"),
    MINOR("Familiar Menor"),
    EMPLOYEE("Empleado"),
    ACCOUNTANT("Contador"),
    SECURITY("Seguridad");

    private final String value;

    UserRolesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getId() {
        return this.name();
    }

}
