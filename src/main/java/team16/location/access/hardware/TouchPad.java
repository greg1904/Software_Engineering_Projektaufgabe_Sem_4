package team16.location.access.hardware;

import team16.communication.commands.ICommand;
import team16.employees.security.access.ControlUnitAccessRole;

public class TouchPad {
    private final Terminal terminal;

    public TouchPad(Terminal terminal) {
        this.terminal = terminal;
    }

    public boolean accessControlUnit(ControlUnitAccessRole role, ICommand command) {
        return terminal.getProxy().executeCommand(role, command);
    }
}
