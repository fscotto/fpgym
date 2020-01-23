package org.gym.fp.fpjava.demo;

import org.gym.fp.fpjava.type.*;

import java.util.List;
import java.util.regex.Pattern;

import static org.gym.fp.fpjava.type.Case.match;
import static org.gym.fp.fpjava.type.Case.when;

public class AbstractControlStructureDemo {

    public static void run() {
        doEmailValidationDemo();
        doCollectionUtilitiesDemo();
        doStore();
    }

    private static void doEmailValidationDemo() {
        validate("this.is@my.email");
        validate(null);
        validate("");
        validate("john.doe@acme.com");
    }

    private static void validate(String email) {
        final EmailValidation validation = new EmailValidation();
        final Function<String, Result<String>> emailChecker = validation.emailChecker;
        final Effect<String> success = validation.success;
        final Effect<String> failure = validation.failure;
        emailChecker.apply(email).bind(success, failure);
    }

    private static void doCollectionUtilitiesDemo() {
        List<Integer> list = CollectionUtilities.list(1, 2, 3, 4, 5);
        String identity = "0";

        // Test foldLeft
        Function<String, Function<Integer, String>> f_1 = x -> y -> addSI(x, y);
        String result_1 = CollectionUtilities.foldLeft(list, identity, f_1);
        System.out.println(result_1);

        // Test foldRight
        Function<Integer, Function<String, String>> f_2 = x -> y -> addIS(x, y);
        String result_2 = CollectionUtilities.foldRightRec(list, identity, f_2);
        System.out.println(result_2);

        // Test reverse
        System.out.println(CollectionUtilities.reverse(list));
    }

    private static String addSI(String s, Integer i) {
        return "(" + s + " + " + i + ")";
    }

    private static String addIS(Integer i, String s) {
        return "(" + i + " + " + s + ")";
    }

    private static void doStore() {
        Product toothPaste = new Product("Tooth paste", Price.of(1.5), Weight.of(0.5));
        Product toothBrush = new Product("Tooth brush", Price.of(3.5), Weight.of(0.3));
        List<OrderLine> order = CollectionUtilities.list(
                new OrderLine(toothPaste, 2),
                new OrderLine(toothBrush, 3)
        );
        Price price = CollectionUtilities.foldLeft(order, Price.ZERO, Price.sum);
        Weight weight = CollectionUtilities.foldLeft(order, Weight.ZERO, Weight.sum);
        System.out.printf("Total price: %s\n", price);
        System.out.printf("Total weight: %s\n", weight);
    }
}

class EmailValidation {
    public final Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    public final Effect<String> success =
            email -> System.out.println("Verification mail sent to " + email);

    public final Effect<String> failure =
            msg -> System.err.println("Error message logged: " + msg);

    public final Function<String, Result<String>> emailChecker = s -> match(
            when(() -> Result.success(s)),
            when(() -> s == null, () -> Result.failure("email must not be null")),
            when(() -> s.length() == 0, () -> Result.failure("email must not be empty")),
            when(() -> !emailPattern.matcher(s).matches(), () -> Result.failure("email " + s + " is invalid")));
}
