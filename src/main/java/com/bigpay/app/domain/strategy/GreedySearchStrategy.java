package com.bigpay.app.domain.strategy;

import com.bigpay.app.domain.Letter;
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

    @Override
    public TimeStep getNextTimeStep(RoadMap roadMap) {
        this.roadMap = roadMap;

        this.letters = Arrays.stream(this.roadMap.getLetters())
                .filter(letter -> !letter.isInProcessing() && !letter.isArrived())
                .collect(Collectors.toSet());

        TimeStep timeStep = new TimeStep(Set.of(this.roadMap.getTrains()));

        boolean hasNextAction = true;
        while (hasNextAction) {
            Set<TrainAction> trainActions = prepareNextAction(timeStep);
            trainActions.forEach(timeStep::addAction);
            hasNextAction = !trainActions.isEmpty();
        }

        return timeStep;
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

                } else if (lastAction.getActionType() == TrainActionType.ARRIVE) {

                    trainActions.add(new TrainAction(this.roadMap, train, new TrainUnloadActionType(train)));

                } else if (lastAction.getActionType() == TrainActionType.DEPART) {

                    trainActions.add(new TrainAction(this.roadMap, train, new TrainMoveActionType(train)));

                } else if (lastAction.getActionType() == TrainActionType.MOVE) {

                    List<TrainDepartActionType> departActionTypes = timeStep.getActionMap().get(train)
                            .stream()
                            .filter(action -> action instanceof TrainDepartActionType)
                            .map(action -> (TrainDepartActionType)action)
                            .collect(Collectors.toList());

                    if (departActionTypes.size() > 0) {
                        if (departActionTypes.get(0).getRoad().getTime() == 1) {
                            trainActions.add(new TrainAction(this.roadMap, train, new TrainArriveActionType(train)));
                        }
                    } else if (train.getRoad() != null) {
                        if (train.getRoad().getTime() <= train.getTimeOnRoad() + 1) {
                            trainActions.add(new TrainAction(this.roadMap, train, new TrainArriveActionType(train)));
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
            return new TrainMoveActionType(train);
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
                return new TrainUnloadActionType(train);
            } else {
                return new TrainDepartActionType(train, this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][deliverLetter.getFinalDest().getIndex()][0]);
            }
        } else {
            if (closestLetterDistance == 0) {
                this.letters.remove(closestLetter);
                return new TrainLoadActionType(train, closestLetter);
            } else {
                return new TrainDepartActionType(train, this.roadMap.getShortestRoadMap()[train.getStation().getIndex()][closestLetter.getCurrentDest().getIndex()][0]);
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
}
