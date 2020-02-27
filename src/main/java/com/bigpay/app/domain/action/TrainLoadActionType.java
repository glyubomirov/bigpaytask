package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Train;

public class TrainLoadActionType implements TrainActionable {

    private Train train;
    private Letter letter;

    public TrainLoadActionType(Train train, Letter letter) {
        this.train = train;
        this.letter = letter;
    }

    @Override
    public TrainActionType getActionType() {
        return TrainActionType.LOAD;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    public Letter getLetter() {
        return this.letter;
    }

    @Override
    public void process() {
        this.train.load(this.letter);
    }

    @Override
    public int getExecutableTime() {
        return 0;
    }
}
