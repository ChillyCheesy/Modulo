package com.chillycheesy.hometracker.commands.operator;

import java.lang.annotation.Annotation;

public class OperatorBuilder {

    public static <T> com.chillycheesy.hometracker.commands.Operator build(T operatorInterface) {
        final com.chillycheesy.hometracker.commands.Operator operator = new com.chillycheesy.hometracker.commands.Operator();
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

    private void applyOperator(com.chillycheesy.hometracker.commands.Operator operator, com.chillycheesy.hometracker.commands.operator.Operator annotation) {
        operator.setPriority(annotation.value());
    }

    private void applyOperatorFindByRegex(com.chillycheesy.hometracker.commands.Operator operator, OperatorFindByRegex operatorFindByRegex) {
        operator.setFinder((flux) -> Operation.buildFormRegex(flux, operatorFindByRegex.value(), operator.getListener()));
    }

    private void selfOperatorListener(com.chillycheesy.hometracker.commands.Operator operator, OperatorListener operatorInterface) {
        operator.setListener(operatorInterface);
    }

    private void selfOperatorFinder(com.chillycheesy.hometracker.commands.Operator operator, OperatorFinder operatorInterface) {
        operator.setFinder(operatorInterface);
    }

}
