package team16.command;

import team16.building.component.sortingSystem.SortingSystem;

public class UnlockCommand implements ICommand {//SOLID-Prinzip: Command

    private final SortingSystem system;

    public UnlockCommand(SortingSystem system) {
        this.system = system;
    }

    @Override
    public void execute() {
        system.unlock();
        System.out.println("System unlocked");
    }
}
