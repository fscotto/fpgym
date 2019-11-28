package org.gym.fp.moderjava.optional;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Insurance {

    @Getter
    private String name;

    public Insurance(String name) {
        this.name = name;
    }

}
