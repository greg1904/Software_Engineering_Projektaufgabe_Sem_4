package team16.building.component;

import team16.command.ICommand;
import team16.security.authorization.ControlUnitAcessRole;

public class TouchPad {

    private final Terminal terminal;

    public TouchPad(Terminal terminal) {
        this.terminal = terminal;
    }

    public boolean accessControlUnit(ControlUnitAcessRole role, ICommand command) {
        return terminal.getProxy().executeCommand(role, command);
    }
}
