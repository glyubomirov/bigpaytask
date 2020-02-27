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
    private Station currentDest;
    private int weight;

    private boolean isArrived;
    private boolean isInProcessing;
    private List<Station> destinationList;
    private int distanceToDest;
    private Train train;

    public Letter(String name, Station initialDest, Station finalDest, int weight) {
        this.name = name;
        this.initialDest = initialDest;
        this.currentDest = initialDest;
        this.finalDest = finalDest;
        this.weight = weight;
        this.destinationList = new ArrayList<>(2);
        this.destinationList.add(initialDest);
        this.distanceToDest = -1;
        this.isArrived = false;
        this.isInProcessing = false;
    }

    public String getName() {
        return name;
    }

    public Station getInitialDest() {
        return initialDest;
    }

    public Station getCurrentDest() {
        return currentDest;
    }

    public Station getFinalDest() {
        return finalDest;
    }

    public int getWeight() {
        return weight;
    }

    private void addDest(Station station) {
        this.destinationList.add(station);

        if (station == this.finalDest) {
            this.isArrived = true;
            this.isInProcessing = false;
        }
    }

    public boolean isArrived() {
        return isArrived;
    }

    public List<Station> getDestList() {
        return Collections.unmodifiableList(this.destinationList);
    }

    public boolean isInProcessing() {
        return isInProcessing;
    }

    public void load(Train train) {
        if (!this.isArrived) {
            this.train = train;
            this.isInProcessing = true;
        }
    }

    public void unload(Station station) {
        this.addDest(station);
        this.train = null;
        this.isInProcessing = false;
        this.currentDest = station;
    }
}
