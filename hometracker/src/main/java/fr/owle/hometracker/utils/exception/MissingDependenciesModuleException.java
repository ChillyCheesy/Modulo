package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;

import java.util.Arrays;
import java.util.List;

public class MissingDependenciesModuleException extends Exception {

    public MissingDependenciesModuleException(HTModule module, List<String> missingDependencies) {
        super("Missing dependencies for module: " + module.getName() + " -> " + Arrays.toString(missingDependencies.toArray()));
    }

}
