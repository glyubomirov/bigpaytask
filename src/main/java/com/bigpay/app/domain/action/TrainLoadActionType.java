package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.Train;

/**
 * Implementation of train load action
 *
 * @author ggeorgiev
 */
public class TrainLoadActionType implements TrainActionable {

    /**
     * Train that loads letter
     */
    private Train train;

    /**
     * Letter that is loaded
     */
    private Letter letter;

    /**
     * Station where train loads letter from
     */
    private Station station;

    /**
     * Creates instance of train load action
     *
     * @param train train that loads
     * @param letter letters that are loaded
     * @param station station where train loads letter from
     */
    public TrainLoadActionType(Train train, Letter letter, Station station) {
        this.train = train;
        this.letter = letter;
        this.station = station;
    }

    /**
     *
     * @return load action type
     */
    @Override
    public TrainActionType getActionType() {
        return TrainActionType.LOAD;
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
     *
     * @return letter that is loaded
     */
    public Letter getLetter() {
        return this.letter;
    }

    /**
     * Processes the action
     */
    @Override
    public void process() {
        this.train.load(this.letter);
    }

    /**
     *
     * @return Station where train loads letter from
     */
    public Station getStation() {
        return this.station;
    }
}
