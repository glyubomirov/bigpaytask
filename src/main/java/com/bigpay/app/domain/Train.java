package com.bigpay.app.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent train in the task
 */
public class Train {
    /**
     * Name of te train
     */
    private String name;

    /**
     * Station that train is on
     */
    private Station station;

    /**
     * Station that train moves to
     */
    private Station nextStation;

    /**
     * Train load capacity
     */
    private int capacity;

    /**
     * Train current load size
     */
    private int size;

    /**
     * Road that train is on
     */
    private Road road;

    /**
     * List of letters that train transports
     */
    private Set<Letter> letters;

    /**
     * Time that trains spends on the road
     */
    private int roadTime;

    public Train(String name, Station station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
        this.letters = new HashSet<>();
    }

    /**
     * Move train on the road, departures and arrives at station
     *
     * @param road Road that train moves on
     * @return true if train has moved
     */
    public boolean move(Road road) {
        if (this.road != road) { // if train is on this road
            return false;
        }

        if (this.station != null) { // if Train is on the Station
           return false;
        } else { // else train is not the station
            this.roadTime++; // train continue moving on the road

            this.arrive();
        }

        return true;
    }

    /**
     * Processes train departure at next station
     *
     * @param road
     * @param letters
     * @return true if departure is successful
     */
    public boolean departure(Road road, Set<Letter> letters) {
        if (!this.station.getRoads().contains(road)){ // if Station has not corresponding Road
            return false;
        } else { // else Station has corresponding Road than train starts moving on the road
            if (!this.canLoadLetters(letters)) {
                return false;
            }
        }

        this.nextStation = this.road.getCounterStation(this.station);
        this.station.unloadLetters(letters);
        this.loadLetters(letters);
        this.station = null;
        this.roadTime = 1;

        return true;
    }

    /**
     * Processes train arrival to next station. Unload letters fo the station.
     *
     * @return true if arrival is successful
     */
    private boolean arrive() {
        if (this.roadTime >= this.road.getTime()) { // if train arrives at the next station
            this.station = this.nextStation;
            this.nextStation = null;
            this.road = null;
            this.roadTime = 0;
            this.station.loadLetters(this.letters);
            this.unloadLetters();
            return true;
        }

        return false;
    }

    private boolean canLoadLetters(Set<Letter> letters) {
        return this.calculateLoadSize(letters) + this.size <= this.capacity;
    }

    private boolean loadLetters(Set<Letter> letters) {
        if (!canLoadLetters(letters)) {
            return false;
        }

        this.letters.addAll(letters);
        this.size = this.calculateLoadSize(this.letters);

        return true;
    }

    public void unloadLetters() {
        this.station.loadLetters(this.letters);
        this.letters.clear();
        this.size = 0;
    }

    /**
     *  Calculates weight of letters
     *
     * @param letters
     * @return weight of letters
     */
    public int calculateLoadSize(Set<Letter> letters) {
        return letters.stream().mapToInt(Letter::getWeight).sum();
    }

    public String getName() {
        return this.name;
    }

    public Station getStation() {
        return this.station;
    }

    public Station getNextStation() {
        return this.nextStation;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Road getRoad() {
        return this.road;
    }

    public Set<Letter> getLetters() {
        return Set.copyOf(this.letters);
    }

    public int getRoadTime() {
        return this.roadTime;
    }
}
