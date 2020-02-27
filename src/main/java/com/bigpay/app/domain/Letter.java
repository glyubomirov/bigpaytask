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

    public Letter(String name, Station initialDest, Station finalDest, int weight) {
        this.name = name;
        this.initialDest = initialDest;
        this.currentDest = initialDest;
        this.finalDest = finalDest;
        this.weight = weight;
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

    public boolean isArrived() {
        return isArrived;
    }

    public boolean isInProcessing() {
        return isInProcessing;
    }

    public void load() {
        if (!this.isArrived) {
            this.isInProcessing = true;
        }
    }

    public void unload(Station station) {
        this.currentDest = station;
        this.isInProcessing = false;

        if (station == this.finalDest) {
            this.isArrived = true;
        }
    }
}
