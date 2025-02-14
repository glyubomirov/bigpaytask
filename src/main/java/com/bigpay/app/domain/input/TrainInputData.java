package com.bigpay.app.domain.input;

/**
 * Represent Train in input data format
 *
 * @author ggeorgiev
 */
public class TrainInputData {

    /**
     * Train's name
     */
    private final String name;

    /**
     * Station that train is on
     */
    private final String station;

    /**
     * Train load capacity
     */
    private final int capacity;

    /**
     *
     * @param name train's name
     * @param station current train station
     * @param capacity train capacity
     */
    public TrainInputData(String name, String station, int capacity) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
    }

    /**
     *
     * @return train's name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return station that this train is on
     */
    public String getStation() {
        return this.station;
    }

    /**
     *
     * @return train capacity
     */
    public int getCapacity() {
        return this.capacity;
    }
}
