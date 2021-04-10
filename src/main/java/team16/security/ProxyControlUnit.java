package team16.security;

import team16.building.component.CentralControlUnit;
import team16.command.ChangeSearchAlgorithmCommand;
import team16.command.ICommand;
import team16.command.InitCommand;
import team16.command.LockCommand;
import team16.command.NextCommand;
import team16.command.ShowStatisticsCommand;
import team16.command.ShutdownCommand;
import team16.command.UnlockCommand;
import team16.security.authorization.ControlUnitAcessRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProxyControlUnit implements IAccess {//SOLID-Prinzip: Proxy

    private final CentralControlUnit centralControlUnit;
    private final Map<ControlUnitAcessRole, List<Class<? extends ICommand>>> permissions = new HashMap<>();

    public ProxyControlUnit(CentralControlUnit centralControlUnit) {
        this.centralControlUnit = centralControlUnit;
        permissions.put(ControlUnitAcessRole.SUPERVISOR, Stream.of(
                InitCommand.class,
                ChangeSearchAlgorithmCommand.class,
                LockCommand.class,
                NextCommand.class,
                ShowStatisticsCommand.class,
                ShutdownCommand.class,
                UnlockCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAcessRole.ADMINISTRATOR, Stream.of(ShowStatisticsCommand.class, ShutdownCommand.class).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAcessRole.OPERATOR, Stream.of(NextCommand.class, ShowStatisticsCommand.class).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAcessRole.DATAANALYST, Stream.of(ShowStatisticsCommand.class).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public boolean executeCommand(ControlUnitAcessRole role, ICommand command) {
        if (!hasPermission(role, command)) {
            return false;
        }
        centralControlUnit.sendCommand(command);
        return true;
    }

    private boolean hasPermission(ControlUnitAcessRole role, ICommand command) {
        return permissions.get(role) != null && permissions.get(role).contains(command.getClass());
    }
}
