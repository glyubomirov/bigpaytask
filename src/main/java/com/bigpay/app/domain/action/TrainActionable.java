package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Train;

public interface TrainActionable {

    TrainActionType getActionType();

    Train getTrain();

    void process();

    int getExecutableTime();
}
