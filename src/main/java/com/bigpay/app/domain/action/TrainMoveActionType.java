package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

/**
 * Implementation of train move action
 *
 * @author ggeorgiev
 */
public class TrainMoveActionType implements TrainActionable {

    /**
     * Train that moves
     */
    private final Train train;

    /**
     * Station that train moves from
     */
    private final Station fromStation;

    /**
     * Station that train moves to
     */
    private final Station toStation;

    /**
     * Creates instance of train move action
     *
     * @param train train that moves
     * @param fromStation station that train moves from
     * @param toStation station that train moves to
     */
    public TrainMoveActionType(Train train, Station fromStation, Station toStation) {
        this.train = train;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    /**
     *
     * @return move action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.MOVE;
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
        this.train.move();
    }

    /**
     *
     * @return Station that train moves from
     */
    public Station getFromStation() {
        return this.fromStation;
    }

    /**
     *
     * @return Station that train moves to
     */
    public Station getToStation() {
        return this.toStation;
    }
}
