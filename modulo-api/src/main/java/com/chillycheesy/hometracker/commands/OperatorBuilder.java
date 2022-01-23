package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorFindByRegex;

import java.lang.annotation.Annotation;

public class OperatorBuilder {

    public static <T> Operator build(T operatorInterface) {
        final Operator operator = new Operator();
        final Annotation[] annotations = operatorInterface.getClass().getDeclaredAnnotations();
        final OperatorBuilder builder = new OperatorBuilder();
        for (Annotation annotation : annotations) {
            if (annotation instanceof com.chillycheesy.hometracker.commands.operator.Operator)
                builder.applyOperator(operator, (com.chillycheesy.hometracker.commands.operator.Operator) annotation);
            else if (annotation instanceof OperatorFindByRegex)
                builder.applyOperatorFindByRegex(operator, (OperatorFindByRegex) annotation);
        }
        if (operatorInterface instanceof OperatorListener)
            builder.selfOperatorListener(operator, (OperatorListener) operatorInterface);
        if (operatorInterface instanceof OperatorFinder)
            builder.selfOperatorFinder(operator, (OperatorFinder) operatorInterface);
        return operator;
    }

    private void applyOperator(Operator operator, com.chillycheesy.hometracker.commands.operator.Operator annotation) {
        operator.setPriority(annotation.value());
    }

    private void applyOperatorFindByRegex(Operator operator, OperatorFindByRegex operatorFindByRegex) {
        operator.setFinder((flux) -> Operation.buildFormRegex(flux, operatorFindByRegex.value(), operator.getListener()));
    }

    private void selfOperatorListener(Operator operator, OperatorListener operatorInterface) {
        operator.setListener(operatorInterface);
    }

    private void selfOperatorFinder(Operator operator, OperatorFinder operatorInterface) {
        operator.setFinder(operatorInterface);
    }

}
