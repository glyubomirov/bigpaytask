package com.bigpay.app.service;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.input.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoadMapServiceTest {

    @Test
    public void generate() {
        InputDataMap inputDataMap = new InputDataMap(
                new StationInputData[]{
                        new StationInputData('A'),
                        new StationInputData('B'),
                        new StationInputData('C')},
                new RoadInputData[]{
                        new RoadInputData('A', 'B', 3),
                        new RoadInputData('B', 'C', 1)
                },
                new LetterInputData[]{
                        new LetterInputData("D1", 'A', 'C', 5)
                },
                new TrainInputData[]{
                        new TrainInputData("T1", 'B', 6)
                }
        );

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        assertEquals(roadMap.getStations().size(), 3);

        assertEquals(roadMap.getRoads().size(), 2);

        assertEquals(roadMap.getLetters().size(), 1);

        assertEquals(roadMap.getTrains().size(), 1);
    }
}
