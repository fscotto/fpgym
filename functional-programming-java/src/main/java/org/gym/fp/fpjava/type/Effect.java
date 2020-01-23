package org.gym.fp.fpjava.type;

public interface Effect<T> {
    void apply(T t);
}
