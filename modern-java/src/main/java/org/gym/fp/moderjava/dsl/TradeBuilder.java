package org.gym.fp.moderjava.dsl;

public class TradeBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade = new Trade();

    public TradeBuilder(MethodChainingOrderBuilder builder,
                        Trade.Type buy, int quantity) {
        this.builder = builder;
        this.trade.setType(buy);
        this.trade.setQuantity(quantity);
    }

    public StockBuilder stock(String symbol) {
        return new StockBuilder(builder, trade, symbol);
    }

}
