package team16.employees.roles;

import team16.employees.security.access.ControlUnitAcessRole;

@SuppressWarnings("unused")
public class DataAnalyst extends Employee {

    public DataAnalyst(String name, int id, int pin, int superPin) {
        super(ControlUnitAcessRole.DATAANALYST, name, id, pin, superPin);
    }
}
