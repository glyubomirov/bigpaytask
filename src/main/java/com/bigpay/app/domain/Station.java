package com.bigpay.app.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents Station(Node) from the task
 *
 * @author ggeorgiev
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
     * Station sequential index from inout data
     */
    private int index;

    /**
     * Constructor used to create new Station instance
     *
     * @param name Station name
     * @param index Station sequential index from inout data
     */
    public Station(String name, int index) {
        this.name = name;
        this.index = index;
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

    public void unload(Set<Letter> letters) {
        if (letters == null) {
            return;
        }

        letters.forEach(letter -> letter.unload(this));
        this.letters.addAll(letters);
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
