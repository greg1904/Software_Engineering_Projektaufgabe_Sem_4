package team16.employees.security.access;

import team16.communication.commands.ICommand;

public interface IAccess { //SOLID-Prinzip: Proxy
    boolean executeCommand(ControlUnitAccessRole role, ICommand command);
}
