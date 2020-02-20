package com.bigpay.app.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent node in the task
 */
public class Station {

    /**
     * Station name
     */
    private char name;

    /**
     * All letters on this station
     */
    private Set<Letter> letters;

    /**
     * All roads that this station connects
     */
    private Set<Road> roads;

    /**
     * All trains on this road
     */
    private Set<Train> trains;

    public Station(char name) {
        this.name = name;
        this.trains = new HashSet<>();
        this.roads = new HashSet<>();
        this.letters = new HashSet<>();
    }

    public char getName() {
        return name;
    }

    public void addRoad(Road road) {
        this.roads.add(road);
    }

    public Set<Letter> getLetters() {
        return Set.copyOf(this.letters);
    }

    public Set<Road> getRoads() {
        return Set.copyOf(this.roads);
    }

    public Set<Train> getTrains() {
        return Set.copyOf(this.trains);
    }

    public void addTrains(Set<Train> trains) {
        this.trains.addAll(trains);
    }

    public void removeTrains(Set<Train> trains) {
        this.trains.removeAll(trains);
    }

    public void loadLetters(Set<Letter> letters) {
        this.letters.addAll(letters);
    }

    public void unloadLetters(Set<Letter> letters) {
        this.letters.removeAll(letters);
    }
}
