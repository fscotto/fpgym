package org.gym.fp.fpjava.collection;

import org.gym.fp.fpjava.type.Function;
import org.gym.fp.fpjava.type.TailCall;

import static org.gym.fp.fpjava.type.TailCall.ret;
import static org.gym.fp.fpjava.type.TailCall.suspend;

public abstract class List<A> {

    public abstract A head();

    public abstract List<A> tail();

    public abstract boolean isEmpty();

    public abstract List<A> setHead(A a);

    public abstract List<A> drop(int n);

    public abstract List<A> dropWhile(Function<A, Boolean> predicate);

    public abstract List<A> reverse();

    public abstract List<A> init();

    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    private List() {
    }

    private static class Nil<A> extends List<A> {

        private Nil() {
        }

        @Override
        public A head() {
            throw new IllegalStateException("head called en empty list");
        }

        @Override
        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A a) {
            throw new IllegalStateException("setHead called on empty list");
        }

        @Override
        public List<A> drop(int n) {
            return this;
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> predicate) {
            return this;
        }

        @Override
        public List<A> reverse() {
            return this;
        }

        @Override
        public List<A> init() {
            throw new IllegalStateException("init called on empty list");
        }

        @Override
        public String toString() {
            return "[NIL]";
        }
    }

    private static class Cons<A> extends List<A> {
        private final A head;
        private final List<A> tail;

        private Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public A head() {
            return head;
        }

        @Override
        public List<A> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<A> setHead(A h) {
            return new Cons<>(h, tail());
        }

        @Override
        public List<A> drop(int n) {
            return n <= 0 ? this : drop(this, n).eval();
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> predicate) {
            return dropWhile(this, predicate).eval();
        }

        @Override
        public List<A> reverse() {
            return reverse(list(), this).eval();
        }

        @Override
        public List<A> init() {
            return reverse().tail().reverse();
        }

        public TailCall<List<A>> reverse(List<A> acc, List<A> list) {
            return list.isEmpty()
                    ? ret(acc)
                    : suspend(() -> reverse(new Cons<>(list.head(), acc), list.tail()));
        }

        private TailCall<List<A>> dropWhile(List<A> list, Function<A, Boolean> predicate) {
            return !list.isEmpty() && predicate.apply(list.head())
                    ? suspend(() -> dropWhile(list.tail(), predicate))
                    : ret(list);
        }

        private TailCall<List<A>> drop(List<A> list, int n) {
            return n <= 0 || list.isEmpty()
                    ? ret(list)
                    : suspend(() -> drop(list.tail(), n - 1));
        }

        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
        }

        private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
            return list.isEmpty()
                    ? ret(acc)
                    : suspend(() -> toString(acc.append(list.head()).append(", "), list.tail()));
        }
    }

    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... elements) {
        List<A> n = list();
        for (int i = elements.length - 1; i >= 0; i--) {
            n = new Cons<>(elements[i], n);
        }
        return n;
    }
}
