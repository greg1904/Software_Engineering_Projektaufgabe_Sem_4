package team16.employee;

import team16.security.authorization.Role;

public class Supervisor extends Employee {

    private final boolean isSenior;

    public Supervisor(String name, int id, int pin, int superPin, boolean isSenior) {
        super(Role.SUPERVISOR, name, id, pin, superPin);
        this.isSenior = isSenior;
    }

    @SuppressWarnings("unused")
    public boolean isSenior() {
        return isSenior;
    }
}
