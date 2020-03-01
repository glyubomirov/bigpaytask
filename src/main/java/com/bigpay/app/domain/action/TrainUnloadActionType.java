package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

/**
 * Implementation of train unload action
 *
 * @author ggeorgiev
 */
public class TrainUnloadActionType implements TrainActionable {

    /**
     * Train that unloads letter
     */
    private Train train;

    /**
     * Station where train unloads letters to
     */
    private Station station;

    /**
     * Creates instance of train unload action
     *
     * @param train train that loads
     * @param station station where train unloads letters to
     */
    public TrainUnloadActionType(Train train, Station station) {
        this.train = train;
        this.station = station;
    }

    /**
     *
     * @return unload action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.UNLOAD;
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
        this.train.unload();
    }

    /**
     *
     * @return station where train unloads letters to
     */
    public Station getStation() {
        return station;
    }
}
