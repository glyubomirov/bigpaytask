package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

public class TrainMoveActionType implements TrainActionable {

    private Train train;
    private Station fromStation;
    private Station toStation;

    public TrainMoveActionType(Train train, Station fromStation, Station toStation) {
        this.train = train;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.MOVE;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public void process() {
        this.train.move();
    }

    @Override
    public int getExecutableTime() {
        return 1;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}
