package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public class TrainUnloadActionType implements TrainActionable {

    private Train train;

    public TrainUnloadActionType(Train train) {
        this.train = train;
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
}
