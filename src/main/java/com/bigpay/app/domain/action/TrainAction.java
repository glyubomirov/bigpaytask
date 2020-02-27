package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public class TrainAction {
    private Train train;
    private TrainActionable action;
    private boolean processed;

    public TrainAction(Train train, TrainActionable action) {
        this.train = train;
        this.action = action;
        this.processed = false;
    }

    public Train getTrain() {
        return train;
    }

    public TrainActionable getAction() {
        return action;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void process() {
        this.processed = true;
    }
}
