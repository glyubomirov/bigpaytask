package com.bigpay.app.domain.input;

/**
 * Represent Station in input data format
 *
 * @author ggeorgiev
 */
public class StationInputData {

    /**
     * Station's name
     */
    private String name;

    /**
     * Constructor used to create new Station instance
     *
     * @param name Station's name
     */
    public StationInputData(String name) {
        this.name = name;
    }

    /**
     *
     * @return station's name
     */
    public String getName() {
        return name;
    }
}
