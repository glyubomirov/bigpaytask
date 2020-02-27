package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public class TrainNoneActionType implements TrainActionable {

    private Train train;

    public TrainNoneActionType(Train train) {
        this.train = train;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.NONE;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public void process() {

    }

    @Override
    public int getExecutableTime() {
        return 0;
    }

}
