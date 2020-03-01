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
     * Station's name
     */
    private String name;

    /**
     * Set of letters on this station
     */
    private Set<Letter> letters;

    /**
     * Set of roads that this station is connected
     */
    private Set<Road> roads;

    /**
     * Station sequential index from input data
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

    /**
     *
     * @return station sequential index from input data
     */
    public int getIndex() {
        return this.index;
    }

    /**
     *
     * @return station's name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param road add connects road to station
     */
    public void addRoad(Road road) {
        this.roads.add(road);
    }

    /**
     *
     * @return set of letters on this station
     */
    public Set<Letter> getLetters() {
        return Set.copyOf(this.letters);
    }

    /**
     *
     * @return set of roads that this station is connected
     */
    public Set<Road> getRoads() {
        return Set.copyOf(this.roads);
    }

    /**
     *
     * @param letters unloads set of letters
     */
    public void unload(Set<Letter> letters) {
        if (letters == null) {
            return;
        }

        letters.forEach(letter -> letter.unload(this));
        this.letters.addAll(letters);
    }

    /**
     * Loads letter on train
     *
     * @param letter letter that has be loaded
     * @param train train that letter has to be loaded on
     */
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
