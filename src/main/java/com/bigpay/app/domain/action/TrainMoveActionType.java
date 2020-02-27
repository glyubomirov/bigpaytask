package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public class TrainMoveActionType implements TrainActionable {

    private Train train;

    public TrainMoveActionType(Train train) {
        this.train = train;
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
}
