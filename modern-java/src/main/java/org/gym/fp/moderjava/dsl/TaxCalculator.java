package org.gym.fp.moderjava.dsl;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {
    private DoubleUnaryOperator taxFunction = d -> d;

    public TaxCalculator with(DoubleUnaryOperator f) {
        this.taxFunction = taxFunction.andThen(f);
        return this;
    }

    public double calculate(Order order) {
        return this.taxFunction.applyAsDouble(order.getValue());
    }

}
