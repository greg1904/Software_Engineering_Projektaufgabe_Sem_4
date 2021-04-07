package team16.security;

import team16.building.component.ControlUnit;
import team16.command.ChangeSearchAlgorithmCommand;
import team16.command.ICommand;
import team16.command.InitCommand;
import team16.command.LockCommand;
import team16.command.NextCommand;
import team16.command.ShowStatisticsCommand;
import team16.command.ShutdownCommand;
import team16.command.UnlockCommand;
import team16.security.authorization.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProxyControlUnit implements IAccess {//SOLID-Prinzip: Proxy

    private final ControlUnit controlUnit;
    private final Map<Role, List<Class<? extends ICommand>>> permissions = new HashMap<>();

    public ProxyControlUnit(ControlUnit controlUnit) {
        this.controlUnit = controlUnit;
        permissions.put(Role.SUPERVISOR, Stream.of(
                InitCommand.class,
                ChangeSearchAlgorithmCommand.class,
                LockCommand.class,
                NextCommand.class,
                ShowStatisticsCommand.class,
                ShutdownCommand.class,
                UnlockCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(Role.ADMINISTRATOR, Stream.of(ShowStatisticsCommand.class, ShutdownCommand.class).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(Role.OPERATOR, Stream.of(NextCommand.class, ShowStatisticsCommand.class).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(Role.DATAANALYST, Stream.of(ShowStatisticsCommand.class).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public boolean executeCommand(Role role, ICommand command) {
        if (!hasPermission(role, command)) {
            return false;
        }
        controlUnit.sendCommand(command);
        return true;
    }

    private boolean hasPermission(Role role, ICommand command) {
        return permissions.get(role) != null && permissions.get(role).contains(command.getClass());
    }
}
