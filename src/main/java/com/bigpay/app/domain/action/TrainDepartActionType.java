package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

public class TrainDepartActionType implements TrainActionable {

    private Train train;
    private Road road;
    private Station fromStation;
    private Station toStation;

    public TrainDepartActionType(Train train, Road road, Station fromStation, Station toStation) {
        this.train = train;
        this.road = road;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.DEPART;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    public Road getRoad() {
        return road;
    }

    @Override
    public void process() {
        this.train.depart(this.road);
    }

    @Override
    public int getExecutableTime() {
        return 0;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}
