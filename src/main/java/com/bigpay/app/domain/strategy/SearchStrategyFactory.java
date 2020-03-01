package com.bigpay.app.domain.strategy;

/**
 * Combination of Singleton, Factory method and Strategy pattern
 *
 * @author ggeorgiev
 */
public class SearchStrategyFactory {
    private static SearchStrategyFactory instance;

    private SearchStrategyFactory() {

    }

    /**
     *
     * @return gets single instance of this class
     */
    public static synchronized SearchStrategyFactory getInstance() {
        if (instance == null) {
            instance = new SearchStrategyFactory();
        }

        return instance;
    }

    /**
     * Chooses strategy that has be performed. There is only one strategy, but in future there could be added more strategies
     *
     * @param strategyType type of strategy
     * @return instance of type of strategy
     */
    public AbstractSearchStrategy getStrategy(StrategyType strategyType) {
        if (strategyType == StrategyType.GREEDY) {
            return new GreedySearchStrategy();
        }

        return null;
    }
}
