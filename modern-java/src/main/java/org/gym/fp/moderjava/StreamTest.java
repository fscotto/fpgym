package org.gym.fp.moderjava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class StreamTest {

    public static void main(String[] args) {
        doStreamFilterDemo();
        doStreamMappingDemo();
        doStreamFindOrMatchingDemo();
        doStreamReducingDemo();
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
        out.println("Permutations");
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
        out.println("----------------------------------------");
    }

    private static void doStreamFindOrMatchingDemo() {
        out.println("ANYMATCH EXAMPLE");
        if (getDishes().stream().anyMatch(Dish::isVegetarian)) {
            out.println("The menu is (somewhat) vegetarian friendly!!!");
        }
        out.println("----------------------------------------");
        out.println("ALLMATCH EXAMPLE");
        if (getDishes().stream().allMatch(dish -> dish.getCalories() < 1000)) {
            out.println("This menù is healthy");
        }
        out.println("----------------------------------------");
        out.println("NONEMATCH EXAMPLE");
        if (getDishes().stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            out.println("This menù is healthy");
        }
        out.println("----------------------------------------");
        out.println("FINDANY EXAMPLE");
        Optional<Dish> dish = getDishes().stream()
                                         .filter(Dish::isVegetarian)
                                         .findAny();
        dish.ifPresent(out::println);
        out.println("----------------------------------------");
        out.println("FINDFIRST EXAMPLE");
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        someNumbers.stream()
                   .map(n -> n * n)
                   .filter(n -> n % 3 == 0)
                   .findFirst()
                   .ifPresent(out::println);
        out.println("----------------------------------------");
    }

    private static void doStreamReducingDemo() {
        out.println("REDUCING");
        int sum = Stream.of(4, 5, 3, 9).reduce(0, Integer::sum);
        out.println(sum);
        out.println("----------------------------------------");
        out.println("MAX & MIN");
        Stream.of(4, 5, 3, 9).reduce(Integer::max).ifPresent(out::println);
        Stream.of(4, 5, 3, 9).reduce(Integer::min).ifPresent(out::println);
        out.println("----------------------------------------");
        out.println("COUNT DISHES");
        getDishes().stream()
                   .map(d -> 1)
                   .reduce(Integer::sum)
                   .ifPresent(out::println);
        out.println("----------------------------------------");
        out.println("PUTTING ALL IN PRACTICE");
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        out.println("1)");
        transactions.stream()
                    .filter(t -> t.getYear() == 2011)
                    .sorted(Comparator.comparing(Transaction::getValue))
                    .collect(Collectors.toList())
                    .forEach(out::println);
        out.println("2)");
        transactions.stream()
                    .map(Transaction::getTrader)
                    .map(Trader::getCity)
                    .distinct()
                    .collect(Collectors.toList())
                    .forEach(out::println);
        out.println("3)");
        transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(trader -> "Cambridge".equalsIgnoreCase(trader.getCity()))
                    .sorted(Comparator.comparing(Trader::getName))
                    .collect(Collectors.toList())
                    .forEach(out::println);

        out.println("----------------------------------------");
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
