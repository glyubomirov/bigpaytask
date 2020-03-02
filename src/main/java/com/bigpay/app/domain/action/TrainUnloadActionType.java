package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

import java.util.Set;

/**
 * Implementation of train unload action
 *
 * @author ggeorgiev
 */
public class TrainUnloadActionType implements TrainActionable {

    /**
     * Train that unloads letter
     */
    private final Train train;

    /**
     * Station where train unloads letters to
     */
    private final Station station;

    /**
     * Letters that have to unloaded
     */
    private final Set<Letter> letters;

    /**
     * Creates instance of train unload action
     *
     * @param train train that loads
     * @param station station where train unloads letters to
     * @param letters set of letters that have to unloaded
     */
    public TrainUnloadActionType(Train train, Station station, Set<Letter> letters) {
        this.train = train;
        this.station = station;
        this.letters = letters;
    }

    /**
     *
     * @return unload action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.UNLOAD;
    }

    /**
     *
     * @return train that performs action
     */
    @Override
    public Train getTrain() {
        return this.train;
    }

    /**
     * Processes the action
     */
    @Override
    public void process() {
        this.train.unload();
    }

    /**
     *
     * @return station where train unloads letters to
     */
    public Station getStation() {
        return this.station;
    }

    /**
     *
     * @return letters that have to unloaded
     */
    public Set<Letter> getLetters() {
        return letters;
    }
}
