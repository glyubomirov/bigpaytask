package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

/**
 * Train action interface
 */
public interface TrainActionable {

    /**
     *
     * @return action type
     */
    TrainActionType getActionType();

    /**
     *
     * @return train that performs action
     */
    Train getTrain();

    /**
     * Processes the action
     */
    void process();
}
