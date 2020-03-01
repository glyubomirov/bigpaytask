package com.bigpay.app.domain;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.component.exception.NonExistingPathException;

import java.util.*;

/**
 * Represents geographical map of train Stations(Nodes), Roads(Edges), Letters and Trains
 *
 * @author ggeorgiev
 */
public class RoadMap {

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
     * Matrix wth list of roads that connects each two stations
     */
    private Road[][][] shortestRoadMap;

    /**
     * Matrix wth minimal distance between two stations
     */
    private int[][] cashedPathMatrix;

    /**
     * Constructor that generates road map of all stations, roads, letters and trains
     *
     * @param stationList station list from input
     * @param roadList roads list from input
     * @param letterList letter list from input
     * @param trainList train list from input
     */
    public RoadMap(Station[] stationList, Road[] roadList, Letter[] letterList, Train[] trainList) {
        this.stationList = stationList.clone();
        this.roadList = roadList.clone();
        this.letterList = letterList.clone();
        this.trainList = trainList.clone();

        this.shortestRoadMap = FloydWarshallSearchComponent.getInstance().generateShortestPath(this);

        this.cashedPathMatrix = new int[stationList.length][stationList.length];

        this.calculatePathMatrix();
    }

    /**
     * Calculates and caches minimal Path length between each two stations.
     */
    private void calculatePathMatrix() {
        // Checks is there is a path between every two Stations
        for (int i = 0; i < this.stationList.length; i++) {
            for (int j = 0; j < this.stationList.length; j++) {

                // Checks if there is missing Path between two stations
                if (this.shortestRoadMap[i][j] == null) {
                    throw new NonExistingPathException(String.format("There is no path between %s and %s",
                            this.stationList[i].getName(), this.stationList[j].getName()));
                } else {
                    this.cashedPathMatrix[i][j] = roadTimeSteps(this.shortestRoadMap[i][j]);
                }
            }
        }
    }

    /**
     * Calculate total time steps for list of roads
     *
     * @param roads list of roads
     * @return total time steps
     */
    private static int roadTimeSteps(Road[] roads) {
        if (roads == null) {
            return Integer.MAX_VALUE;
        }
        return Arrays.stream(roads).map(Road::getTimeSteps).mapToInt(Integer::valueOf).sum();
    }

    /**
     * Calculates shortest distance between two stations in time steps
     *
     * @param sourceStation from station
     * @param targetStation to station
     * @return distance in time steps between two Stations
     */
    public int getDistance(Station sourceStation, Station targetStation) {
        return this.cashedPathMatrix[sourceStation.getIndex()][targetStation.getIndex()];
    }

    /**
     * @return List of all stations
     */
    public Station[] getStations() {
        return this.stationList.clone();
    }

    /**
     * @return List of all roads
     */
    public Road[] getRoads() {
        return this.roadList.clone();
    }

    /**
     * @return List of all letters
     */
    public Letter[] getLetters() {
        return this.letterList.clone();
    }

    /**
     * @return List of all trains
     */
    public Train[] getTrains() {
        return this.trainList.clone();
    }

    /**
     * @return Matrix wth list of roads that connects each two stations
     */
    public Road[][][] getShortestRoadMap() {
        return this.shortestRoadMap.clone();
    }
}
