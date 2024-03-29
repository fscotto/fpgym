package org.gym.fp.fpjava.type;

public class Price {
    public static final Price ZERO = new Price(0.0);
    public static final Function<Price, Function<OrderLine, Price>> sum =
            x -> y -> x.add(y.getAmount());

    public final double value;

    public static Price of(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        return new Price(value);
    }

    private Price(double value) {
        this.value = value;
    }

    public Price add(Price that) {
        return new Price(this.value + that.value);
    }

    public Price mult(int count) {
        return new Price(this.value * count);
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }
}
