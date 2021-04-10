package team16.employees.roles;

import team16.employees.security.access.ControlUnitAcessRole;

@SuppressWarnings("unused")
public class Operator extends Employee {

    public Operator(String name, int id, int pin, int superPin) {
        super(ControlUnitAcessRole.OPERATOR, name, id, pin, superPin);
    }
}
