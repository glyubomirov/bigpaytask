package com.bigpay.app.component;

import com.bigpay.app.domain.action.TimeStep;

import java.util.Collections;
import java.util.List;

public class TimeStepComponent {
    private static TimeStepComponent instance;

    private List<TimeStep> timeStepList;

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
}
