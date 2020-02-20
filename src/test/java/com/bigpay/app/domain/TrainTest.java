package com.bigpay.app.domain;

import com.bigpay.app.domain.input.*;
import org.junit.Test;

public class TrainTest {

    @Test
    public void departureTest() {
        InputDataMap inputDataMap = new InputDataMap(
                new StationInputData[]{
                        new StationInputData('A'),
                        new StationInputData('B'),
                        new StationInputData('c')},
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
    }
}
