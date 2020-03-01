package com.bigpay.app.service;

import com.bigpay.app.domain.*;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.domain.input.StationInputData;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generates road map based on input data
 *
 * @author ggeorgiev
 */
public class RoadMapService {

    /**
     * Generates relational object data from input object data
     *
     * @param inputDataMap input data map
     * @return Generated road map from input data
     */
    public static RoadMap generate(InputDataMap inputDataMap) {

        // Generates Station relation object from Station input data
        StationInputData[] stationInputData = inputDataMap.getStationList();

        Station[] stations = IntStream.range(0, stationInputData.length)
                .mapToObj(i -> new Station(stationInputData[i].getName(), i)).toArray(Station[]::new);

        // Generate Map(Station Name -> Station Object)
        Map<String, Station> stationMap = Arrays.stream(stations)
                .collect(Collectors.toMap(Station::getName, Function.identity()));

        // Generates Road relation object from Road input data
        Road[] roads = Arrays.stream(inputDataMap.getRoadList())
                .map(road -> new Road(
                road.getTimeSteps(),
                stationMap.get(road.getSourceStation()),
                stationMap.get(road.getTargetStation()))).
                toArray(Road[]::new);

        // Generates Road relation object from Road input data
        Letter[] letters = Arrays.stream(inputDataMap.getLetterList())
                .map(letter -> new Letter(
                letter.getName(),
                stationMap.get(letter.getSourceStation()),
                stationMap.get(letter.getTargetStation()),
                letter.getWeight())).
                toArray(Letter[]::new);

        // Generates Road relation object from Road input data
        Train[] trains = Arrays.stream(inputDataMap.getTrainList())
                .map(train -> new Train(
                train.getName(),
                stationMap.get(train.getStation()),
                train.getCapacity())).
                toArray(Train[]::new);

        // Bind roads to each Station
        Arrays.stream(roads).forEach(road -> {
            road.getStations().forEach(station -> {
                station.addRoad(road);
            });
        });

        // Bind letters to each Station
        Arrays.stream(letters).forEach(letter -> {
            letter.getInitialDest().unload(Set.of(letter));
        });

        return new RoadMap(stations, roads, letters, trains);
    }
}
