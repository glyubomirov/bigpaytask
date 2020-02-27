package com.bigpay.app.domain.strategy;

import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.action.TimeStep;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.helper.InputDataMapHelper;
import com.bigpay.app.service.RoadMapService;
import org.junit.Test;


public class GreedySearchStrategyTest {

    @Test
    public void search() {

        InputDataMap inputDataMap = InputDataMapHelper.getExtendedDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        TimeStep timeStep = strategy.getNextTimeStep(roadMap);

        timeStepComponent.addTimeStep(timeStep);

        timeStepComponent.process();

    }
}
