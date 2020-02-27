package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

public class TrainUnloadActionType implements TrainActionable {

    private Train train;
    private Station station;

    public TrainUnloadActionType(Train train, Station station) {
        this.train = train;
        this.station = station;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.UNLOAD;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public void process() {
        this.train.unload();
    }

    @Override
    public int getExecutableTime() {
        return 0;
    }

    public Station getStation() {
        return station;
    }
}
