package com.bigpay.app.domain.input;

/**
 * Represent Train from the input
 */
public class TrainInputData {
    private String name;
    private char station;
    private int capacity;

    public TrainInputData(String name, char station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public char getStation() {
        return station;
    }

    public int getCapacity() {
        return capacity;
    }
}
