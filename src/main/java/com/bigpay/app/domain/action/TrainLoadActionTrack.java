package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;
import java.util.stream.Collectors;

public class TrainLoadActionTrack implements TrackableAction {

    protected String action;

    public TrainLoadActionTrack(Station station, Road road, Train train, Set<Letter> letters) {
        this.action = String.format("Train %s loads letters %s on Station %s", train.getName(),
                letters.stream().map(Letter::getName).collect(Collectors.joining( "," )), station.getName());
    }

    public String toString() {
        return this.action;
    }
}
