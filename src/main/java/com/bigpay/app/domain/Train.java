package com.bigpay.app.domain;

import com.bigpay.app.component.ActionTrackerComponent;
import com.bigpay.app.domain.action.track.TrackActionType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private int timeOnRoad;

    /**
     *
     */
    private ActionTrackerComponent actionTrackerComponent;

    public Train(String name, Station station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
        this.letters = new HashSet<>();
        this.actionTrackerComponent = ActionTrackerComponent.getInstance();
    }

    /**
     * Move train on the road, departures and arrives at station
     *
     * @return true if train has moved
     */
    public void move() {

        if (this.road == null) {
            return;
        }

        if (this.timeOnRoad < this.road.getTime()) {
            this.timeOnRoad++;
            this.actionTrackerComponent.track(TrackActionType.MOVE, null, road, this, null);
        }
    }

    /**
     * Processes train depart at next station
     *
     * @param road
     * @return true if depart is successful
     */
    public void depart(Road road) {
        if (!this.station.getRoads().contains(road)){ // if Station has not corresponding Road
            return;
        }

        this.road = road;
        this.nextStation = this.road.getCounterStation(this.station);
        this.actionTrackerComponent.track(TrackActionType.DEPART, this.station, road, this, null);
        this.station = null;
        this.timeOnRoad = 0;
    }

    /**
     * Processes train arrival to next station. Unload letters fo the station.
     *
     * @return true if arrival is successful
     */
    public void arrive() {
        if (this.timeOnRoad >= this.road.getTime()) { // if train arrives at the next station

            this.station = this.nextStation;
            this.nextStation = null;
            this.road = null;
            this.timeOnRoad = 0;
            this.actionTrackerComponent.track(TrackActionType.ARRIVE, this.station, null, this, null);
        }
    }

    private boolean canLoadLetter(Letter letter) {
        return letter.getWeight() + this.size <= this.capacity;
    }

    public void load(Set<Letter> letters) {
        if (letters == null) {
            return;
        }

        Set<Letter> unprocessedLetters = letters.stream()
                .filter(letter -> !letter.isArrived() && !letter.isInProcessing())
                .collect(Collectors.toSet());

        unprocessedLetters.forEach(letter -> this.load(letter));

        this.actionTrackerComponent.track(TrackActionType.LOAD, this.station, null, this, this.letters);
    }

    public void load(Letter letter) {
        if (letter.isArrived() || letter.isInProcessing()) {
            return;
        }

        if (canLoadLetter(letter)) {
            this.station.load(letter, this);
            this.size += letter.getWeight();
            this.letters.add(letter);
            this.actionTrackerComponent.track(TrackActionType.LOAD, this.station, null, this, this.letters);
        }
    }

    public void unload() {
        this.actionTrackerComponent.track(TrackActionType.UNLOAD, this.station, null, this, this.letters);
        this.station.unload(this.letters);
        this.letters.clear();
        this.size = 0;
    }

    /**
     *  Calculates weight of letters
     *
     * @param letters
     * @return weight of letters
     */
    public static int calculateLoadSize(Set<Letter> letters) {
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

    public int getTimeOnRoad() {
        return this.timeOnRoad;
    }

    public int getFreeCapacity() {
        return this.capacity - this.letters.stream().mapToInt(Letter::getWeight).sum();
    }
}
