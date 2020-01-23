package org.gym.fp.fpjava.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CollectionUtilities {

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T value) {
        return Collections.singletonList(value);
    }

    public static <T> List<T> list(List<T> ts) {
        return Collections.unmodifiableList(new ArrayList<>(ts));
    }

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        final T[] copy = Arrays.copyOf(elements, elements.length);
        return Collections.unmodifiableList(Arrays.asList(copy));
    }

    public static <T> T head(List<T> ts) {
        if (ts.size() == 0) throw new IllegalStateException("head of empty list");
        return ts.get(0);
    }

    public static <T> List<T> tail(List<T> ts) {
        if (ts.size() == 0) throw new IllegalStateException("tail of empty list");
        final List<T> workingList = copy(ts);
        workingList.remove(0);
        return Collections.unmodifiableList(workingList);
    }

    public static <T> List<T> append(List<T> ts, T value) {
        List<T> newList = copy(ts);
        newList.add(value);
        return Collections.unmodifiableList(newList);
    }

    // ES. 3.5
    public static int fold(List<Integer> list, Integer identity, Function<Integer, Function<Integer, Integer>> f) {
        int result = identity;
        for (Integer i : list) {
            result = f.apply(result).apply(i);
        }
        return result;
    }

    // ES. 3.6
    public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T, U>> f) {
        U result = identity;
        for (T e : list) {
            result = f.apply(result).apply(e);
        }
        return result;
    }

    // ES. 3.7
    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> f) {
        U result = identity;
        for (int i = list.size(); i > 0; i--) {
            result = f.apply(list.get(i - 1)).apply(result);
        }
        return result;
    }

    // ES. 3.8
    public static <T, U> U foldRightRec(List<T> list, U identity, Function<T, Function<U, U>> f) {
        return list.isEmpty()
                ? identity
                : f.apply(head(list))
                   .apply(foldRightRec(tail(list), identity, f));
    }

    // ES. 3.9
    public static <T> List<T> reverse(List<T> ts) {
        return foldLeft(ts, list(), x -> y -> prepend(y, x));
    }

    public static <T> List<T> prepend(T t, List<T> list) {
        return foldLeft(list, list(t), a -> b -> append(a, b));
    }

    // ES. 3.10
    public static <T, U> List<U> mapViaFoldLeft(List<T> list, Function<T, U> f) {
        return foldLeft(list, list(), x -> y -> append(x, f.apply(y)));
    }

    public static <T, U> List<U> mapViaFoldRight(List<T> list, Function<T, U> f) {
        return foldRight(list, list(), x -> y -> prepend(f.apply(x), y));
    }

    private static <T> List<T> copy(List<T> ts) {
        return new ArrayList<>(ts);
    }

    public static <T, U> List<U> map(List<T> list, Function<T, U> f) {
        final List<U> newList = new ArrayList<>();
        for (T value : list) {
            newList.add(f.apply(value));
        }
        return Collections.unmodifiableList(newList);
    }

    // ES. 3.11, 3.13
    public static List<Integer> range(int start, int end) {
//        List<Integer> result = list();
//        int tmp = start;
//        while (tmp < end) {
//            result = append(result, tmp);
//            tmp = tmp + 1;
//        }
//        return result;
        return unfold(start, x -> x + 1, x -> x < end);
    }

    // ES. 3.12
    public static <T> List<T> unfold(T seed, Function<T, T> f, Function<T, Boolean> predicate) {
        List<T> result = list();
        for (T tmp = seed; predicate.apply(tmp); tmp = f.apply(tmp)) {
            result = append(result, tmp);
        }
        return result;
    }
}
