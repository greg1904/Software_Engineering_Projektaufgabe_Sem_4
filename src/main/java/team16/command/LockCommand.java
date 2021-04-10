package team16.command;

import team16.building.component.sortingSystem.SortingSystem;

public class LockCommand implements ICommand { //SOLID-Prinzip: Command
    private final SortingSystem sortingSystem;

    public LockCommand(SortingSystem sortingSystem) {
        this.sortingSystem = sortingSystem;
    }

    @Override
    public void execute() {
        sortingSystem.lock();
        System.out.println("System has been locked.");
    }
}
