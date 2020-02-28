package com.bigpay.app.component;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.action.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class processes and saves time steps.
 *
 * @author ggeorgiev
 */
public class TimeStepComponent {
    private static TimeStepComponent instance;

    private List<TimeStep> timeStepList;

    /**
     * Private constructor
     */
    private TimeStepComponent() {
        // 100 is random number for performance improvement
        this.timeStepList = new ArrayList<>(100);
    }

    /**
     * Get the single instance of this component
     * @return instance of this component
     */
    public static synchronized TimeStepComponent getInstance() {
        if (instance == null) {
            instance = new TimeStepComponent();
        }

        return instance;
    }

    /**
     * Get list with processed time steps
     *
     * @return list with processed time steps
     */
    public List<TimeStep> getTimeStepList() {
        return Collections.unmodifiableList(timeStepList);
    }

    /**
     * Saves and processes time steps
     *
     * @param timeStep time step that has to be processed
     */
    public void process(TimeStep timeStep) {
        timeStep.process();
        this.timeStepList.add(timeStep);
    }

    /**
     * Checks if all letters are delivered
     *
     * @param roadMap road map with information for letters and stations
     * @return True if all letters are delivered and False if there is at least one undelivered letter
     */
    public static boolean isFinish(RoadMap roadMap) {
        return Arrays.stream(roadMap.getLetters()).allMatch(letter -> letter.getCurrentDest() == letter.getFinalDest());
    }
}
