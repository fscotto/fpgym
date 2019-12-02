package org.gym.fp.moderjava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

public class ConcurrencyTest {

    public static void main(String[] args) throws Exception {
        doExecutorsServiceTest();
    }

    private static void doExecutorsServiceTest() throws ExecutionException, InterruptedException {
        int x = 1337;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> y = executorService.submit(() -> f(x));
        Future<Integer> z = executorService.submit(() -> g(x));
        out.println("Result y + z = " + (y.get() + z.get()));
        executorService.shutdown();
    }

    static int f(int x) {
        return 42;
    }

    static int g(int x) {
        return x;
    }

}
