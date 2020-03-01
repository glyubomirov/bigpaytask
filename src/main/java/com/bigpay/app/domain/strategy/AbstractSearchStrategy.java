package com.bigpay.app.domain.strategy;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.action.TimeStep;

/**
 * This abstract class searches and returns next time step
 *
 * @author ggeorgiev
 */
public abstract class AbstractSearchStrategy {

    /**
     * Return next time step based on road map
     *
     * @param roadMap road map
     * @return time step
     */
    public abstract TimeStep getNextTimeStep(RoadMap roadMap);
}
