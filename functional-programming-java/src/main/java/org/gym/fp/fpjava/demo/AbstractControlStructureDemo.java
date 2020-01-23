package org.gym.fp.fpjava.demo;

import org.gym.fp.fpjava.type.Effect;
import org.gym.fp.fpjava.type.Function;
import org.gym.fp.fpjava.type.Result;

import java.util.regex.Pattern;

import static org.gym.fp.fpjava.type.Case.match;
import static org.gym.fp.fpjava.type.Case.when;

public class AbstractControlStructureDemo {

    public static void run() {
        doEmailValidationDemo();
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
