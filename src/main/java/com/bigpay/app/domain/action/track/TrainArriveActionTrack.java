package com.bigpay.app.domain.action.track;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;

public class TrainArriveActionTrack implements TrackableAction {

    protected String action;

    public TrainArriveActionTrack(Station station, Road road, Train train, Set<Letter> letters) {
        this.action = String.format("Train %s arrives at station %s", train.getName(), station.getName());
    }

    public String toString() {
        return this.action;
    }
}
