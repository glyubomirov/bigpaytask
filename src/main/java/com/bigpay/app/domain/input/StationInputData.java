package com.bigpay.app.domain.input;

/**
 * Represent Node from the input
 */
public class StationInputData {
    private char name;

    public StationInputData(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }
}
