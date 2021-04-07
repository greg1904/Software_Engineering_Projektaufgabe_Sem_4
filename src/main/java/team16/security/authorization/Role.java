package team16.security.authorization;

public enum Role {//SOLID-Prinzip: Role
    SUPERVISOR("Supervisor"),
    ADMINISTRATOR("Administrator"),
    OPERATOR("Operator"),
    DATAANALYST("DataAnalyst");

    private final String displayName;

    Role(String name) {
        displayName = name;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
