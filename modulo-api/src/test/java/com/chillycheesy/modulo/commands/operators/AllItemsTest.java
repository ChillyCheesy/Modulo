package com.chillycheesy.modulo.commands.operators;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.CommandManager;
import com.chillycheesy.modulo.commands.natif.AliasCommand;
import com.chillycheesy.modulo.commands.natif.EchoCommand;
import com.chillycheesy.modulo.commands.natif.ReturnCommand;
import com.chillycheesy.modulo.commands.operator.natif.*;
import com.chillycheesy.modulo.utils.exception.CommandException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllItemsTest {

    private CommandManager commandManager;

    @BeforeEach
    public final void beforeEach() {
        commandManager = ModuloAPI.getCommand().getCommandManager();
        ModuloAPI.getCommand().getOperatorManager().registerItemToBuild(null,
                new AliasOperator(),
                new AndOperator(),
                new BlockOperator(),
                new CommandStatusOperator(),
                new CreateStrictAliasOperator(),
                new CreateAliasOperator(),
                new DivideOperator(),
                new EqualsOperator(),
                new GreaterOrEqualsThanOperator(),
                new GreaterThanOperator(),
                new InjectorOperator(),
                new InstructionEndOperator(),
                new LowerOrEqualsThanOperator(),
                new LowerThanOperator(),
                new MinusOperator(),
                new ModuloOperator(),
                new MultiplicationOperator(),
                new NotOperator(),
                new OrOperator(),
                new ParenthesesOperator(),
                new PlusOperator(),
                new PowerOperator(),
                new SkipOperator(),
                new XorOperator()
        );
        commandManager.registerItemToBuild(null, new EchoCommand(), new ReturnCommand(), new AliasCommand());
    }

    @AfterEach
    public final void afterEach() {
        ModuloAPI.getCommand().getMainAliasManager().clear();
        ModuloAPI.getCommand().getOperatorManager().removeAllItems(null);
        ModuloAPI.getCommand().getCommandManager().removeAllItems(null);
    }

    @Test
    public final void functionPlusTest() throws CommandException {
        final String command =
            "plus => a + b;" +
            "\"a=8;b=7\":> plus";
        assertEquals("15.0", commandManager.applyCommand(command).getContent());
    }

    @Test
    public final void functionPrintTest() throws CommandException {
        final String command =
                "print => return {" +
                "   return \"Message: msg\"" +
                "};" +
                "echo \"msg=hello\" :> print";
        assertEquals("Message: hello", commandManager.applyCommand(command).getContent());
    }

}
