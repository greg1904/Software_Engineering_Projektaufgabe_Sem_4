package team16.employees.roles;

import team16.employees.security.access.ControlUnitAccessRole;

@SuppressWarnings("unused")
public class DataAnalyst extends Employee {

    public DataAnalyst(String name, int id, int pin, int superPin) {
        super(ControlUnitAccessRole.DATAANALYST, name, id, pin, superPin);
    }
}
