package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public class TrainArriveActionType implements TrainActionable {

    private Train train;

    public TrainArriveActionType(Train train) {
        this.train = train;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.ARRIVE;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public void process() {
        train.arrive();
    }

    @Override
    public int getExecutableTime() {
        return 0;
    }
}
