package com.bigpay.app.domain;

import java.util.*;

/**
 * Represents geographical map of train stations (nodes) and railways(edges)
 */
public class RoadMap {
    /**
     * StationMatrix is 2D matrix representation of nodes and edges. Because app will need to work very often with 2D
     * matrix it will be represented as single N*N array
     */
    private int[] stationMatrix;

    /**
     * Number of rows in stationMatrix
     */
    private int stationMatrixRawCount;

    /**
     * List of all stations
     */
    private Station[] stationList;

    /**
     * List of all roads
     */
    private Road[] roadList;

    /**
     * List of all letters
     */
    private Letter[] letterList;

    /**
     * List of all trains
     */
    private Train[] trainList;

    /**
     * Constructor sets default values
     *
     * @param stationList
     * @param roadList
     * @param letterList
     * @param trainList
     */
    public RoadMap(Station[] stationList, Road[] roadList, Letter[] letterList, Train[] trainList) {
        this.stationList = stationList.clone();
        this.roadList = roadList.clone();
        this.letterList = letterList.clone();
        this.trainList = trainList.clone();

        this.stationMatrixRawCount = stationList.length;
        this.stationMatrix = this.generateStationMatrix(stationList, roadList);
    }

    /**
     *
     * Generates station matrix as 1D array for fast element access
     *
     * @return
     */
    private int[] generateStationMatrix(Station[] stationList, Road[] roadList) {
        int []roadMatrix = new int[stationList.length * stationList.length];

        /**
         * Generate Map(Station -> Station Index)
         */
        Map<String, Integer> stationMap = new HashMap<>();

        for (int i = 0; i < stationList.length; i++) {
            stationMap.put(stationList[i].getName(), i);
        }

        /**
         * Represents 2D matrix Station matrix as 1D array
         */
        Arrays.stream(roadList).forEach(road ->{
            Station[] stations = road.getStations().toArray(Station[]::new);
            int i = stationMap.get(stations[0].getName()); // get i coordinate of the matrix
            int j = stationMap.get(stations[1].getName()); // get j coordinate of the matrix
            roadMatrix[i * stationList.length + j] = road.getTime();
            roadMatrix[i + j * stationList.length] = road.getTime();
        });

        return roadMatrix;
    }

    public Station[] getStations() {
        return this.stationList.clone();
    }

    public Road[] getRoads() {
        return roadList.clone();
    }

    public Letter[] getLetters() {
        return letterList.clone();
    }

    public Train[] getTrains() {
        return trainList.clone();
    }

    public int[] getStationMatrix() {
        return stationMatrix.clone();
    }

    public int getStationMatrixRawCount() {
        return stationMatrixRawCount;
    }
}
