package com.bigpay.app.service;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;
import com.bigpay.app.domain.action.AbstractTrainAction;
import com.bigpay.app.domain.action.TrainCollectAction;
import com.bigpay.app.domain.action.TrainDeliverAction;

import java.util.*;

/**
 *
 */
public class SearchService2 {
    private static SearchService2 instance;

    private Map<Train, AbstractTrainAction> trainActionMap;
    private RoadMap roadMap;
    private Road[][][] shortestRoadsMap;
    private static FloydWarshallSearchComponent floydWarshallSearchComponent = FloydWarshallSearchComponent.getInstance();

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

        this.trainActionMap = getNextAction(Set.of(this.roadMap.getTrains()), Set.of(this.roadMap.getLetters()));

        for(Train train : this.trainActionMap.keySet()) {
            int distance = floydWarshallSearchComponent
                    .getDistance(train.getStation(), this.trainActionMap.get(train).getLetter().getInitialDest());

            System.out.println(String.format("%s %s %d", train.getName(),
                    this.trainActionMap.get(train).getLetter().getName(), distance));
        }
    }

    /**
     * Finds closest distance to letter for collection/delivery for each train and chooses closest distance action.
     *
     * @param trains
     * @param letters
     * @return map of train and closes letter
     */
    private static Map<Train, AbstractTrainAction> getNextAction(Set<Train> trains, Set<Letter> letters) {

        Map<Train, AbstractTrainAction> trainActionMap = new HashMap<>(trains.size());

        Set<Letter> tempLetterSet = new HashSet<>(letters);

        for (Train train : trains) {

            // 1. Find closest letter that can be collected
            Letter closestLetter = findClosestLetter(train, tempLetterSet);

            // 2. Find closest letter that can be delivered
            Letter deliverLetter = deliverClosestLetter(train);

            // 3. Calculates distance to closest letter for collection
            int closestLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), closestLetter.getInitialDest());

            int deliverLetterDistance = Integer.MAX_VALUE;

            // 4. Check if thain has collected any letters
            if (train.getLetters().size() > 0) {

                // 4.1. Calculate distance to closest letter for delivery
                deliverLetterDistance = floydWarshallSearchComponent.getDistance(train.getStation(), deliverLetter.getFinalDest());
            }

            System.out.println(String.format("collect %d, delivery %d", closestLetterDistance, deliverLetterDistance));

            // 5. Chooses closest distance
            if (closestLetterDistance > deliverLetterDistance) {

                trainActionMap.put(train, new TrainDeliverAction(deliverLetter));

            } else {

                trainActionMap.put(train, new TrainCollectAction(closestLetter));
            }

            tempLetterSet.remove(closestLetter);
        }

        return trainActionMap;
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
