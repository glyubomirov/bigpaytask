package com.bigpay.app;

import com.bigpay.app.domain.*;
import com.bigpay.app.service.RandomRoadMapService;

/**
 * This class helps to generate random road map
 *
 * @author ggeorgiev
 *
 */
public class InputGenerator {

    /**
     * Main method is used to generate random road map. This is out-of-scope helper class
     *
     * @param args not used
     */
    public static void main(String[] args) {

        // Generates input data
        RoadMap roadMap = RandomRoadMapService.generate(30, 100, 200,  10,1);

        // Print number of stations and information for each station
        System.out.println("// Stations");
        System.out.println(roadMap.getStations().length);
        for (Station station : roadMap.getStations()) {
            System.out.println(station.getName());
        }
        System.out.println();

        // Print number of roads and information for each road
        System.out.println("// Roads");
        System.out.println(roadMap.getRoads().length);
        for (Road road : roadMap.getRoads()) {
            System.out.println(String.format("%s,%s,%d", road.getStations().iterator().next().getName(), road.getCounterStation(road.getStations().iterator().next()).getName(), road.getTimeSteps()));
        }
        System.out.println();

        // Print number of letters and information for each letter
        System.out.println("// Letters");
        System.out.println(roadMap.getLetters().length);
        for (Letter letter : roadMap.getLetters()) {
            System.out.println(String.format("%s,%s,%s,%d", letter.getName(), letter.getInitialDest().getName(), letter.getFinalDest().getName(), letter.getWeight()));
        }
        System.out.println();

        // Print number of trains and information for each train
        System.out.println("// Trains");
        System.out.println(roadMap.getTrains().length);
        for (Train train : roadMap.getTrains()) {
            System.out.println(String.format("%s,%s,%d", train.getName(), train.getStation().getName(), train.getCapacity()));
        }
        System.out.println();
    }
}
