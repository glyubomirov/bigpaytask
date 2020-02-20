package com.bigpay.app.domain.input;

import com.bigpay.app.domain.input.LetterInputData;
import com.bigpay.app.domain.input.RoadInputData;
import com.bigpay.app.domain.input.StationInputData;
import com.bigpay.app.domain.input.TrainInputData;

public class InputDataMap {
    private StationInputData[] stationList;
    private RoadInputData[] roadList;
    private LetterInputData[] letterList;
    private TrainInputData[] trainList;

    public InputDataMap(StationInputData[] stationList, RoadInputData[] roadList, LetterInputData[] letterList,
                        TrainInputData[] trainList) {
        this.stationList = stationList;
        this.roadList = roadList;
        this.letterList = letterList;
        this.trainList = trainList;
    }

    public StationInputData[] getStationList() {
        return stationList;
    }

    public RoadInputData[] getRoadList() {
        return roadList;
    }

    public LetterInputData[] getLetterList() {
        return letterList;
    }

    public TrainInputData[] getTrainList() {
        return trainList;
    }
}
