package org.gym.fp.moderjava;

import org.gym.fp.moderjava.dsl.*;

import static java.lang.System.out;
import static org.gym.fp.moderjava.dsl.LambdaOrderBuilder.order;
import static org.gym.fp.moderjava.dsl.MethodChainingOrderBuilder.forCustomer;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.at;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.buy;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.on;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.order;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.sell;
import static org.gym.fp.moderjava.dsl.NestedFunctionOrderBuilder.stock;

public class DslTest {

    public static void main(String[] args) {
        doUnacceptableDslTest();
        doMethodChainingPatternTest();
        doNestedFunctionsPatternTest();
        doLambdaSequencingPatternTest();
        doMixedPatternTest();
        doUsingMethodReferenceDslTest();
    }

    private static void doUnacceptableDslTest() {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Trade.Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);
    }

    private static void doMethodChainingPatternTest() {
        Order order = forCustomer("BigBank")
                .buy(80)
                .stock("IBM")
                .on("NYSE")
                .at(125.00)
                .sell(50)
                .stock("Google")
                .on("NASDAQ")
                .at(375.00)
                .end();
        out.println("Order: " + order.toString());
        out.println("----------------------------------------");
    }

    private static void doNestedFunctionsPatternTest() {
        Order order = order("BigBank",
                buy(80,
                        stock("IBM", on("NYSE")),
                        at(125.00)),
                sell(50,
                        stock("Google", on("NASDAQ")),
                        at(375.00))
        );
        out.println("Order: " + order.toString());
        out.println("----------------------------------------");
    }

    private static void doLambdaSequencingPatternTest() {
        Order order = order(o -> {
            o.forCustomer("BigBank");
            o.buy(t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock(s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell(t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock(s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });
        out.println("Order: " + order.toString());
        out.println("----------------------------------------");
    }

    private static void doMixedPatternTest() {
        Order order = MixedBuilder.forCustomer("BigBank",
                MixedBuilder.buy(t -> t.quantity(80)
                                       .stock("IBM")
                                       .on("NYSE")
                                       .at(125.00)),
                MixedBuilder.sell(t -> t.quantity(50)
                                        .stock("Google")
                                        .on("NASDAQ")
                                        .at(375.00))
        );
        out.println("Order: " + order.toString());
        out.println("----------------------------------------");
    }

    private static void doUsingMethodReferenceDslTest() {
        Order order = MixedBuilder.forCustomer("BigBank",
                MixedBuilder.buy(t -> t.quantity(80)
                                       .stock("IBM")
                                       .on("NYSE")
                                       .at(125.00)),
                MixedBuilder.sell(t -> t.quantity(50)
                                        .stock("Google")
                                        .on("NASDAQ")
                                        .at(375.00))
        );
        double value = new TaxCalculator()
                .with(Tax::regional)
                .with(Tax::surcharge)
                .calculate(order);
        out.printf("Calculate value order with taxes: %.2f\n", value);
        out.println("----------------------------------------");
    }

}
