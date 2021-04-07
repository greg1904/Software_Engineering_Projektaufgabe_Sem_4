package team16.command;

import team16.configuration.Configuration;
import team16.configuration.SearchAlgorithm;

public class ChangeSearchAlgorithmCommand implements ICommand {//SOLID-Prinzip: Command

    @Override
    public void execute() {
        int count = SearchAlgorithm.values().length;
        if (Configuration.INSTANCE.searchAlgorithm.ordinal() == count - 1) {
            Configuration.INSTANCE.searchAlgorithm = SearchAlgorithm.values()[0];
        } else {
            Configuration.INSTANCE.searchAlgorithm = SearchAlgorithm.values()[Configuration.INSTANCE.searchAlgorithm.ordinal() + 1];
        }
    }
}
