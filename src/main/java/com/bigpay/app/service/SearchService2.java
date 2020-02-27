package com.bigpay.app.service;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class SearchService2 {
    private static SearchService2 instance;

    private Map<Train, TrainActionable> trainActionMap;
    private RoadMap roadMap;
    private Road[][][] shortestRoadsMap;
    private static FloydWarshallSearchComponent floydWarshallSearchComponent = FloydWarshallSearchComponent.getInstance();
    private static TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();
    private Set<Letter> letters;

    public static synchronized SearchService2 getInstance() {
        if (instance == null) {
            instance = new SearchService2();
        }

        return instance;
    }

    /**
     *
     * @param roadMap
     * @param shortestRoadsMap
     */
    public void search(RoadMap roadMap, Road[][][] shortestRoadsMap) {
        this.trainActionMap = new HashMap<>();
        this.roadMap = roadMap;
        this.shortestRoadsMap = shortestRoadsMap.clone();

        TimeStep timeStep = new TimeStep(Set.of(this.roadMap.getTrains()));

//        Arrays.stream(roadMap.getTrains()).forEach(train -> timeStep.addAction(new TrainAction(train, new TrainNoneActionType(train))));

        this.letters = Arrays.stream(roadMap.getLetters())
                .filter(letter -> !letter.isInProcessing() && !letter.isArrived())
                .collect(Collectors.toSet());

        boolean hasNextAction = true;
        while (hasNextAction) {
            Set<TrainAction> trainActions = prepareNextAction(timeStep);
            trainActions.forEach(timeStep::addAction);
            hasNextAction = !trainActions.isEmpty();
        }

        timeStepComponent.addTimeStep(timeStep);

//        this.trainActionMap = getNextAction(Set.of(this.roadMap.getTrains()), Set.of(this.roadMap.getLetters()));

//        for(Train train : this.trainActionMap.keySet()) {
//            int distance = floydWarshallSearchComponent
//                    .getDistance(train.getStation(), this.trainActionMap.get(train).getLetter().getInitialDest());
//
//            System.out.println(String.format("%s %s %d", train.getName(),
//                    this.trainActionMap.get(train).getLetter().getName(), distance));
//        }
    }

    private Set<TrainAction> prepareNextAction(TimeStep timeStep) {

        Set<TrainAction> trainActions = new HashSet<>();

        Set<Train> trainSet = timeStep.getTrainsWithFreeTime();

        for (Train train : trainSet) {

            if (timeStep.getLastTrainAction(train) == TrainActionType.LOAD) {

                TrainActionable action = getNextAction(train);

                if (action != null) {
                    trainActions.add(new TrainAction(train, action));
                }

            } else if (timeStep.getLastTrainAction(train) == TrainActionType.UNLOAD) {

                TrainActionable action = getNextAction(train);

                if (action != null) {
                    trainActions.add(new TrainAction(train, action));
                } else {
                    if (timeStep.getTrainFreeSpace(train) == train.getCapacity()) {
                        //TODO: Train Departure
                    }
                }

            } else if (timeStep.getLastTrainAction(train)== TrainActionType.ARRIVE) {

                trainActions.add(new TrainAction(train, new TrainUnloadActionType(train)));

            } else if (timeStep.getLastTrainAction(train) == TrainActionType.DEPART) {

                trainActions.add(new TrainAction(train, new TrainMoveActionType(train)));

            } else if (timeStep.getLastTrainAction(train) == TrainActionType.MOVE) {

//                timeStep.addAction(new TrainAction(train, new TrainMoveActionType(train)));

            } else {
                TrainActionable action = getNextAction(train);

                if (action != null) {
                    trainActions.add(new TrainAction(train, action));
                }
            }
        }

        return trainActions;
    }

//    /**
//     * Finds closest distance to letter for collection/delivery for each train and chooses closest distance action.
//     *
//     * @param trains
//     * @param letters
//     * @return map of train and closes letter
//     */
//    private static Map<Train, TrainActionable> getNextAction(Set<Train> trains, Set<Letter> letters) {
//
//        Map<Train, TrainActionable> trainActionMap = new HashMap<>(trains.size());
//
//        Set<Letter> tempLetterSet = new HashSet<>(letters);
//
//        if (trains.isEmpty()) {
//            return Map.of();
//        }
//
//        if(letters.isEmpty()) {
//            return trains.stream().forEach();
//        }
//
//        for (Train train : trains) {
//
//            // 1. Find closest letter that can be collected
//            Letter closestLetter = findClosestLetter(train, tempLetterSet);
//
//            // 2. Find closest letter that can be delivered
//            Letter deliverLetter = deliverClosestLetter(train);
//
//            // 3. Calculates distance to closest letter for collection
//            int closestLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), closestLetter.getInitialDest());
//
//            int deliverLetterDistance = Integer.MAX_VALUE;
//
//            // 4. Check if thain has collected any letters
//            if (train.getLetters().size() > 0) {
//
//                // 4.1. Calculate distance to closest letter for delivery
//                deliverLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), deliverLetter.getFinalDest());
//            }
//
//            System.out.println(String.format("collect %d, delivery %d", closestLetterDistance, deliverLetterDistance));
//
//            // 5. Chooses closest distance
//            if (closestLetterDistance > deliverLetterDistance) {
//
//                trainActionMap.put(train, new TrainDeliverActionable(deliverLetter));
//
//            } else {
//
//                trainActionMap.put(train, new TrainMoveActionType(closestLetter));
//            }
//
//            tempLetterSet.remove(closestLetter);
//        }
//
//        return trainActionMap;
//    }

    private TrainActionable getNextAction(Train train) {

        // 1. Find closest letter that can be collected
        int trainFreeCapacity =  train.getFreeCapacity();
        Letter closestLetter = findClosestLetter(train, this.letters.stream()
                .filter(letter -> letter.getWeight() <= trainFreeCapacity)
                .collect(Collectors.toSet()));

        int closestLetterDistance = Integer.MAX_VALUE;
        if (closestLetter != null) {
            closestLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), closestLetter.getInitialDest());
        }

        // 2. Find closest letter that can be delivered
        Letter deliverLetter = deliverClosestLetter(train);
        int deliverLetterDistance = Integer.MAX_VALUE;
        // 4. Check if thain has collected any letters
        if (deliverLetter != null) {

            // 4.1. Calculate distance to closest letter for delivery
            deliverLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), deliverLetter.getFinalDest());
        }

        if (closestLetterDistance == Integer.MAX_VALUE && deliverLetterDistance == Integer.MAX_VALUE){
            return null;
        }

        // 5. Chooses closest distance
        if (closestLetterDistance > deliverLetterDistance) {

            if (deliverLetterDistance == 0) {
                return new TrainUnloadActionType(train);
            } else {
                return new TrainDepartActionType(train, this.shortestRoadsMap[train.getStation().getIndex()][deliverLetter.getFinalDest().getIndex()][0]);
            }
        } else {
            if (closestLetterDistance == 0) {

                this.letters.remove(closestLetter);
                return new TrainLoadActionType(train, closestLetter);
            } else {
                return new TrainDepartActionType(train, this.shortestRoadsMap[train.getStation().getIndex()][closestLetter.getCurrentDest().getIndex()][0]);
            }
        }
    }

    /**
     *
     * @param train
     * @param letters
     * @return
     */
    private static Letter findClosestLetter(Train train, Set<Letter> letters) {

        Letter minPathLetter = null;
        int minPath = Integer.MAX_VALUE;

        for (Letter letter : letters) {

            if (train.getFreeCapacity() >= letter.getWeight()) {

                int distance = floydWarshallSearchComponent.getDistance(train.getStation(), letter.getInitialDest());

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
    private static Letter deliverClosestLetter(Train train) {

        Letter minPathLetter = null;
        int minPath = Integer.MAX_VALUE;

        for (Letter letter : train.getLetters()) {

            int distance = floydWarshallSearchComponent.getDistance(train.getStation(), letter.getFinalDest());

            if (distance < minPath) {
                minPath = distance;
                minPathLetter = letter;
            }
        }

        return minPathLetter;
    }
}
