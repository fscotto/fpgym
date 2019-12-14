package org.gym.fp.moderjava.stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

// Collector<T, A, R> =>
//      T = tipo generico degli elementi nello stream
//      A = tipo dell'accumulatore, risultato parziale della riduzione
//      R = tipo dell'oggetto risultato dall'operazione
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * @return l'accumulatore vuoto
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * @return la funzione che accumula gli elementi T in A
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /**
     * Questo metodo è invocato per combinare gli accumulatori
     * elaborati da diversi sotto-stream
     *
     * @return la combinazione degli accumulatori
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * Questo metodo viene invocato al termine del processo
     * di accumulazione degli elementi dello {@link java.util.stream.Stream}
     *
     * @return la funzione che trasforma A in R
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    /**
     * @return insieme dei comportamenti che il collector deve assumere
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }

}
