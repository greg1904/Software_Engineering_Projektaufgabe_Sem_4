package team16.employees.roles;

import team16.employees.types.AdminProfileType;
import team16.employees.security.access.ControlUnitAcessRole;

public class Administrator extends Employee {
    private final AdminProfileType profile;

    public Administrator(String name, int id, int pin, int superPin, AdminProfileType profile) {
        super(ControlUnitAcessRole.ADMINISTRATOR, name, id, pin, superPin);
        this.profile = profile;
    }

    public AdminProfileType getProfile() {
        return profile;
    }
}
