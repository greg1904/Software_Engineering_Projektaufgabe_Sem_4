package team16.communication.commands;

import team16.base.Configuration;

public class ChangeSearchAlgorithmCommand implements ICommand { //SOLID-Prinzip: Command

    @Override
    public void execute() {
        Configuration.instance.searchAlgorithmJarPath =
                Configuration.instance.searchAlgorithmJarPath.equals(Configuration.instance.boyerMooreJarPath) ?
                        Configuration.instance.rabinKarpJarPath
                        : Configuration.instance.boyerMooreJarPath;
    }
}
