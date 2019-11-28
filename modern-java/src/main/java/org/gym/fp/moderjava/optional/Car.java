package org.gym.fp.moderjava.optional;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Car {

    @Getter
    private Optional<Insurance> insurance;

    public Car(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
