package team16.employee;

import team16.security.authorization.ControlUnitAcessRole;

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
