package team16.communication.commands;

import team16.location.sorting.SortingSystem;

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
