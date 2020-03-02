package com.bigpay.app.domain.input;

/**
 * Represent Letter in input data format
 *
 * @author ggeorgiev
 */
public class LetterInputData {

    /**
     * Letter name
     */
    private final String name;

    /**
     * Letter source station
     */
    private final String sourceStation;

    /**
     * Target letter station
     */
    private final String targetStation;

    /**
     * Letter weight
     */
    private final int weight;

    /**
     *
     *
     * @param name Letter's name
     * @param sourceStation letter source station
     * @param targetStation letter target station
     * @param weight letter weight
     */
    public LetterInputData(String name, String sourceStation, String targetStation, int weight ) {
        this.name = name;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.weight = weight;
    }

    /**
     *
     * @return letter name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return letter source station
     */
    public String getSourceStation() {
        return this.sourceStation;
    }

    /**
     *
     * @return letter target station
     */
    public String getTargetStation() {
        return this.targetStation;
    }

    /**
     *
     * @return letter weight
     */
    public int getWeight() {
        return this.weight;
    }
}
