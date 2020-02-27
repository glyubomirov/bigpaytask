package com.bigpay.app.domain.strategy;

public class SearchStrategyFactory {
    private static SearchStrategyFactory instance;

    public static synchronized SearchStrategyFactory getInstance() {
        if (instance == null) {
            instance = new SearchStrategyFactory();
        }

        return instance;
    }

    public AbstractSearchStrategy getStrategy(StrategyType strategyType) {
        if (strategyType == StrategyType.GREEDY) {
            return new GreedySearchStrategy();
        }

        return null;
    }
}
