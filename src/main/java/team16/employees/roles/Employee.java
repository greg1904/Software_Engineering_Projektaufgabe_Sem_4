package team16.employees.roles;

import team16.employees.security.access.ControlUnitAcessRole;
import team16.employees.security.idcard.IDCard;
import team16.employees.security.idcard.MagnetStripe;

public abstract class Employee {
    private final ControlUnitAcessRole role;
    private final int id;
    private final String name;
    private final IDCard card;

    protected Employee(ControlUnitAcessRole role, String name, int id, int pin, int superPin) {
        this.role = role;
        this.name = name;
        this.id = id;
        this.card = new IDCard(new MagnetStripe(
                String.format("[%d];[%s];[%s];[%d];[%d]", id, name, role.name(), pin % 10_000, superPin % 1_000_000)));
    }

    public ControlUnitAcessRole getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IDCard getCard() {
        return card;
    }
}
