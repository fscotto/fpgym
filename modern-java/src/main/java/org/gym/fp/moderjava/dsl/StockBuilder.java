package org.gym.fp.moderjava.dsl;

public class StockBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    public StockBuilder(MethodChainingOrderBuilder builder,
                        Trade trade, String symbol) {
        this.builder = builder;
        this.trade = trade;
        this.stock.setSymbol(symbol);
    }

    public TradeBuilderWithStock on(String market) {
        this.stock.setMarket(market);
        trade.setStock(this.stock);
        return new TradeBuilderWithStock(builder, trade);
    }

}
