package com.chillycheesy.modulo.utils.exception;

import com.chillycheesy.modulo.modules.Module;

import java.util.Arrays;
import java.util.List;

public class MissingDependenciesModuleException extends Exception {

    public MissingDependenciesModuleException(Module module, List<String> missingDependencies) {
        super("Missing dependencies for module: " + module.getName() + " -> " + Arrays.toString(missingDependencies.toArray()));
    }

}
