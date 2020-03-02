package com.bigpay.app.domain;

/**
 * Represents letter from the task
 *
 * @author ggeorgiev
 *
 */
public class Letter {

    /**
     * Letter name
     */
    private final String name;

    /**
     * Initial Letter destination
     */
    private final Station initialDest;

    /**
     * Final Letter destination
     */
    private final Station finalDest;

    /**
     * Current Letter destination
     */
    private Station currentDest;

    /**
     * Letter weight
     */
    private final int weight;

    /**
     * Flag if letter is delivered
     */
    private boolean isDelivered;

    /**
     * Flag if letter is processing (is on the train)
     */
    private boolean isInProcessing;

    /**
     * Constructor used to create new letter instance
     *
     * @param name letter name
     * @param initialDest letter initial destination
     * @param finalDest letter final destination
     * @param weight weight of letter
     */
    public Letter(String name, Station initialDest, Station finalDest, int weight) {
        this.name = name;
        this.initialDest = initialDest;
        this.currentDest = initialDest;
        this.finalDest = finalDest;
        this.weight = weight;
        this.isDelivered = false;
        this.isInProcessing = false;
    }

    /**
     * @return letter name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return initial letter destination
     */
    public Station getInitialDest() {
        return this.initialDest;
    }

    /**
     * @return current Letter destination
     */
    public Station getCurrentDest() {
        return this.currentDest;
    }

    /**
     * @return final Letter destination
     */
    public Station getFinalDest() {
        return this.finalDest;
    }

    /**
     * @return Letter weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * @return true if letter is delivered to final destination
     */
    public boolean isDelivered() {
        return this.isDelivered;
    }

    /**
     * @return true if letter is in train
     */
    public boolean isInProcessing() {
        return this.isInProcessing;
    }

    /**
     * Load letter to train
     */
    public void load() {
        // Letter can be loaded if it is nor arrived at its final station
        if (!this.isDelivered) {
            this.isInProcessing = true;
        }
    }

    /**
     * Unload letter from train to station
     *
     * @param station Station where letter is unloaded
     */
    public void unload(Station station) {
        this.currentDest = station;
        this.isInProcessing = false;

        // if tis station is letter final station then letter is delivered
        if (station == this.finalDest) {
            this.isDelivered = true;
        }
    }
}
