package team16.employee;

import team16.security.authorization.Role;

@SuppressWarnings("unused")
public class DataAnalyst extends Employee {

    public DataAnalyst(String name, int id, int pin, int superPin) {
        super(Role.DATAANALYST, name, id, pin, superPin);
    }
}
