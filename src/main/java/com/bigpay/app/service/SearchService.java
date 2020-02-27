package com.bigpay.app.service;

import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.*;
import com.bigpay.app.domain.strategy.AbstractSearchStrategy;
import com.bigpay.app.domain.strategy.SearchStrategyFactory;
import com.bigpay.app.domain.strategy.StrategyType;

/**
 *
 */
public class SearchService {
    private static SearchService instance;

    public static synchronized SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }

        return instance;
    }

    /**
     *
     * @param roadMap
     */
    public void search(RoadMap roadMap) {

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        TimeStep timeStep = strategy.getNextTimeStep(roadMap);

        while (timeStep != null) {
            timeStepComponent.addTimeStep(timeStep);
            timeStepComponent.process();

            timeStep = strategy.getNextTimeStep(roadMap);
        }
    }
}
