package com.chillycheesy.hometracker.commands.operator.builder;

import com.chillycheesy.hometracker.commands.operator.Operation;
import com.chillycheesy.hometracker.commands.operator.OperatorFinder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;

import java.lang.annotation.Annotation;

public class OperatorBuilder {

    public static <T> com.chillycheesy.hometracker.commands.operator.Operator build(T operatorInterface) {
        final com.chillycheesy.hometracker.commands.operator.Operator operator = new com.chillycheesy.hometracker.commands.operator.Operator();
        final Annotation[] annotations = operatorInterface.getClass().getDeclaredAnnotations();
        final OperatorBuilder builder = new OperatorBuilder();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Operator)
                builder.applyOperator(operator, (Operator) annotation);
            else if (annotation instanceof OperatorFindByRegex)
                builder.applyOperatorFindByRegex(operator, (OperatorFindByRegex) annotation);
        }
        if (operatorInterface instanceof OperatorListener)
            builder.selfOperatorListener(operator, (OperatorListener) operatorInterface);
        if (operatorInterface instanceof OperatorFinder)
            builder.selfOperatorFinder(operator, (OperatorFinder) operatorInterface);
        return operator;
    }

    private void applyOperator(com.chillycheesy.hometracker.commands.operator.Operator operator, Operator annotation) {
        operator.setPriority(annotation.value());
    }

    private void applyOperatorFindByRegex(com.chillycheesy.hometracker.commands.operator.Operator operator, OperatorFindByRegex operatorFindByRegex) {
        operator.setFinder((flux) -> Operation.buildFormRegex(flux, operatorFindByRegex.value(), operator.getListener()));
    }

    private void selfOperatorListener(com.chillycheesy.hometracker.commands.operator.Operator operator, OperatorListener operatorInterface) {
        operator.setListener(operatorInterface);
    }

    private void selfOperatorFinder(com.chillycheesy.hometracker.commands.operator.Operator operator, OperatorFinder operatorInterface) {
        operator.setFinder(operatorInterface);
    }

}
