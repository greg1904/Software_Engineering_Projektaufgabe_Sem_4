package team16.employees.security.access;

public enum ControlUnitAccessRole { //SOLID-Prinzip: Role
    SUPERVISOR("Supervisor"),
    ADMINISTRATOR("Administrator"),
    OPERATOR("Operator"),
    DATAANALYST("DataAnalyst");

    private final String displayName;

    ControlUnitAccessRole(String name) {
        displayName = name;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
