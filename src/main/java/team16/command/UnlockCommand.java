package team16.command;

import team16.building.component.sortingSystem.SortingSystem;

public class UnlockCommand implements ICommand { //SOLID-Prinzip: Command
    private final SortingSystem sortingSystem;

    public UnlockCommand(SortingSystem sortingSystem) {
        this.sortingSystem = sortingSystem;
    }

    @Override
    public void execute() {
        System.out.println("System has been unlocked");
        sortingSystem.unlock();
    }
}
