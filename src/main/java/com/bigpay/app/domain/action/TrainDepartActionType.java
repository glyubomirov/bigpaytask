package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Train;

public class TrainDepartActionType implements TrainActionable {

    private Train train;
    private Road road;

    public TrainDepartActionType(Train train, Road road) {
        this.train = train;
        this.road = road;
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
}
