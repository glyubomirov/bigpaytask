package com.bigpay.app.domain;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.component.exception.NonExistingPathException;

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
     *
     */
    private Road[][][] shortestRoadMap;

    /**
     *
     */
    private int[][] cashedPathMatrix;

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

        this.shortestRoadMap = FloydWarshallSearchComponent.getInstance().generateShortestPath(this);

        this.cashedPathMatrix = new int[stationMatrixRawCount][stationMatrixRawCount];

        this.calculatePathMatrix();
    }

    private void calculatePathMatrix() {
        // Checks is there is a path between every two Stations
        for (int i = 0; i < stationMatrixRawCount; i++) {
            for (int j = 0; j < stationMatrixRawCount; j++) {
                if (shortestRoadMap[i][j] == null) {
                    throw new NonExistingPathException(String.format("There is no path between %s and %s",
                            this.stationList[i].getName(), this.stationList[j].getName()));
                } else {
                    this.cashedPathMatrix[i][j] = roadTimeSteps(shortestRoadMap[i][j]);
                }
            }
        }
    }

    /**
     * Calculate total time steps for list of roads
     *
     * @param roads list of roads
     * @return total time
     */
    private static int roadTimeSteps(Road[] roads) {
        if (roads == null) {
            return Integer.MAX_VALUE;
        }
        return Arrays.stream(roads).map(Road::getTime).mapToInt(Integer::valueOf).sum();
    }

    /**
     *
     * Generates station matrix as 1D array for fast element access
     *
     * @return
     */
    private int[] generateStationMatrix(Station[] stationList, Road[] roadList) {
        int []roadMatrix = new int[stationList.length * stationList.length];

        // Generate Map(Station -> Station Index)
        Map<String, Integer> stationMap = new HashMap<>();

        for (int i = 0; i < stationList.length; i++) {
            stationMap.put(stationList[i].getName(), i);
        }

        // Represents 2D matrix Station matrix as 1D array
        Arrays.stream(roadList).forEach(road ->{
            Station[] stations = road.getStations().toArray(Station[]::new);
            int i = stationMap.get(stations[0].getName()); // get i coordinate of the matrix
            int j = stationMap.get(stations[1].getName()); // get j coordinate of the matrix
            roadMatrix[i * stationList.length + j] = road.getTime();
            roadMatrix[i + j * stationList.length] = road.getTime();
        });

        return roadMatrix;
    }


    /**
     * Calculates shortest distance between two stations
     *
     * @param sourceStation
     * @param targetStation
     * @return distance in time steps between two Stations
     */
    public int getDistance(Station sourceStation, Station targetStation) {
        return this.cashedPathMatrix[sourceStation.getIndex()][targetStation.getIndex()];
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

    public Road[][][] getShortestRoadMap() {
        return shortestRoadMap;
    }
}
