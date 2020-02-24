package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;

public class TrainDepartActionTrack implements TrackableAction {

    protected String action;

    public TrainDepartActionTrack(Station station, Road road, Train train, Set<Letter> letters) {
        this.action = String.format("Train %s departs from Station %s for Station %s",
                train.getName(), station.getName(), road.getCounterStation(station).getName());
    }

    public String toString() {
        return this.action;
    }
}
