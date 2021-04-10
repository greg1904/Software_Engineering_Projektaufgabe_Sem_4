package team16.employees.roles;

import team16.employees.security.access.ControlUnitAccessRole;

public class Supervisor extends Employee {
    private final boolean isSenior;

    public Supervisor(String name, int id, int pin, int superPin, boolean isSenior) {
        super(ControlUnitAccessRole.SUPERVISOR, name, id, pin, superPin);
        this.isSenior = isSenior;
    }

    @SuppressWarnings("unused")
    public boolean isSenior() {
        return isSenior;
    }
}
