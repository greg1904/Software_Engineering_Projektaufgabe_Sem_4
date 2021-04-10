package team16.command;

import team16.configuration.Configuration;
import team16.configuration.SearchAlgorithm;

public class ChangeSearchAlgorithmCommand implements ICommand {//SOLID-Prinzip: Command

    @Override
    public void execute() {
        int count = SearchAlgorithm.values().length;
        if (Configuration.instance.searchAlgorithm.ordinal() == count - 1) {
            Configuration.instance.searchAlgorithm = SearchAlgorithm.values()[0];
        } else {
            Configuration.instance.searchAlgorithm = SearchAlgorithm.values()[Configuration.instance.searchAlgorithm.ordinal() + 1];
        }
    }
}
