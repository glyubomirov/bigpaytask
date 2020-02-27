package com.bigpay.app.domain.action;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;

public class TrainAction {
    private Train train;
    private TrainActionable action;
    private boolean processed;

    public TrainAction(RoadMap roadMap, Train train, TrainActionable action) {
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

        if (this.action.getActionType() == TrainActionType.LOAD) {
            train.load(((TrainLoadActionType)this.action).getLetter());
        } else if (this.action.getActionType() == TrainActionType.UNLOAD) {
            train.unload();
        } else if (this.action.getActionType() == TrainActionType.ARRIVE) {
            train.arrive();
        } else if (this.action.getActionType() == TrainActionType.DEPART) {
            train.depart(((TrainDepartActionType)this.action).getRoad());
        } else if (this.action.getActionType() == TrainActionType.MOVE) {
            train.move();
        } else {

        }

        this.processed = true;
    }
}
