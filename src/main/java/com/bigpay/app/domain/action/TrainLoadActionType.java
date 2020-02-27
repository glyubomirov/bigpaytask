package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

public class TrainLoadActionType implements TrainActionable {

    private Train train;
    private Letter letter;
    private Station station;

    public TrainLoadActionType(Train train, Letter letter, Station station) {
        this.train = train;
        this.letter = letter;
        this.station = station;
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

    public Station getStation() {
        return station;
    }
}
