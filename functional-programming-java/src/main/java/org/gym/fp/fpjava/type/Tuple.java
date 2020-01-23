package org.gym.fp.fpjava.type;

public class Tuple<A, B> {
    private final A _1;
    private final B _2;

    public Tuple(A first, B second) {
        this._1 = first;
        this._2 = second;
    }
}
