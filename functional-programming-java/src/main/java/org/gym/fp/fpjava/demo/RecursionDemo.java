package org.gym.fp.fpjava.demo;

import org.gym.fp.fpjava.type.TailCall;

import static org.gym.fp.fpjava.type.TailCall.ret;
import static org.gym.fp.fpjava.type.TailCall.suspend;

public class RecursionDemo {

    public static void run() {
        System.out.println(add(10, 1000));
    }

    public static int add(int x, int y) {
        return addRec(x, y).eval();
    }

    private static TailCall<Integer> addRec(int x, int y) {
        return y == 0
                ? ret(x)
                : suspend(() -> addRec(x + 1, y - 1));
    }
}
