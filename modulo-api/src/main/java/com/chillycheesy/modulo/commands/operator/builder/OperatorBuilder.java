package com.chillycheesy.modulo.commands.operator.builder;

import com.chillycheesy.modulo.commands.operator.Operation;
import com.chillycheesy.modulo.commands.operator.OperatorFinder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;

import java.lang.annotation.Annotation;

public class OperatorBuilder {

    public static <T> com.chillycheesy.modulo.commands.operator.Operator build(T operatorInterface) {
        final com.chillycheesy.modulo.commands.operator.Operator operator = new com.chillycheesy.modulo.commands.operator.Operator();
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

    private void applyOperator(com.chillycheesy.modulo.commands.operator.Operator operator, Operator annotation) {
        operator.setPriority(annotation.value());
    }

    private void applyOperatorFindByRegex(com.chillycheesy.modulo.commands.operator.Operator operator, OperatorFindByRegex operatorFindByRegex) {
        operator.setFinder((flux) -> Operation.buildFormRegex(flux, operatorFindByRegex.value(), operator.getListener()));
    }

    private void selfOperatorListener(com.chillycheesy.modulo.commands.operator.Operator operator, OperatorListener operatorInterface) {
        operator.setListener(operatorInterface);
    }

    private void selfOperatorFinder(com.chillycheesy.modulo.commands.operator.Operator operator, OperatorFinder operatorInterface) {
        operator.setFinder(operatorInterface);
    }

}
