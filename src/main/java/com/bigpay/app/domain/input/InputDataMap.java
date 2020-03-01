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
    private StationInputData[] stationList;

    /**
     * List of all roads
     */
    private RoadInputData[] roadList;

    /**
     * List of all letters
     */
    private LetterInputData[] letterList;

    /**
     * List of all trains
     */
    private TrainInputData[] trainList;

    /**
     * Constructor used to create new ImputDataMap instance
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
        return stationList;
    }

    /**
     *
     * @return list of all roads
     */
    public RoadInputData[] getRoadList() {
        return roadList;
    }

    /**
     *
     * @return list of all letters
     */
    public LetterInputData[] getLetterList() {
        return letterList;
    }

    /**
     *
     * @return list of all trains
     */
    public TrainInputData[] getTrainList() {
        return trainList;
    }
}
