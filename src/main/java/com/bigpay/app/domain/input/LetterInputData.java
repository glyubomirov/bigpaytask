package com.bigpay.app.domain.input;

/**
 * Represent Edge from the input
 */
public class LetterInputData {
    private String name;
    private String sourceStation;
    private String targetStation;
    private int weight;

    public LetterInputData(String name, String sourceStation, String targetStation, int weight ) {
        this.name = name;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getTargetStation() {
        return targetStation;
    }

    public int getWeight() {
        return weight;
    }
}
