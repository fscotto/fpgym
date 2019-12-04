package org.gym.fp.moderjava;

import org.gym.fp.moderjava.concurrent.Shop;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.*;

import static java.lang.System.out;

public class ConcurrencyTest {

    public static void main(String[] args) throws Exception {
        doExecutorsServiceTest();
        doFutureTest();
    }

    private static void doExecutorsServiceTest() throws Exception {
        int x = 1337;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> y = executorService.submit(() -> f(x));
        Future<Integer> z = executorService.submit(() -> g(x));
        out.println("Result y + z = " + (y.get() + z.get()));
        executorService.shutdown();
    }

    private static void doFutureTest() throws Exception {
        Collection<Shop> shops = getShops();
        Future<Integer> future = CompletableFuture.supplyAsync(() -> {
            out.println("Primo completable future");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            out.println("Secondo completable future");
            return 30;
        }), (first, second) -> first + second);
        out.println(future.get(1, TimeUnit.NANOSECONDS));
    }

    static int f(int x) {
        return 42;
    }

    static int g(int x) {
        return x;
    }

    static Collection<Shop> getShops() {
        return Arrays.asList(
                new Shop("BestShop1"),
                new Shop("BestShop2"),
                new Shop("BestShop3"),
                new Shop("BestShop4"),
                new Shop("BestShop5"),
                new Shop("BestShop6"),
                new Shop("BestShop7"),
                new Shop("BestShop8")
        );
    }

}
