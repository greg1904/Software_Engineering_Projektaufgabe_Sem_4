package team16.security;

import team16.command.ICommand;
import team16.security.authorization.Role;

public interface IAccess {

    boolean executeCommand(Role role, ICommand command);
}
