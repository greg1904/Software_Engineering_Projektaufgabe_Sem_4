package team16.employee;

import team16.security.authorization.Role;

@SuppressWarnings("unused")
public class Operator extends Employee {

    public Operator(String name, int id, int pin, int superPin) {
        super(Role.OPERATOR, name, id, pin, superPin);
    }
}
