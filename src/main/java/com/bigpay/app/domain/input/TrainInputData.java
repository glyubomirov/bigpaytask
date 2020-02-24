package com.bigpay.app.domain.input;

/**
 * Represent Train from the input
 */
public class TrainInputData {
    private String name;
    private String station;
    private int capacity;

    public TrainInputData(String name, String station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getStation() {
        return station;
    }

    public int getCapacity() {
        return capacity;
    }
}
