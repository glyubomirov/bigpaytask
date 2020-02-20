package com.bigpay.app.domain;

import java.util.*;

/**
 * Represent edge in the task
 */
public class Road {
    /**
     * Number of time steps that it takes a train to travel along an edge
     */
    private int time;

    /**
     * All trains on this road
     */
    private Set<Train> trainList;

    /**
     * Contains 2 stations that this road connects
     */
    private Set<Station> stations;

    /**
     *
     * @param time
     */
    public Road(int time, Station firstStation, Station secondStation) {
        this.time = time;
        this.stations = Set.of(firstStation, secondStation);
        this.trainList = new HashSet<>();
    }

    public int getTime() {
        return time;
    }

    public Set<Train> getTrainList() {
        return Set.copyOf(this.trainList);
    }

    public Set<Station> getStations() {
        return Set.copyOf(this.stations);
    }

    public Station getCounterStation(Station station) {
        if (!this.stations.contains(station)) {
            return null;
        }
        //noinspection OptionalGetWithoutIsPresent
        return this.stations.stream().filter(item -> item != station).findFirst().get();
    }
}
