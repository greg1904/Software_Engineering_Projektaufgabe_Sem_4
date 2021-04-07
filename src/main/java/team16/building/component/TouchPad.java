package team16.building.component;

import team16.command.ICommand;
import team16.security.authorization.Role;

public class TouchPad {

    private final Terminal terminal;

    public TouchPad(Terminal terminal) {
        this.terminal = terminal;
    }

    public boolean accessControlUnit(Role role, ICommand command) {
        return terminal.getProxy().executeCommand(role, command);
    }
}
