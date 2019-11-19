package org.gym.fp.moderjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class StreamTest {

    public static void main(String[] args) {
        doStreamFilterDemo();
        doStreamMappingDemo();
    }

    private static void doStreamFilterDemo() {
        out.println("FILTER EXAMPLE");
        getDishes().stream()
                   .filter(Dish::isVegetarian)
                   .collect(Collectors.toList())
                   .forEach(out::println);
        out.println("----------------------------------------");
        out.println("DISTINCT EXAMPLE");
        getDishes().stream()
                   .filter(dish -> dish.getName().length() > 4)
                   .distinct()
                   .forEach(out::println);
        out.println("----------------------------------------");
        out.println("TAKEWHILE EXAMPLE");
        getDishes().stream()
                   .takeWhile(dish -> dish.getCalories() > 40)
                   .forEach(out::println);
        out.println("----------------------------------------");
        out.println("DROPWHILE EXAMPLE");
        getDishes().stream()
                   .dropWhile(dish -> dish.getCalories() > 40)
                   .forEach(out::println);
        out.println("----------------------------------------");
    }

    private static void doStreamMappingDemo() {
        List<String> words = Arrays.asList("Hello", "World");
        List<String> uniqueCharacters = words.stream()
                                             .map(word -> word.split(""))
                                             .flatMap(Arrays::stream)
                                             .distinct()
                                             .collect(Collectors.toList());
        out.println(uniqueCharacters);
        out.println("----------------------------------------");
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs1 = numbers1.stream()
                                     .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                                     .collect(Collectors.toList());
        pairs1.forEach(pair -> out.println(Arrays.toString(pair)));
        out.println("----------------------------------------");
        List<int[]> pairs2 = numbers1.stream()
                                     .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                                     .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                     .collect(Collectors.toList());
        pairs2.forEach(pair -> out.println(Arrays.toString(pair)));
    }

    private static List<Dish> getDishes() {
        List<Dish> dishes = Arrays.asList(
                new Dish("pork", 300, Dish.Type.MEAT),
                new Dish("salad", 50, Dish.Type.VEGETARIAN),
                new Dish("chicken", 100, Dish.Type.MEAT),
                new Dish("chicken", 100, Dish.Type.MEAT),
                new Dish("tomato", 30, Dish.Type.VEGETARIAN),
                new Dish("tunny", 120, Dish.Type.FISH),
                new Dish("potato", 70, Dish.Type.VEGETARIAN)
                                         );
        return Collections.unmodifiableList(dishes);
    }

}
