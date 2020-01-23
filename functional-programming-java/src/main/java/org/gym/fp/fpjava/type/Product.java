package org.gym.fp.fpjava.type;

public class Product {
    public final String name;
    public final Price price;
    public final Weight weigth;

    public Product(String name, Price price, Weight weigth) {
        this.name = name;
        this.price = price;
        this.weigth = weigth;
    }
}
