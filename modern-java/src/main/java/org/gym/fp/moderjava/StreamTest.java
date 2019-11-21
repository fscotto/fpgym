package org.gym.fp.moderjava;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamTest {

    public static void main(String[] args) {
        doStreamFilterDemo();
        doStreamMappingDemo();
        doStreamFindOrMatchingDemo();
        doStreamReducingDemo();
        doStreamCollectingDemo();
    }

    private static void doStreamFilterDemo() {
        out.println("FILTER EXAMPLE");
        getDishes().stream()
                   .filter(Dish::isVegetarian)
                   .collect(toList())
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
        List<String> words = asList("Hello", "World");
        List<String> uniqueCharacters = words.stream()
                                             .map(word -> word.split(""))
                                             .flatMap(Arrays::stream)
                                             .distinct()
                                             .collect(toList());
        out.println(uniqueCharacters);
        out.println("----------------------------------------");
        out.println("Permutations");
        List<Integer> numbers1 = asList(1, 2, 3);
        List<Integer> numbers2 = asList(3, 4);
        List<int[]> pairs1 = numbers1.stream()
                                     .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                                     .collect(toList());
        pairs1.forEach(pair -> out.println(Arrays.toString(pair)));
        out.println("----------------------------------------");
        List<int[]> pairs2 = numbers1.stream()
                                     .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                                     .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                     .collect(toList());
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
        List<Integer> someNumbers = asList(1, 2, 3, 4, 5);
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
        List<Transaction> transactions = asList(
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
                    .collect(toList())
                    .forEach(out::println);
        out.println("2)");
        transactions.stream()
                    .map(Transaction::getTrader)
                    .map(Trader::getCity)
                    .distinct()
                    .collect(toList())
                    .forEach(out::println);
        out.println("3)");
        transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(trader -> "Cambridge".equalsIgnoreCase(trader.getCity()))
                    .distinct() // anche se sul testo non è specificato che dovevano essere diversi
                    .sorted(Comparator.comparing(Trader::getName))
                    .collect(toList())
                    .forEach(out::println);
        out.println("4)");
        String result = transactions.stream()
                                    .map(Transaction::getTrader)
                                    .map(Trader::getName)
                                    .distinct()
                                    .sorted()
                                    .reduce("", (acc, name) -> acc + name);
        out.println(result);
        out.println("5)");
        boolean anyTraderInMilan = transactions.stream()
                                               .map(Transaction::getTrader)
                                               .anyMatch(trader -> "Milan".equalsIgnoreCase(trader.getCity()));
        out.println(anyTraderInMilan);
        out.println("6)");
        transactions.stream()
                    .filter(transaction -> "Cambridge".equalsIgnoreCase(transaction.getTrader().getCity()))
                    .forEach(transaction -> out.println(transaction.getValue()));
        out.println("7)");
        transactions.stream()
                    .mapToInt(Transaction::getValue)
                    .max()
                    .ifPresent(out::println);
        out.println("8)");
        transactions.stream()
                    .mapToInt(Transaction::getValue)
                    .min()
                    .ifPresent(out::println);
        out.println("----------------------------------------");
        IntStream.rangeClosed(1, 100)
                 .boxed()
                 .flatMap(a -> IntStream.rangeClosed(a, 100)
                                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                                        .filter(t -> t[2] % 1 == 0)
                 )
                 .limit(5)
                 .forEach(triple -> out.printf("(%.0f, %.0f, %.0f)\n", triple[0], triple[1], triple[2]));
        out.println("----------------------------------------");
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
              .limit(20)
              .forEach(pair -> out.printf("(%d, %d) ", pair[0], pair[1]));
        out.println();
        out.println("----------------------------------------");
    }

    private static void doStreamCollectingDemo() {
        List<Dish> menu = getDishes();
        long howManyDishes = menu.stream().count();
        out.println(howManyDishes);
        out.println("----------------------------------------");
        Comparator<Dish> dishCaloriesComparator = comparingInt(Dish::getCalories);
        menu.stream()
            .collect(maxBy(dishCaloriesComparator))
            .ifPresent(out::println);
        out.println("----------------------------------------");
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        out.println(totalCalories);
        out.println("----------------------------------------");
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        out.println(avgCalories);
        out.println("----------------------------------------");
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        out.println(menuStatistics);
        out.println("----------------------------------------");
        String shortMenu = menu.stream()
                               .map(Dish::getName)
                               .collect(joining(", "));
        out.println(shortMenu);
        out.println("----------------------------------------");
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        out.println(dishesByType);
        out.println("----------------------------------------");
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));
        out.println(dishesByCaloricLevel);
        out.println("----------------------------------------");
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                menu.stream()
                    .collect(groupingBy(Dish::getType,
                            filtering(dish -> dish.getCalories() > 500,
                                    toList())));
        out.println(caloricDishesByType);
        out.println("----------------------------------------");
        Map<Dish.Type, List<String>> dishNamesByType =
                menu.stream()
                    .collect(groupingBy(Dish::getType,
                            mapping(Dish::getName, toList())));
        out.println(dishNamesByType);
        out.println("----------------------------------------");
        Map<String, List<String>> dishTags = Map.of(
                "pork", asList("greasy", "salty"),
                "beef", asList("salty", "roasted"),
                "chicken", asList("fried", "crisp"),
                "french fries", asList("greasy", "fried"),
                "rice", asList("light", "natural"),
                "season fruit", asList("fresh", "natural"),
                "pizza", asList("tasty", "salty"),
                "prawns", asList("tasty", "roasted"),
                "salmon", asList("delicious", "fresh")
        );
        Map<Dish.Type, Set<String>> dishNamesByType2 =
                menu.stream()
                    .collect(groupingBy(Dish::getType,
                            flatMapping(dish -> {
                                // FIXME: The example of book is not safety
                                List<String> tags = dishTags.get(dish.getName());
                                if (tags != null) {
                                    return tags.stream();
                                }
                                return Stream.empty();
                            }, toSet())));
        out.println(dishNamesByType2);
        out.println("----------------------------------------");
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream()
                    .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                        if (dish.getCalories() <= 400)
                            return CaloricLevel.DIET;
                        else if (dish
                                .getCalories() <= 700)
                            return CaloricLevel.NORMAL;
                        else
                            return CaloricLevel.FAT;
                    })));
        out.println(dishesByTypeCaloricLevel);
        out.println("----------------------------------------");
    }

    enum CaloricLevel {DIET, NORMAL, FAT}

    private static List<Dish> getDishes() {
        List<Dish> dishes = asList(
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
