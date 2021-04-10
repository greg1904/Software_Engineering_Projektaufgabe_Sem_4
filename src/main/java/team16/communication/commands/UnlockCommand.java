package team16.communication.commands;

import team16.location.sorting.SortingSystem;

public class UnlockCommand implements ICommand { //SOLID-Prinzip: Command
    private final SortingSystem sortingSystem;

    public UnlockCommand(SortingSystem sortingSystem) {
        this.sortingSystem = sortingSystem;
    }

    @Override
    public void execute() {
        System.out.println("System has been unlocked.");
        sortingSystem.unlock();
    }
}
