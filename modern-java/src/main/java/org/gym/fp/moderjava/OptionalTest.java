package org.gym.fp.moderjava;

import org.gym.fp.moderjava.optional.Car;
import org.gym.fp.moderjava.optional.Insurance;
import org.gym.fp.moderjava.optional.Person;

import java.util.Optional;

import static java.lang.System.out;

public class OptionalTest {

    public static void main(String[] args) {
        Insurance myInsurance1 = new Insurance("Allianz");
        Car myCar1 = new Car(Optional.of(myInsurance1));
        Person person1 = new Person(Optional.of(myCar1));
        out.println("My insurance's name is " + getCarInsuranceName(Optional.ofNullable(person1)));
        out.println("----------------------------------------");
        Car myCar2 = new Car(Optional.ofNullable(null));
        Person person2 = new Person(Optional.ofNullable(myCar2));
        out.println("My insurance's name is " + getCarInsuranceName(Optional.ofNullable(person2)));
        out.println("----------------------------------------");
    }

    static String getCarInsuranceName(Optional<Person> maybePerson) {
        return maybePerson
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}
