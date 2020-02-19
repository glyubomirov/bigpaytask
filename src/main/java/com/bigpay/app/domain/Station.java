package com.bigpay.app.domain;

import java.util.ArrayList;

/**
 * Represent node in the task
 */
public class Station {
    private ArrayList<Letter> letterList;

    public ArrayList<Letter> getLetterList() {
        return letterList;
    }

    public void setLetterList(ArrayList<Letter> letterList) {
        this.letterList = letterList;
    }
}
