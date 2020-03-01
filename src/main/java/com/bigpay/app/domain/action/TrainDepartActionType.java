package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

/**
 * Implementation of train depart action
 *
 * @author ggeorgiev
 */
public class TrainDepartActionType implements TrainActionable {

    /**
     * Train that departs
     */
    private Train train;

    /**
     * Road that this train uses for departure
     */
    private Road road;

    /**
     * Station that train departs from
     */
    private Station fromStation;

    /**
     * Station that train departs for
     */
    private Station toStation;

    /**
     * Creates instance of train departure action
     *
     * @param train train that has to perform action
     * @param road road that this train uses for departure
     * @param fromStation station that train departs from
     * @param toStation station that train departs for
     */
    public TrainDepartActionType(Train train, Road road, Station fromStation, Station toStation) {
        this.train = train;
        this.road = road;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    /**
     *
     * @return depart action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.DEPART;
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
     *
     * @return Road that this train uses for departure
     */
    public Road getRoad() {
        return this.road;
    }

    /**
     * Processes the action
     */
    @Override
    public void process() {
        this.train.depart(this.road);
    }

    /**
     *
     * @return Station that train departs from
     */
    public Station getFromStation() {
        return this.fromStation;
    }

    /**
     *
     * @return Station that train departs for
     */
    public Station getToStation() {
        return this.toStation;
    }
}
