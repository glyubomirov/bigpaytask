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
    private String name;

    /**
     * Source letter station
     */
    private String sourceStation;

    /**
     * Target letter station
     */
    private String targetStation;

    /**
     * Letter weight
     */
    private int weight;

    /**
     *
     *
     * @param name
     * @param sourceStation
     * @param targetStation
     * @param weight
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
        return name;
    }

    /**
     *
     * @return source letter station
     */
    public String getSourceStation() {
        return sourceStation;
    }

    /**
     *
     * @return target letter station
     */
    public String getTargetStation() {
        return targetStation;
    }

    /**
     *
     * @return letter weight
     */
    public int getWeight() {
        return weight;
    }
}
