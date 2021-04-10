package team16.security;

import team16.command.ICommand;
import team16.security.authorization.ControlUnitAcessRole;

public interface IAccess {
    boolean executeCommand(ControlUnitAcessRole role, ICommand command);
}
