package org.gym.fp.fpjava.demo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gym.fp.fpjava.type.TailCall;

import java.math.BigInteger;

import static org.gym.fp.fpjava.type.TailCall.ret;
import static org.gym.fp.fpjava.type.TailCall.suspend;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecursionDemo {

    public static void run() {
        System.out.println(add(10, 1000));
        System.out.print(fibonacci(10000));
    }

    public static int add(int x, int y) {
        return addRec(x, y).eval();
    }

    private static TailCall<Integer> addRec(int x, int y) {
        return y == 0
                ? ret(x)
                : suspend(() -> addRec(x + 1, y - 1));
    }

    // ES. 4.1, 4.2
    public static BigInteger fibonacci(int number) {
        class Aux {
            public TailCall<BigInteger> fib(BigInteger acc1, BigInteger acc2, BigInteger x) {
                if (x.equals(BigInteger.ZERO)) {
                    return ret(BigInteger.ZERO);
                } else if (x.equals(BigInteger.ONE)) {
                    return ret(acc1.add(acc2));
                } else {
                    return suspend(() -> fib(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
                }
            }
        }
        return (new Aux()).fib(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(number)).eval();
    }
}
