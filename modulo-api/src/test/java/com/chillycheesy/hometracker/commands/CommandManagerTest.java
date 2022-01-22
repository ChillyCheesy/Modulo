package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.ParenthesesOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandManagerTest {

    private OperatorManager operatorManager;
    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        operatorManager = new OperatorManager();
        commandManager = new CommandManager(operatorManager);
    }

    @Test
    public final void applyOperatorMatchCorrectOperators() {
        operatorManager.registerItem(null, new ParenthesesOperator());
        final String line = "Salut tout (le (monde)) (les gonz)";
        final CommandFlux flux = commandManager.applyCommand(line);
        System.out.println(flux.getContent());
    }

}
