package org.gym.fp.fpjava.type;

public class Weight {
    public static final Weight ZERO = new Weight(0.0);
    public static final Function<Weight, Function<OrderLine, Weight>> sum =
            x -> y -> x.add(y.getWeight());

    public final double value;

    public static Weight of(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        return new Weight(value);
    }

    private Weight(double value) {
        this.value = value;
    }

    public Weight add(Weight that) {
        return new Weight(this.value + that.value);
    }

    public Weight mult(int count) {
        return new Weight(this.value * count);
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }
}
