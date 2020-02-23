package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;
import java.util.stream.Collectors;

public class TrainUnloadActionTrack implements TrackableAction {

    protected String action;

    public TrainUnloadActionTrack(Station station, Road road, Train train, Set<Letter> letters) {
        this.action = String.format("Train %s unloads letters %s on Station %c", train.getName(),
                letters.stream().map(Letter::getName).collect(Collectors.joining( "," )), station.getName());
    }

    public String toString() {
        return this.action;
    }
}
