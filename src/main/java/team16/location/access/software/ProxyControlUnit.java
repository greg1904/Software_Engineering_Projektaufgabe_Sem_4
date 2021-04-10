package team16.location.access.software;

import team16.communication.commands.*;
import team16.employees.security.access.ControlUnitAccessRole;
import team16.employees.security.access.IAccess;
import team16.location.CentralControlUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProxyControlUnit implements IAccess { //SOLID-Prinzip: Proxy
    private static final Map<ControlUnitAccessRole, List<Class<? extends ICommand>>> permissions = new HashMap<>();

    static {
        permissions.put(ControlUnitAccessRole.SUPERVISOR, Stream.of(
                ChangeSearchAlgorithmCommand.class,
                InitCommand.class,
                LockCommand.class,
                NextCommand.class,
                ShowStatisticsCommand.class,
                ShutdownCommand.class,
                UnlockCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAccessRole.ADMINISTRATOR, Stream.of(
                ShutdownCommand.class,
                ShowStatisticsCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAccessRole.OPERATOR, Stream.of(
                NextCommand.class,
                ShowStatisticsCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));

        permissions.put(ControlUnitAccessRole.DATAANALYST, Stream.of(
                ShowStatisticsCommand.class
        ).collect(Collectors.toCollection(ArrayList::new)));
    }

    private final CentralControlUnit centralControlUnit;

    public ProxyControlUnit(CentralControlUnit centralControlUnit) {
        this.centralControlUnit = centralControlUnit;
    }

    @Override
    public boolean executeCommand(ControlUnitAccessRole role, ICommand command) {
        if (!hasPermission(role, command)) {
            return false;
        }
        centralControlUnit.sendCommand(command);
        return true;
    }

    private boolean hasPermission(ControlUnitAccessRole role, ICommand command) {
        return permissions.get(role) != null && permissions.get(role).contains(command.getClass());
    }
}
