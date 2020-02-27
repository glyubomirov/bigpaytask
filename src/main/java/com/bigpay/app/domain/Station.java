package com.bigpay.app.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent node in the task
 */
public class Station {

    /**
     * Station name
     */
    private String name;

    /**
     * All letters on this station
     */
    private Set<Letter> letters;

    /**
     * All roads that this station connects
     */
    private Set<Road> roads;

    /**
     * All trains on this station
     */
    private Set<Train> trains;

    /**
     * Sequence index from inout data
     */
    private int index;

    /**
     *
     * @param name
     */
    public Station(String name, int index) {
        this.name = name;
        this.index = index;
        this.trains = new HashSet<>();
        this.roads = new HashSet<>();
        this.letters = new HashSet<>();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
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

    public void unload(Set<Letter> letters) {
        if (letters == null) {
            return;
        }

        letters.forEach(letter -> letter.unload(this));
        this.letters.addAll(letters);
    }

    public void load(Set<Letter> letters, Train train) {
        if (letters == null) {
            return;
        }

        this.letters.forEach(Letter::load);
        this.letters.removeAll(letters);
    }

    public void load(Letter letter, Train train) {
        if (letter == null) {
            return;
        }

        if (train == null) {
            return;
        }

        letter.load();
        this.letters.remove(letter);
    }
}
