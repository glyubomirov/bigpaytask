package com.bigpay.app.component;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.TrainActionable;

import java.util.*;

public class TrainActionComponent {

    private Map<Train, List<TrainActionable>> trainActionMap;
    private RoadMap roadMap;
    private Road[][][] shortestRoadsMap;

    private TrainActionComponent(RoadMap roadMap, Road[][][] shortestRoadsMap){
        this.trainActionMap = new HashMap<>();
        this.roadMap = roadMap;
        this.shortestRoadsMap = shortestRoadsMap.clone();

        Arrays.stream(roadMap.getTrains()).forEach(train -> trainActionMap.put(train, new ArrayList<>()));
    }
}
