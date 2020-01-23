package org.gym.fp.fpjava.demo;

import org.gym.fp.fpjava.type.BinaryOperator;
import org.gym.fp.fpjava.type.Function;
import org.gym.fp.fpjava.type.Tuple;

public class FunctionDemo {

    // Es. 2.12
    public static final Function<Integer, Integer> factorial =
            n -> n <= 1 ? n : n * FunctionDemo.factorial.apply(n - 1);

    public static void run() {
        // function composition
        Function<Integer, Integer> triple = n -> n * 3;
        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, Integer> compose = square.compose(triple);
        System.out.println(compose.apply(2));
        System.out.println(square.andThen(triple).apply(2));

        // function currying
        BinaryOperator add = x -> y -> x + y;
        System.out.println(add.apply(3).apply(5));
        System.out.println(func().apply("ciao").apply("mondo").apply("questo").apply("currying"));

        // partial function
        Function<Double, Function<Double, Double>> addTax = x -> y -> y + y / 100 * x;
        Function<Double, Double> add9PercentTax = addTax.apply(9.0);
        System.out.println(add9PercentTax.apply(90.0));

        // recursive function
        System.out.println(factorial.apply(5));
    }

    // ES. 2.9
    private static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> func() {
        return a -> b -> c -> d -> String.format("%s %s %s %s", a, b, c, d);
    }

    // ES. 2.10
    private static <A, B, C> Function<A, Function<B, C>> curry(Function<Tuple<A, B>, C> fn) {
        return a -> b -> fn.apply(new Tuple<>(a, b));
    }

    // ES. 2.11
    private static <T, U, V> Function<U, Function<T, V>> swap(Function<T, Function<U, V>> fn) {
        return u -> t -> fn.apply(t).apply(u);
    }
}
