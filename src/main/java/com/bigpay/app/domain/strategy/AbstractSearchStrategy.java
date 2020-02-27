package com.bigpay.app.domain.strategy;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.action.TimeStep;

public abstract class AbstractSearchStrategy {

    public abstract TimeStep getNextTimeStep(RoadMap roadMap);
}
