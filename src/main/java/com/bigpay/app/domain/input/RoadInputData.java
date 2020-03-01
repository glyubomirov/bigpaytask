package com.bigpay.app.domain.input;

/**
 * Represent Road in input data format
 *
 * @author ggeorgiev
 */
public class RoadInputData {

    /**
     * First Station that road has to be connected to
     */
    private String sourceStation;

    /**
     * Second Station that road has to be connected to
     */
    private String targetStation;

    /**
     * Time that train needs to passes it
     */
    private int time;

    /**
     *
     * @param sourceStation first Station that road has to be connected to
     * @param targetStation second Station that road has to be connected to
     * @param time time that train needs to passes it
     */
    public RoadInputData(String sourceStation, String targetStation, int time) {
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.time = time;
    }

    /**
     *
     * @return first Station that road has to be connected to
     */
    public String getSourceStation() {
        return this.sourceStation;
    }

    /**
     *
     * @return second Station that road has to be connected to
     */
    public String getTargetStation() {
        return this.targetStation;
    }

    /**
     *
     * @return time that train needs to passes it
     */
    public int getTimeSteps() {
        return this.time;
    }

}
