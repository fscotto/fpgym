package org.gym.fp.moderjava;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Trader {
    private final String name;
    private final String city;
}
