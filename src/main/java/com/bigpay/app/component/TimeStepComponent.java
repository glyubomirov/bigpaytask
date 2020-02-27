package com.bigpay.app.component;

import com.bigpay.app.domain.action.TimeStep;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeStepComponent {
    private static TimeStepComponent instance;

    private List<TimeStep> timeStepList;
    private int lastProcessedStep = -1;

    public TimeStepComponent() {
        this.timeStepList = new ArrayList<>(10);
    }

    public static synchronized TimeStepComponent getInstance() {
        if (instance == null) {
            instance = new TimeStepComponent();
        }

        return instance;
    }

    public List<TimeStep> getTimeStepList() {
        return Collections.unmodifiableList(timeStepList);
    }

    public void addTimeStep(TimeStep timeStep) {
        this.timeStepList.add(timeStep);
    }

    public void process() {
        for (int i = lastProcessedStep + 1; i < timeStepList.size(); i++) {
            TimeStep timeStep = this.timeStepList.get(i);
            timeStep.process();
            lastProcessedStep = i;
        }
    }
}
