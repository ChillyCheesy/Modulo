package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.Operation;
import com.chillycheesy.hometracker.commands.Operator;
import com.chillycheesy.hometracker.commands.OperatorListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlusOperator extends Operator implements OperatorListener {

    public PlusOperator() {
        super(Operator.MAJOR, null);
        super.setListener(this);
    }

    @Override
    public CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right) {
        return null;
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        final Pattern pattern = Pattern.compile("\\+");
        final Matcher matcher = pattern.matcher(flux.getContent());
        // todo: check if the matcher is valid
        return null;
    }

}
