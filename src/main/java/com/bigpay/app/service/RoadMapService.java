package com.bigpay.app.service;

import com.bigpay.app.domain.*;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.domain.input.StationInputData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoadMapService {

    /**
     * Generates relational object data from input object data
     *
     * @param inputDataMap
     * @return
     */
    public static RoadMap generate(InputDataMap inputDataMap) {

        // Generates Station relation object from Station input data
        Station[] stations = Arrays.stream(inputDataMap.getStationList()).
                map(station -> new Station(station.getName())).toArray(Station[]::new);

        // Generate Map(Station Name -> Station Object)
        Map<Character, Station> stationMap = new HashMap<>();

        StationInputData[] stationInputData = inputDataMap.getStationList();

        for (int i = 0; i < stationInputData.length; i++) {
            stationMap.put(stationInputData[i].getName(), stations[i]);
        }

        // Generates Road relation object from Road input data
        Road[] roads = Arrays.stream(inputDataMap.getRoadList()).map(road -> new Road(
                road.getTime(),
                stationMap.get(road.getSourceStation()),
                stationMap.get(road.getTargetStation()))).
                toArray(Road[]::new);

        // Generates Road relation object from Road input data
        Letter[] letters = Arrays.stream(inputDataMap.getLetterList()).map(letter -> new Letter(
                letter.getName(),
                stationMap.get(letter.getSourceStation()),
                stationMap.get(letter.getTargetStation()),
                letter.getWeight())).
                toArray(Letter[]::new);

        // Generates Road relation object from Road input data
        Train[] trains = Arrays.stream(inputDataMap.getTrainList()).map(train -> new Train(
                train.getName(),
                stationMap.get(train.getStation()),
                train.getCapacity())).
                toArray(Train[]::new);

        Arrays.stream(roads).forEach(road -> {
            road.getStations().forEach(station -> {
                station.addRoad(road);
            });
        });

        Arrays.stream(letters).forEach(letter -> {
            letter.getInitialDest().loadLetters(Set.of(letter));
        });

        Arrays.stream(trains).forEach(train -> {
            train.getStation().addTrains(Set.of(train));
        });

        return new RoadMap(stations, roads, letters, trains);
    }
}
