package com.bigpay.app.domain;

import java.util.*;

/**
 * Represents Road(Edge) from the task
 *
 * @author ggeorgiev
 */
public class Road {
    /**
     * Number of time steps that it takes a train to travel along an edge
     */
    private int timeSteps;

    /**
     * Set of stations that this road connects
     */
    private Set<Station> stations;

    /**
     * Constructor used to create new Road instance
     * @param timeSteps Road time steps
     * @param firstStation First Station that this road connects
     * @param secondStation Second Station that this road connects
     */
    public Road(int timeSteps, Station firstStation, Station secondStation) {
        this.timeSteps = timeSteps;
        this.stations = Set.of(firstStation, secondStation);
    }

    /**
     * @return Number of time steps that it takes a train to travel along an edge
     */
    public int getTimeSteps() {
        return this.timeSteps;
    }

    /**
     * @return Set of stations that this road connects
     */
    public Set<Station> getStations() {
        return Set.copyOf(this.stations);
    }

    /**
     * Returns counter station of road
     *
     * @param station one of the roads stations
     * @return other of the roads station
     */
    public Station getCounterStation(Station station) {
        if (!this.stations.contains(station)) {
            return null;
        }

        //noinspection OptionalGetWithoutIsPresent
        return this.stations.stream().filter(item -> item != station).findFirst().get();
    }
}
