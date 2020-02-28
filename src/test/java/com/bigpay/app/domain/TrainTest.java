package com.bigpay.app.domain;

import org.junit.Test;

public class TrainTest {

    @Test
    public void test() {
//        InputDataMap inputDataMap = InputDataMapHelper.getSimpleDataMap();
//        RoadMap roadMap = RoadMapService.generate(inputDataMap);
//
//        Station[] stations = roadMap.getStations();
//        Road[] roads = roadMap.getRoads();
//        Train train = roadMap.getTrains()[0];
//        Letter letter = roadMap.getLetters()[0];
//
//        // Checks initial train, station and letters state at Station S
//        assertEquals(stations[12].getTrains().size(), 1);
//        assertEquals(stations[12].getLetters().size(), 1);
//        assertEquals(train.getLetters().size(), 0);
//
//        // Train departs from station S
//        train.depart(roads[4], Set.of(letter));
//
//        // Checks train, station and letters state on the road to station S-B
//        assertEquals(stations[12].getLetters().size(), 0);
//        assertEquals(train.getLetters().size(), 1);
//        assertEquals(train.getTimeOnRoad(), 1);
//
//        // Trains moves and arrives at station B
//        train.move();
//
//        // Checks train, station and letters state at station B
//        assertEquals(train.getStation(), stations[1]);
//        assertNull(train.getNextStation());
//        assertEquals(train.getLetters().size(), 0);
//        assertEquals(train.getStation().getLetters().size(), 1);
//
//        // Train departs from station B and arrives at station H
//        train.depart(roads[5], Set.of(letter));
//
//        // Train departs from station H to station G
//        train.depart(roads[12], Set.of(letter));
//
//        // Trains moves and arrives at station G
//        train.move();
//
//        // Train departs from station G to station E
//        train.depart(roads[9], Set.of(letter));
//
//        // Trains moves and arrives at station E
//        train.move();
//
//        // Checks that game is finished
//        assertTrue(GameComponent.getInstance().finished(Set.of(roadMap.getLetters())));
//
//        TrackableAction track = ActionTrackerComponent.getInstance().poll();
//        while (track != null) {
//            System.out.println(track.toString());
//            track = ActionTrackerComponent.getInstance().poll();
//        }
    }
}
