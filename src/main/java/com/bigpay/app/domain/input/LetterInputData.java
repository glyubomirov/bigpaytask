package com.bigpay.app.domain.input;

/**
 * Represent Edge from the input
 */
public class LetterInputData {
    private String name;
    private char sourceStation;
    private char targetStation;
    private int weight;

    public LetterInputData( String name, char sourceStation, char targetStation, int weight ) {
        this.name = name;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public char getSourceStation() {
        return sourceStation;
    }

    public char getTargetStation() {
        return targetStation;
    }

    public int getWeight() {
        return weight;
    }
}
