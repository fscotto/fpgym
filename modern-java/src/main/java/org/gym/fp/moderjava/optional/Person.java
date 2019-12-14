package org.gym.fp.moderjava.optional;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Person {

    @Getter
    private Optional<Car> car;

    public Person(Optional<Car> car) {
        this.car = car;
    }

}
