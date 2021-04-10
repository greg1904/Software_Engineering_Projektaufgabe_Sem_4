package team16.command;

import team16.configuration.Configuration;
import team16.configuration.SearchAlgorithm;

public class ChangeSearchAlgorithmCommand implements ICommand { //SOLID-Prinzip: Command

    @Override
    public void execute() {
        Configuration.instance.searchAlgorithmJarPath =
                Configuration.instance.searchAlgorithmJarPath.equals(Configuration.instance.boyerMooreJarPath) ?
                        Configuration.instance.rabinKarpJarPath
                        : Configuration.instance.boyerMooreJarPath;
    }
}
