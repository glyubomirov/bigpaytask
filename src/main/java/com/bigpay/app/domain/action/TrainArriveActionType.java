package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

/**
 * Implementation of train arrive action
 *
 * @author ggeorgiev
 */
public class TrainArriveActionType implements TrainActionable {

    /**
     * Train that arrives
     */
    private Train train;

    /**
     * Station where train arrives to
     */
    private Station station;

    /**
     * Creates instance of train arrive action
     *
     * @param train train that has to perform action
     * @param station action that has to be performed
     */
    public TrainArriveActionType(Train train, Station station) {
        this.train = train;
        this.station = station;
    }

    /**
     *
     * @return arrive action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.ARRIVE;
    }

    /**
     *
     * @return train that performs action
     */
    @Override
    public Train getTrain() {
        return this.train;
    }

    /**
     * Processes the action
     */
    @Override
    public void process() {
        train.arrive();
    }

    /**
     *
     * @return Station where train arrives to
     */
    public Station getStation() {
        return station;
    }
}
