package com.bigpay.app.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent train in the task
 *
 * @author ggeorgiev
 */
public class Train {

    /**
     * Train name
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
    private int timeOnRoad;

    /**
     * Constructor used to create new train instance
     *
     * @param name train name
     * @param station current train station
     * @param capacity train capacity
     */
    public Train(String name, Station station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
        this.letters = new HashSet<>();
    }

    /**
     * Move train on the road, departs and arrives at station
     *
     * @return true if train has moved
     */
    public void move() {

        if (this.road == null) {
            return;
        }

        if (this.timeOnRoad < this.road.getTimeSteps()) {
            this.timeOnRoad++;
        }
    }

    /**
     * Processes train depart for next station
     *
     * @param road
     * @return true if departure is successful
     */
    public void depart(Road road) {
        if (!this.station.getRoads().contains(road)){ // if Station has not corresponding Road
            return;
        }

        this.road = road;
        this.nextStation = this.road.getCounterStation(this.station);
        this.station = null;
        this.timeOnRoad = 0;
    }

    /**
     * Processes train arrival to next station. Unload letters fo the station.
     *
     * @return true if arrival is successful
     */
    public void arrive() {
        if (this.timeOnRoad >= this.road.getTimeSteps()) { // if train arrives at the next station

            this.station = this.nextStation;
            this.nextStation = null;
            this.road = null;
            this.timeOnRoad = 0;
        }
    }


    /**
     * Checks if letter can be loaded in the train. If letter exceeds capacity it can not be loaded
     *
     * @param letter letter that check has to be performed for
     * @return True if letter can be loaded in the train, false otherwise
     */
    private boolean canLoadLetter(Letter letter) {
        return letter.getWeight() + this.size <= this.capacity;
    }

    /**
     * Performs letter load from train to train
     *
     * @param letter
     */
    public void load(Letter letter) {
        if (letter.isDelivered() || letter.isInProcessing()) {
            return;
        }

        if (canLoadLetter(letter)) {
            this.station.load(letter, this);
            this.size += letter.getWeight();
            this.letters.add(letter);
        }
    }

    /**
     * Performs letter unload from train to station
     */
    public void unload() {
        this.station.unload(this.letters);
        this.letters.clear();
        this.size = 0;
    }

    /**
     *
     * @return train's name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return station that this train is on
     */
    public Station getStation() {
        return this.station;
    }

    /**
     *
     * @return station that train is traveling to
     */
    public Station getNextStation() {
        return this.nextStation;
    }

    /**
     *
     * @return train capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     *
     * @return  raid that this train is traveling on
     */
    public Road getRoad() {
        return this.road;
    }

    /**
     *
     * @return list of letters that train is transferring
     */
    public Set<Letter> getLetters() {
        return Set.copyOf(this.letters);
    }

    /**
     *
     * @return time in time steps that train spends on the road
     */
    public int getTimeOnRoad() {
        return this.timeOnRoad;
    }

    /**
     *
     * @return weight that train can load
     */
    public int getFreeStace() {
        return this.capacity - this.letters.stream().mapToInt(Letter::getWeight).sum();
    }
}
