package com.bigpay.app.domain.strategy;

import com.bigpay.app.component.exception.WrondRoadMapStateException;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
                .filter(letter -> !letter.isInProcessing() && !letter.isArrived())
                .collect(Collectors.toSet());

        TimeStep timeStep = new TimeStep(timeStepNumber++, Set.of(this.roadMap.getTrains()));

        prepareNextAction2(timeStep);

        return timeStep;
    }

    private void prepareNextAction2(TimeStep timeStep) {

        Set<Letter> letters = Arrays.stream(this.roadMap.getLetters())
                .filter(letter -> !letter.isArrived()).collect(Collectors.toSet());

        for (Train train : this.roadMap.getTrains()) {
            if (train.getRoad() != null) {

                timeStep.addAction(new TrainAction(this.roadMap, train,
                        new TrainMoveActionType(train, train.getRoad()
                                .getCounterStation(train.getNextStation()), train.getNextStation())));

                if (train.getRoad().getTime() <= train.getTimeOnRoad() + 1) {

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
                    Letter closestLetter = findClosestLetter(train, this.letters.stream()
                            .filter(letter -> letter.getWeight() <= train.getCapacity())
                            .filter(letter -> !letter.isArrived() && !letter.isInProcessing())
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

                    if (road.getTime() == 1) {

                        timeStep.addAction(new TrainAction(this.roadMap, train,
                                new TrainArriveActionType(train, road.getCounterStation(train.getStation()))));

                        timeStep.addAction(new TrainAction(this.roadMap, train,
                                new TrainUnloadActionType(train, road.getCounterStation(train.getStation()))));
                    }
                }

            } else {
                throw new WrondRoadMapStateException(String.format("Unknown train %s position", train.getName()));
            }
        }
    }

    private Set<TrainAction> prepareNextAction(TimeStep timeStep) {

        Set<TrainAction> trainActions = new HashSet<>();

        for (Train train : roadMap.getTrains()) {

            TrainActionable lastAction = timeStep.getLastTrainAction(train);

            if (lastAction != null) {
                if (lastAction.getActionType() == TrainActionType.LOAD) {

                    TrainActionable action = getNextAction(train, timeStep);

                    if (action != null) {
                        trainActions.add(new TrainAction(this.roadMap, train, action));
                    }

                } else if (lastAction.getActionType() == TrainActionType.UNLOAD) {


                } else if (lastAction.getActionType() == TrainActionType.ARRIVE) {

                    trainActions.add(new TrainAction(this.roadMap, train,
                            new TrainUnloadActionType(train, ((TrainArriveActionType)lastAction).getStation())));

                } else if (lastAction.getActionType() == TrainActionType.DEPART) {

                    trainActions.add(new TrainAction(this.roadMap, train,
                            new TrainMoveActionType(train,
                                    ((TrainDepartActionType)lastAction).getFromStation(),
                                    ((TrainDepartActionType)lastAction).getToStation())));

                } else if (lastAction.getActionType() == TrainActionType.MOVE) {

                    List<TrainDepartActionType> departActionTypes = timeStep.getActionMap().get(train)
                            .stream()
                            .filter(action -> action instanceof TrainDepartActionType)
                            .map(action -> (TrainDepartActionType)action)
                            .collect(Collectors.toList());

                    if (departActionTypes.size() > 0) {
                        if (departActionTypes.get(0).getRoad().getTime() == 1) {
                            trainActions.add(new TrainAction(this.roadMap, train,
                                    new TrainArriveActionType(train,
                                            ((TrainMoveActionType)lastAction).getToStation())));
                        }
                    } else if (train.getRoad() != null) {
                        if (train.getRoad().getTime() <= train.getTimeOnRoad() + 1) {
                            trainActions.add(new TrainAction(this.roadMap, train,
                                    new TrainArriveActionType(train,
                                            ((TrainMoveActionType)lastAction).getToStation())));
                        }
                    }

                }
            } else {
                TrainActionable action = getNextAction(train, timeStep);

                if (action != null) {
                    if (action.getActionType() == TrainActionType.MOVE) {
                        if (timeStep.hasTrainFreeTime(train)) {
                            trainActions.add(new TrainAction(this.roadMap, train, action));
                        }
                    } else {
                        trainActions.add(new TrainAction(this.roadMap, train, action));
                    }
                }
            }
        }

        return trainActions;
    }

    private TrainActionable getNextAction(Train train, TimeStep timeStep) {

        if ((train.getStation() == null) && (train.getRoad() != null)) {
            return new TrainMoveActionType(train, train.getRoad().getCounterStation(train.getNextStation()), train.getNextStation());
        }

        // 1. Find closest letter that can be collected
        int trainFreeCapacity = timeStep.getTrainFreeSpace(train);
        Letter closestLetter = findClosestLetter(train, this.letters.stream()
                .filter(letter -> letter.getWeight() <= trainFreeCapacity)
                .filter(letter -> !letter.isArrived() && !letter.isInProcessing())
                .collect(Collectors.toSet()));

        int closestLetterDistance = Integer.MAX_VALUE;
        if (closestLetter != null) {
            closestLetterDistance = this.roadMap.getDistance(train.getStation(), closestLetter.getCurrentDest());
        }

        // 2. Find closest letter that can be delivered
        Letter deliverLetter = deliverClosestLetter(train, timeStep.getPreparedForDeliveryLetter(train));
        int deliverLetterDistance = Integer.MAX_VALUE;
        // 4. Check if thain has collected any letters
        if (deliverLetter != null) {

            // 4.1. Calculate distance to closest letter for delivery
            deliverLetterDistance = this.roadMap.getDistance(train.getStation(), deliverLetter.getFinalDest());
        }

        if (closestLetterDistance == Integer.MAX_VALUE && deliverLetterDistance == Integer.MAX_VALUE){
            return null;
        }


        // 5. Chooses closest distance
        if (closestLetterDistance > deliverLetterDistance) {

            if (deliverLetterDistance == 0) {
                return new TrainUnloadActionType(train, deliverLetter.getFinalDest());
            } else {
                Road road = this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][deliverLetter.getFinalDest().getIndex()][0];
                return new TrainDepartActionType(train, road, train.getStation(), road.getCounterStation(train.getStation()));
            }
        } else {
            if (closestLetterDistance == 0) {
                this.letters.remove(closestLetter);
                return new TrainLoadActionType(train, closestLetter, closestLetter.getCurrentDest());
            } else {
                Road road = this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][closestLetter.getCurrentDest().getIndex()][0];
                return new TrainDepartActionType(train, road, train.getStation(), road.getCounterStation(train.getStation()));
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

    /**
     *
     * @param train
     * @return
     */
    private Letter deliverClosestLetter(Train train, Set<Letter> additionalLetters) {

        Letter minPathLetter = null;
        int minPath = Integer.MAX_VALUE;

        for (Letter letter : train.getLetters()) {

            int distance = this.roadMap.getDistance(train.getStation(), letter.getFinalDest());

            if (distance < minPath) {
                minPath = distance;
                minPathLetter = letter;
            }
        }

        for (Letter letter : additionalLetters) {

            int distance = this.roadMap.getDistance(train.getStation(), letter.getFinalDest());

            if (distance < minPath) {
                minPath = distance;
                minPathLetter = letter;
            }
        }

        return minPathLetter;
    }

    private Road getLetterFavoriteDirection(Letter letter) {
        return this.roadMap.getShortestRoadMap()[letter.getCurrentDest().getIndex()][letter.getFinalDest().getIndex()][0];
    }
}
