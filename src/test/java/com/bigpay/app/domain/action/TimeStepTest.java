package com.bigpay.app.domain.action;

import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.domain.strategy.AbstractSearchStrategy;
import com.bigpay.app.domain.strategy.SearchStrategyFactory;
import com.bigpay.app.domain.strategy.StrategyType;
import com.bigpay.app.helper.InputDataMapHelper;
import com.bigpay.app.service.RoadMapService;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeStepTest {

    @Test
    public void validateSimpleMap() {

        InputDataMap inputDataMap = InputDataMapHelper.getSimpleDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        TimeStep timeStep;

        while(!TimeStepComponent.isFinish(roadMap)) {
            timeStep = strategy.getNextTimeStep(roadMap);
            timeStepComponent.process(timeStep);
            timeStep.print();
        }

        assertTrue(TimeStepComponent.isFinish(roadMap));

    }

    @Test
    public void validateExtendedMap() {

        InputDataMap inputDataMap = InputDataMapHelper.getExtendedDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        TimeStep timeStep;

        while(!TimeStepComponent.isFinish(roadMap)) {
            timeStep = strategy.getNextTimeStep(roadMap);
            timeStepComponent.process(timeStep);
            timeStep.print();
        }

        assertTrue(TimeStepComponent.isFinish(roadMap));

    }
}
