package team16.command;

import team16.building.component.sortingSystem.SortingSystem;

public class LockCommand implements ICommand {//SOLID-Prinzip: Command

    private final SortingSystem system;

    public LockCommand(SortingSystem system) {
        this.system = system;
    }

    @Override
    public void execute() {
        system.lock();
        System.out.println("System locked");
    }
}
