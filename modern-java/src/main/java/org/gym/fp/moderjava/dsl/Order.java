package org.gym.fp.moderjava.dsl;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Order {
    private String customer;
    private List<Trade> trades = new ArrayList<>();

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getValue() {
        return trades.stream()
                     .mapToDouble(Trade::getValue)
                     .sum();
    }

}
