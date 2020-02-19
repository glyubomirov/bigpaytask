package com.bigpay.app.domain.input;

/**
 * Represent Edge from the input
 */
public class RoadInputData {
    private char sourceStation;
    private char targetStation;
    private int time;

    public RoadInputData(char sourceStation, char targetStation, int time) {
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.time = time;
    }

    public char getSourceStation() {
        return sourceStation;
    }

    public char getTargetStation() {
        return targetStation;
    }

    public int getTime() {
        return time;
    }

}
