package com.bigpay.app.domain.input;

/**
 * Aggregates information form input data as object
 *
 * @author ggeorgiev
 */
public class InputDataMap {
    /**
     * List of all station
     */
    private final StationInputData[] stationList;

    /**
     * List of all roads
     */
    private final RoadInputData[] roadList;

    /**
     * List of all letters
     */
    private final LetterInputData[] letterList;

    /**
     * List of all trains
     */
    private final TrainInputData[] trainList;

    /**
     * Constructor used to create new InputDataMap instance
     *
     * @param stationList list of all station
     * @param roadList list of al roads
     * @param letterList list of all letters
     * @param trainList list of all trains
     */
    public InputDataMap(StationInputData[] stationList, RoadInputData[] roadList, LetterInputData[] letterList,
                        TrainInputData[] trainList) {
        this.stationList = stationList;
        this.roadList = roadList;
        this.letterList = letterList;
        this.trainList = trainList;
    }

    /**
     *
     * @return list of all station
     */
    public StationInputData[] getStationList() {
        return this.stationList;
    }

    /**
     *
     * @return list of all roads
     */
    public RoadInputData[] getRoadList() {
        return this.roadList;
    }

    /**
     *
     * @return list of all letters
     */
    public LetterInputData[] getLetterList() {
        return this.letterList;
    }

    /**
     *
     * @return list of all trains
     */
    public TrainInputData[] getTrainList() {
        return this.trainList;
    }
}
