package com.bigpay.app.domain.strategy;

import com.bigpay.app.component.exception.WrongRoadMapStateException;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Implementation of greedy algorithm with some modification for this purpose
 *
 * @author ggeorgiev
 */
public class GreedySearchStrategy extends AbstractSearchStrategy {

    private RoadMap roadMap;
    private int timeStepNumber = 1;

    /**
     * Return next time step
     *
     * @param roadMap road map
     * @return time step
     */
    @Override
    public TimeStep getNextTimeStep(RoadMap roadMap) {
        this.roadMap = roadMap;

        TimeStep timeStep = new TimeStep(timeStepNumber++, Set.of(this.roadMap.getTrains()));

        prepareNextAction(timeStep);

        return timeStep;
    }

    /**
     * Prepares next next action
     *
     * @param timeStep time step
     */
    private void prepareNextAction(TimeStep timeStep) {

        // set of potential letters for collection
        Set<Letter> letters = Arrays.stream(this.roadMap.getLetters())
                .filter(letter -> !letter.isDelivered() && !letter.isInProcessing()).collect(Collectors.toSet());

        // Iterate over all rains
        for (Train train : this.roadMap.getTrains()) {

            // If train is on the track
            if (train.getRoad() != null) {

                // Adds move action
                timeStep.addAction(new TrainMoveActionType(train, train.getRoad()
                                .getCounterStation(train.getNextStation()), train.getNextStation()));

                // If train is 1 time step to station then adds Arrive and Unload actions
                if (train.getRoad().getTimeSteps() <= train.getTimeOnRoad() + 1) {

                    timeStep.addAction(new TrainArriveActionType(train, train.getNextStation()));

                    timeStep.addAction(new TrainUnloadActionType(train, train.getNextStation()));
                }

            } else if (train.getStation() != null) { // If train is in the station
                Road road = null;
                Set<Letter> removedLetters = new HashSet<>(letters.size());

                // Searches all letters that can be collected
                for (Letter letter: letters) {

                    // If train and letter are on the same station
                    if (letter.getCurrentDest() == train.getStation()) {

                        // If train can collect letter
                        if (timeStep.getTrainFreeSpace(train) >= letter.getWeight()) {

                            // If this is first latter that has to be loaded to train
                            if (timeStep.getTrainFreeSpace(train) == train.getCapacity()) {

                                // adds load action
                                road = roadMap.getShortestRoadMap()[letter.getCurrentDest().getIndex()][letter.getFinalDest().getIndex()][0];
                                timeStep.addAction(new TrainLoadActionType(train, letter, letter.getCurrentDest()));
                                removedLetters.add(letter);
                            } else { // If this is not first latter that has to be loaded to train

                                // if letter is in the same direction as already loaded letters
                                if (road == roadMap.getShortestRoadMap()[letter.getCurrentDest().getIndex()][letter.getFinalDest().getIndex()][0]) {

                                    // adds load action
                                    timeStep.addAction(new TrainLoadActionType(train, letter, letter.getCurrentDest()));
                                    removedLetters.add(letter);
                                }
                            }
                        }
                    }
                }

                // removes letters that are planned to be loaded
                letters.removeAll(removedLetters);

                // If thain is not on the road
                if (road == null) {

                    //find closest letter
                    Letter closestLetter = findClosestLetter(train, letters.stream()
                            .filter(letter -> letter.getWeight() <= train.getCapacity())
                            .filter(letter -> !letter.isDelivered() && !letter.isInProcessing())
                            .collect(Collectors.toSet()));

                    // if there is closest letter
                    if (closestLetter != null) {
                        // sets road as direction fot closest letter
                        road = this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][closestLetter.getCurrentDest().getIndex()][0];
                    }
                }

                // If there is road
                if (road != null) {

                    // Adds depart action for thee train
                    timeStep.addAction(new TrainDepartActionType(train,
                                    road, train.getStation(), road.getCounterStation(train.getStation())));

                    // Adds move action for thee train
                    timeStep.addAction(new TrainMoveActionType(train,
                                    train.getStation(), road.getCounterStation(train.getStation())));

                    // If road is only 1 time step
                    if (road.getTimeSteps() == 1) {

                        // Adds arrives action for thee train
                        timeStep.addAction(new TrainArriveActionType(train, road.getCounterStation(train.getStation())));

                        // Adds unload action for thee train
                        timeStep.addAction(new TrainUnloadActionType(train, road.getCounterStation(train.getStation())));
                    }
                }

            } else { // if thain is not on the road and not at the station
                throw new WrongRoadMapStateException(String.format("Unknown train %s position", train.getName()));
            }
        }
    }

    /**
     * Find closest path letter from train
     *
     * @param train train that plans to collect letter
     * @param letters set of possible letters for collection
     * @return letter that has to be collected or null if there is no letter
     */
    private Letter findClosestLetter(Train train, Set<Letter> letters) {

        Letter minPathLetter = null;
        int minPath = Integer.MAX_VALUE;

        for (Letter letter : letters) {

            if (train.getFreeStace() >= letter.getWeight()) {

                int distance = this.roadMap.getDistance(train.getStation(), letter.getInitialDest());

                if (distance < minPath) {
                    minPath = distance;
                    minPathLetter = letter;
                }
            }
        }

        return minPathLetter;
    }
}
