package com.bigpay.app.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents letter/delivery in the task
 */
public class Letter {
    private String name;
    private Station initialDest;
    private Station finalDest;
    private int weight;

    private boolean isArrived;
    private List<Station> destinationList;
    private int distanceToDest;

    public Letter(String name, Station initialDest, Station finalDest, int weight) {
        this.name = name;
        this.initialDest = initialDest;
        this.finalDest = finalDest;
        this.weight = weight;
        this.destinationList = new ArrayList<>(2);
        this.destinationList.add(initialDest);
        this.distanceToDest = -1;
    }

    public String getName() {
        return name;
    }

    public Station getInitialDest() {
        return initialDest;
    }

    public Station getFinalDest() {
        return finalDest;
    }

    public int getWeight() {
        return weight;
    }

    public void addDest(Station station) {
        this.destinationList.add(station);

        if (station == this.finalDest) {
            this.isArrived = true;
        }
    }

    public Station removeDest() {
        if (this.destinationList.size() > 1) { // initial station can not be removed
            return this.destinationList.remove(this.destinationList.size() - 1);
        }

        return null;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public List<Station> getDestList() {
        return Collections.unmodifiableList(this.destinationList);
    }
}
