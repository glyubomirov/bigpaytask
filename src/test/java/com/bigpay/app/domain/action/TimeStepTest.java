package com.bigpay.app.domain.action;

import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.domain.strategy.AbstractSearchStrategy;
import com.bigpay.app.domain.strategy.SearchStrategyFactory;
import com.bigpay.app.domain.strategy.StrategyType;
import com.bigpay.app.helper.InputDataMapHelper;
import com.bigpay.app.service.RoadMapService;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TimeStepTest {

    @Test
    public void validateExtendedStep() {

        InputDataMap inputDataMap = InputDataMapHelper.getExtendedDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        TimeStep timeStep = strategy.getNextTimeStep(roadMap);

        timeStepComponent.addTimeStep(timeStep);

        assertTrue(timeStepComponent.getTimeStepList().size() > 0);

        List<TrainAction> trainActionList = timeStepComponent.getTimeStepList().get(0).getActionList();

        assertEquals(10, trainActionList.size());

        Map<Train, List<TrainActionable>> trainActionMap = timeStepComponent.getTimeStepList().get(0).getActionMap();

        // Expects 4 trains
        assertEquals(4, trainActionMap.size());

        // Expects T1 train actions
        assertEquals(4, trainActionMap.get(roadMap.getTrains()[0]).size());
        assertTrue(trainActionMap.get(roadMap.getTrains()[0]).get(0) instanceof TrainLoadActionType);
        assertTrue(Set.of(roadMap.getLetters()[0], roadMap.getLetters()[1]).contains(((TrainLoadActionType)trainActionMap.get(roadMap.getTrains()[0]).get(0)).getLetter()));
        assertTrue(trainActionMap.get(roadMap.getTrains()[0]).get(1) instanceof TrainLoadActionType);
        assertTrue(Set.of(roadMap.getLetters()[0], roadMap.getLetters()[1]).contains(((TrainLoadActionType)trainActionMap.get(roadMap.getTrains()[0]).get(1)).getLetter()));
        assertTrue(trainActionMap.get(roadMap.getTrains()[0]).get(2) instanceof TrainDepartActionType);
        assertEquals(roadMap.getRoads()[4], ((TrainDepartActionType)trainActionMap.get(roadMap.getTrains()[0]).get(2)).getRoad());
        assertTrue(trainActionMap.get(roadMap.getTrains()[0]).get(3) instanceof TrainMoveActionType);

        // Expects T2 train actions
        assertEquals(2, trainActionMap.get(roadMap.getTrains()[1]).size());
        assertTrue(trainActionMap.get(roadMap.getTrains()[1]).get(0) instanceof TrainDepartActionType);
        assertEquals(roadMap.getRoads()[15], ((TrainDepartActionType)trainActionMap.get(roadMap.getTrains()[1]).get(0)).getRoad());
        assertTrue(trainActionMap.get(roadMap.getTrains()[1]).get(1) instanceof TrainMoveActionType);

        // Expects T3 train actions
        assertEquals(2, trainActionMap.get(roadMap.getTrains()[2]).size());
        assertTrue(trainActionMap.get(roadMap.getTrains()[2]).get(0) instanceof TrainDepartActionType);
        assertEquals(roadMap.getRoads()[17], ((TrainDepartActionType)trainActionMap.get(roadMap.getTrains()[2]).get(0)).getRoad());
        assertTrue(trainActionMap.get(roadMap.getTrains()[2]).get(1) instanceof TrainMoveActionType);

        // Expects T4 train actions
        assertEquals(2, trainActionMap.get(roadMap.getTrains()[3]).size());
        assertTrue(trainActionMap.get(roadMap.getTrains()[3]).get(0) instanceof TrainDepartActionType);
        assertEquals(roadMap.getRoads()[7], ((TrainDepartActionType)trainActionMap.get(roadMap.getTrains()[3]).get(0)).getRoad());
        assertTrue(trainActionMap.get(roadMap.getTrains()[3]).get(1) instanceof TrainMoveActionType);

        // Process Time Step
        timeStepComponent.process();

        // Train T1 assertions
        assertEquals(2, roadMap.getTrains()[0].getLetters().size());
        assertTrue(roadMap.getTrains()[0].getLetters().contains(roadMap.getLetters()[0]));
        assertTrue(roadMap.getTrains()[0].getLetters().contains(roadMap.getLetters()[1]));
        assertNull(roadMap.getTrains()[0].getStation());
        assertEquals(roadMap.getTrains()[0].getNextStation(), roadMap.getStations()[1]);
        assertEquals(roadMap.getRoads()[4], roadMap.getTrains()[0].getRoad());
        assertEquals(1, roadMap.getTrains()[0].getTimeOnRoad());
        assertEquals(5, roadMap.getTrains()[0].getFreeCapacity());

        //Station S
        assertFalse(roadMap.getStations()[12].getLetters().contains(roadMap.getLetters()[0]));
        assertFalse(roadMap.getStations()[12].getLetters().contains(roadMap.getLetters()[1]));
        assertTrue(roadMap.getStations()[12].getLetters().contains(roadMap.getLetters()[2]));

        // Train T2 assertions
        assertEquals(0, roadMap.getTrains()[1].getLetters().size());
        assertNull(roadMap.getTrains()[1].getStation());
        assertEquals(roadMap.getStations()[11], roadMap.getTrains()[1].getNextStation());
        assertEquals(roadMap.getRoads()[15], roadMap.getTrains()[1].getRoad());
        assertEquals(1, roadMap.getTrains()[1].getTimeOnRoad());
        assertEquals(6, roadMap.getTrains()[1].getFreeCapacity());

        // Train T3 assertions
        assertEquals(0, roadMap.getTrains()[2].getLetters().size());
        assertNull(roadMap.getTrains()[2].getStation());
        assertEquals(roadMap.getStations()[11], roadMap.getTrains()[2].getNextStation());
        assertEquals(roadMap.getRoads()[17], roadMap.getTrains()[2].getRoad());
        assertEquals(1, roadMap.getTrains()[2].getTimeOnRoad());
        assertEquals(8, roadMap.getTrains()[2].getFreeCapacity());

        // Train T4 assertions
        assertEquals(0, roadMap.getTrains()[3].getLetters().size());
        assertNull(roadMap.getTrains()[3].getStation());
        assertEquals(roadMap.getStations()[2], roadMap.getTrains()[3].getNextStation());
        assertEquals(roadMap.getRoads()[7], roadMap.getTrains()[3].getRoad());
        assertEquals(1, roadMap.getTrains()[3].getTimeOnRoad());
        assertEquals(5, roadMap.getTrains()[3].getFreeCapacity());

        // Letter D1
        assertFalse(roadMap.getLetters()[0].isArrived());
        assertTrue(roadMap.getLetters()[0].isInProcessing());

        // Letter D2
        assertFalse(roadMap.getLetters()[1].isArrived());
        assertTrue(roadMap.getLetters()[1].isInProcessing());

        // Letter D3
        assertFalse(roadMap.getLetters()[2].isArrived());
        assertFalse(roadMap.getLetters()[2].isInProcessing());

        //Add second time step and processes it
        timeStepComponent.addTimeStep(strategy.getNextTimeStep(roadMap));
        timeStepComponent.process();

        System.out.println(roadMap);
    }

    @Test
    public void validateSimpleStep() {

        InputDataMap inputDataMap = InputDataMapHelper.getSimpleDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        // Step 1
        TimeStep timeStep = strategy.getNextTimeStep(roadMap);
        timeStepComponent.addTimeStep(timeStep);
        timeStepComponent.process();

        // Step 2
        timeStep = strategy.getNextTimeStep(roadMap);
        timeStepComponent.addTimeStep(timeStep);
        timeStepComponent.process();

        // Step 3
        timeStep = strategy.getNextTimeStep(roadMap);
        timeStepComponent.addTimeStep(timeStep);
        timeStepComponent.process();

        System.out.println(roadMap);
    }
}
