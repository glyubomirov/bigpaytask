package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;

public abstract class AbstractTrainAction {

    protected Letter letter;

    protected boolean isProcessed;

    public AbstractTrainAction(Letter letter){
        this.letter = letter;
    }

    public Letter getLetter() {
        return this.letter;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
