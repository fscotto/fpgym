package org.gym.fp.moderjava;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class Dish {
    private final String name;
    private final int calories;
    private final Type type;

    public enum Type {
        MEAT, FISH, VEGETARIAN, OTHER
    }

    public boolean isVegetarian() {
        return this.type == Type.VEGETARIAN;
    }

    @Override
    public String toString() {
        return name;
    }

}
