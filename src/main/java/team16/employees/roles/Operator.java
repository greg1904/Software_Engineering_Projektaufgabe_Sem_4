package team16.employees.roles;

import team16.employees.security.access.ControlUnitAccessRole;

@SuppressWarnings("unused")
public class Operator extends Employee {

    public Operator(String name, int id, int pin, int superPin) {
        super(ControlUnitAccessRole.OPERATOR, name, id, pin, superPin);
    }
}
