package team16.employees.security.access;

public enum ControlUnitAcessRole {//SOLID-Prinzip: Role
    SUPERVISOR("Supervisor"),
    ADMINISTRATOR("Administrator"),
    OPERATOR("Operator"),
    DATAANALYST("DataAnalyst");

    private final String displayName;

    ControlUnitAcessRole(String name) {
        displayName = name;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
