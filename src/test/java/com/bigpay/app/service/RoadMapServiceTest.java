package com.bigpay.app.service;

import com.bigpay.app.domain.*;
import com.bigpay.app.domain.input.*;
import com.bigpay.app.helper.InputDataMapHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoadMapServiceTest {

    @Test
    public void testRoadMapService() {

        InputDataMap inputDataMap = InputDataMapHelper.getSimpleDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        Station[] stations = roadMap.getStations();
        Road[] roads = roadMap.getRoads();
        Letter[] letters = roadMap.getLetters();
        Train[] trains = roadMap.getTrains();

        assertEquals(13, stations.length);

        assertEquals(18, roads.length);

        assertEquals(1, letters.length);

        assertEquals(1, trains.length);

        assertEquals(3, stations[0].getRoads().size());

        assertTrue(stations[0].getRoads().contains(roads[0]));
        assertTrue(stations[0].getRoads().contains(roads[1]));
        assertTrue(stations[0].getRoads().contains(roads[2]));

        assertTrue(roads[0].getStations().contains(stations[0]));
        assertTrue(roads[0].getStations().contains(stations[1]));
    }
}
