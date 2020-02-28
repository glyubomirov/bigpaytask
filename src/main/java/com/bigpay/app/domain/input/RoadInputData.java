package com.bigpay.app.domain.input;

/**
 * Represent Edge from the input
 */
public class RoadInputData {
    private String sourceStation;
    private String targetStation;
    private int time;

    public RoadInputData(String sourceStation, String targetStation, int time) {
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.time = time;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getTargetStation() {
        return targetStation;
    }

    public int getTimeSteps() {
        return time;
    }

}
