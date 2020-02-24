package com.bigpay.app.component;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.track.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ActionTrackerComponent {

    private static ActionTrackerComponent instance;
    private Queue<TrackableAction> actionQueue;

    private ActionTrackerComponent() {
        this.actionQueue = new LinkedList<>();
    }

    public static synchronized ActionTrackerComponent getInstance() {
        if (instance == null) {
            instance = new ActionTrackerComponent();
        }

        return instance;
    }

    public void track(TrackActionType trackActionType, Station station, Road road, Train train, Set<Letter> letters) {
        TrackableAction action;

        if (trackActionType == TrackActionType.ARRIVE) {
            action = new TrainArriveActionTrack(station, road, train, letters);
        } else if (trackActionType == TrackActionType.DEPART) {
            action = new TrainDepartActionTrack(station, road, train, letters);
        } else if (trackActionType == TrackActionType.MOVE) {
            action = new TrainMoveActionTrack(station, road, train, letters);
        } else if (trackActionType == TrackActionType.LOAD) {
            action = new TrainLoadActionTrack(station, road, train, letters);
        } else if (trackActionType == TrackActionType.UNLOAD) {
            action = new TrainUnloadActionTrack(station, road, train, letters);
        } else {
            throw new UnsupportedOperationException("Not supported action type");
        }

        this.actionQueue.add(action);
    }

    public TrackableAction poll() {
        return this.actionQueue.poll();
    }
}
