package team16.employee;

import team16.security.authorization.Role;

@SuppressWarnings("unused")
public class Administrator extends Employee {

    private final AdminProfile profile;

    public Administrator(String name, int id, int pin, int superPin, AdminProfile profile) {
        super(Role.ADMINISTRATOR, name, id, pin, superPin);
        this.profile = profile;
    }

    public AdminProfile getProfile() {
        return profile;
    }
}
