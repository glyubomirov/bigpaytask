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

public class GreedySearchStrategy extends AbstractSearchStrategy {

    private RoadMap roadMap;
    private Set<Letter> letters;
    private int timeStepNumber = 1;

    @Override
    public TimeStep getNextTimeStep(RoadMap roadMap) {
        this.roadMap = roadMap;

        this.letters = Arrays.stream(this.roadMap.getLetters())
                .filter(letter -> !letter.isInProcessing() && !letter.isDelivered())
                .collect(Collectors.toSet());

        TimeStep timeStep = new TimeStep(timeStepNumber++, Set.of(this.roadMap.getTrains()));

        prepareNextAction(timeStep);

        return timeStep;
    }

    private void prepareNextAction(TimeStep timeStep) {

        Set<Letter> letters = Arrays.stream(this.roadMap.getLetters())
                .filter(letter -> !letter.isDelivered() && !letter.isInProcessing()).collect(Collectors.toSet());

        for (Train train : this.roadMap.getTrains()) {
            if (train.getRoad() != null) {

                timeStep.addAction(new TrainAction(this.roadMap, train,
                        new TrainMoveActionType(train, train.getRoad()
                                .getCounterStation(train.getNextStation()), train.getNextStation())));

                if (train.getRoad().getTimeSteps() <= train.getTimeOnRoad() + 1) {

                    timeStep.addAction(new TrainAction(this.roadMap, train,
                            new TrainArriveActionType(train, train.getNextStation())));

                    timeStep.addAction(new TrainAction(this.roadMap, train,
                            new TrainUnloadActionType(train, train.getNextStation())));
                }

            } else if (train.getStation() != null) {
                Road road = null;
                Set<Letter> removedLetters = new HashSet<>(letters.size());
                for (Letter letter: letters) {
                    if (letter.getCurrentDest() == train.getStation()) {
                        if (timeStep.getTrainFreeSpace(train) >= letter.getWeight()) {
                            if (timeStep.getTrainFreeSpace(train) == train.getCapacity()) {
                                road = roadMap.getShortestRoadMap()[letter.getCurrentDest().getIndex()][letter.getFinalDest().getIndex()][0];
                                timeStep.addAction(new TrainAction(this.roadMap, train,
                                        new TrainLoadActionType(train, letter, letter.getCurrentDest())));
                                removedLetters.add(letter);
                            } else {
                                if (road == roadMap.getShortestRoadMap()[letter.getCurrentDest().getIndex()][letter.getFinalDest().getIndex()][0]) {
                                    timeStep.addAction(new TrainAction(this.roadMap, train,
                                            new TrainLoadActionType(train, letter, letter.getCurrentDest())));
                                    removedLetters.add(letter);
                                }
                            }
                        }
                    }
                }

                letters.removeAll(removedLetters);

                if (road == null) {
                    Letter closestLetter = findClosestLetter(train, letters.stream()
                            .filter(letter -> letter.getWeight() <= train.getCapacity())
                            .filter(letter -> !letter.isDelivered() && !letter.isInProcessing())
                            .collect(Collectors.toSet()));

                    if (closestLetter != null) {
                        road = this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][closestLetter.getCurrentDest().getIndex()][0];
                    }
                }

                if (road != null) {
                    timeStep.addAction(new TrainAction(this.roadMap, train,
                            new TrainDepartActionType(train,
                                    road, train.getStation(), road.getCounterStation(train.getStation()))));

                    timeStep.addAction(new TrainAction(this.roadMap, train,
                            new TrainMoveActionType(train,
                                    train.getStation(), road.getCounterStation(train.getStation()))));

                    if (road.getTimeSteps() == 1) {

                        timeStep.addAction(new TrainAction(this.roadMap, train,
                                new TrainArriveActionType(train, road.getCounterStation(train.getStation()))));

                        timeStep.addAction(new TrainAction(this.roadMap, train,
                                new TrainUnloadActionType(train, road.getCounterStation(train.getStation()))));
                    }
                }

            } else {
                throw new WrongRoadMapStateException(String.format("Unknown train %s position", train.getName()));
            }
        }
    }

    /**
     *
     * @param train
     * @param letters
     * @return
     */
    private Letter findClosestLetter(Train train, Set<Letter> letters) {

        Letter minPathLetter = null;
        int minPath = Integer.MAX_VALUE;

        for (Letter letter : letters) {

            if (train.getFreeCapacity() >= letter.getWeight()) {

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
