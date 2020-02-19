package com.bigpay.app.domain;

import java.util.List;

/**
 * Represents geographical map of train stations (nodes) and railways(edges)
 */
public class StationMap {
    /**
     * StationMatrix is 2D matrix representation of nodes and edges. Because app will need to work very often with 2D
     * matrix it will be represented as single N*N array
     */
    private int[] stationMatrix;

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
    public StationMap(Station[] stationList, Road[] roadList, Letter[] letterList, Train[] trainList) {
        int stationCount = stationList.length;
        this.stationList = stationList.clone();
        this.roadList = roadList.clone();
        this.letterList = letterList.clone();
        this.trainList = trainList.clone();

        this.stationMatrix = new int[stationCount * stationCount];
    }

    /**
     *
     * @return unmodifiable list of stations
     */
    public List<Station> getStations() {
        return List.of(this.stationList);
    }

    public List<Station> getStationList() {
        return List.of(stationList);
    }

    public List<Road> getRoadList() {
        return List.of(roadList);
    }

    public List<Letter> getLetterList() {
        return List.of(letterList);
    }

    public List<Train> getTrainList() {
        return List.of(trainList);
    }
}
