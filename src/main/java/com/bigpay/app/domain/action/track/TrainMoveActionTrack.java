package com.bigpay.app.domain.action.track;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;

public class TrainMoveActionTrack implements TrackableAction {

    protected String action;

    public TrainMoveActionTrack(Station station, Road road, Train train, Set<Letter> letters) {
        this.action = String.format("Train %s moves from Station %s to Station %s",
                train.getName(), road.getCounterStation(train.getNextStation()).getName(), train.getNextStation().getName());
    }

    public String toString() {
        return this.action;
    }
}
