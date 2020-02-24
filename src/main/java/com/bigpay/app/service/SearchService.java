package com.bigpay.app.service;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Train;

import java.util.*;

/**
 * Perform search for optimal letter delivery.
 * Algorithm:
 *
 * 1. Calculate average letter
 *
 *
 */
public class SearchService {

    private RoadMap roadMap;
    private Road[][][] shortestRoadsMap;

    private List<Map.Entry<Train, Letter>> bestTrainDistList;
    private int bestTrainDistTime = Integer.MAX_VALUE;
    private boolean stopSearchSignal = false;
    private long iterations = 0;

    private static SearchService instance;

    public static synchronized SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }

        return instance;
    }

    /**
     * Search algorithm
     *
     * @param roadMap
     * @param shortestRoadsMap
     */
    public void search(RoadMap roadMap, Road[][][] shortestRoadsMap, int seconds) {
        this.roadMap = roadMap;
        this.shortestRoadsMap = shortestRoadsMap;

        Set<Letter> letters = new HashSet<>(Set.of(roadMap.getLetters()));
        Set<Train> trains = new HashSet<>(Set.of(roadMap.getTrains()));
        Map<Train, Letter> tempsTrainDistPlan = new HashMap<>(trains.size());

//        this.setSearchTimeout(seconds);

//        findBestLetterDistribution(trains, letters, tempsTrainDistPlan);

        bestTrainDistList = findClosestLetter(trains, letters);
        bestTrainDistTime = findLongestDeliveryTime(bestTrainDistList);

        // Find better solution fot the slowest 6 letter delivery
        findBetterPathPermutation(new ArrayList<>(bestTrainDistList), 0, Math.min(6, bestTrainDistList.size()));

        for (Map.Entry<Train, Letter> trainEntry : bestTrainDistList) {

            Train train = trainEntry.getKey();
            Letter letter = trainEntry.getValue();

            int distance =
                    FloydWarshallSearchComponent.getInstance()
                            .getDistance(train.getStation(), letter.getInitialDest()) +
                            FloydWarshallSearchComponent.getInstance()
                                    .getDistance(letter.getInitialDest(), letter.getFinalDest());

            System.out.println(String.format("%s %s %d", train.getName(), letter.getName(), distance));
        }
    }

    private void findBetterPathPermutation(List<Map.Entry<Train, Letter>> trainDistList, int left, int right)
    {
        if (left == right) {
            int deliveryTime = findLongestDeliveryTime(trainDistList);
            if (deliveryTime < bestTrainDistTime) {
                bestTrainDistTime = deliveryTime;
                bestTrainDistList = new ArrayList<>(trainDistList.size());

                // TODO: not the best solution, must think for better
                trainDistList.forEach(item -> {
                    Map<Train, Letter> tempItem = new HashMap<>();
                    tempItem.put(item.getKey(), item.getValue());
                    bestTrainDistList.add(tempItem.entrySet().iterator().next());
                });
            }
        } else {
            for (int i = left; i <= right; i++) {
                Letter letter = trainDistList.get(left).getValue();
                trainDistList.get(left).setValue(trainDistList.get(i).getValue());
                trainDistList.get(i).setValue(letter);

                findBetterPathPermutation(trainDistList, left + 1, right);

                letter = trainDistList.get(left).getValue();
                trainDistList.get(left).setValue(trainDistList.get(i).getValue());
                trainDistList.get(i).setValue(letter);
            }
        }
    }

    /**
     *
     * @param availableTrains
     * @param availableLetters
     * @param tempTrainDistMap
     */
//    private void findBestLetterDistribution(Set<Train> availableTrains, Set<Letter> availableLetters,
//                                                           Map<Train, Letter> tempTrainDistMap) {
//        Set<Letter> letters = new HashSet<>(availableLetters);
//        Set<Train> trains = new HashSet<>(availableTrains);
//
//        if (trains.isEmpty() || letters.isEmpty()) {
//
//            int maxDeliveryTime = getLongestDeliveryTime(tempTrainDistMap);
//
//            System.out.println(maxDeliveryTime);
//
//            if(maxDeliveryTime < bestTrainDistTime) {
//                this.bestTrainDistMap = new HashMap<>(tempTrainDistMap);
//                this.bestTrainDistTime = maxDeliveryTime;
//
//                System.out.println(bestTrainDistTime);
//
//                for (Train train : tempTrainDistMap.keySet()) {
//                    Letter letter = tempTrainDistMap.get(train);
//                    System.out.println(String.format("%s %s", train.getName(), letter.getName()));
//                }
//                System.out.println();
//            }
//
//            return;
//        }
//
//        if (this.stopSearchSignal) {
//            return;
//        }
//
//        for (Train train : trains) {
//            for (Letter letter : letters) {
//                this.iterations++;
//                if (train.getFreeCapacity() < letter.getWeight()) {
//                    continue;
//                }
//
//                // Add current result as temp result
//                tempTrainDistMap.put(train, letter);
//
//                // Remove processed items
//                this.findBestLetterDistribution(
//                        trains.stream().filter(item -> item != train).collect(Collectors.toSet()),
//                        letters.stream().filter(item -> item != letter).collect(Collectors.toSet()),
//                        tempTrainDistMap);
//
//                // Remove current result from temp result
//                tempTrainDistMap.remove(train);
//            }
//        }
//    }

    /**
     * Find closest stations with undelivered letters based on train capacity
     *
     * @param trains
     * @param letters
     * @return
     */
    private static List<Map.Entry<Train, Letter>> findClosestLetter(Set<Train> trains, Set<Letter> letters) {

        Map<Train, Letter> trainPathMap = new HashMap<>(trains.size());

        Set<Letter> tempLetters = new HashSet<>(letters);

        // calculates distance from train to letter and to final destination
        for (Train train : trains) {

            int minPath = Integer.MAX_VALUE;

            for (Letter letter : tempLetters) {

                int distance =
                        FloydWarshallSearchComponent.getInstance()
                                .getDistance(train.getStation(), letter.getInitialDest()) +
                        FloydWarshallSearchComponent.getInstance()
                                .getDistance(letter.getInitialDest(), letter.getFinalDest());

                if (distance < minPath) {
                    minPath = distance;
                    trainPathMap.put(train, letter);
                }
            }

            tempLetters.remove(trainPathMap.get(train));

        }

        return sortClosestLetter(trainPathMap);
    }

    /**
     * Sort Train-Letter delivery by time required for delivery
     * @param trainPathMap
     * @return
     */
    private static List<Map.Entry<Train, Letter>> sortClosestLetter(Map<Train, Letter> trainPathMap) {

        ArrayList<Map.Entry<Train, Letter>> sortedArray = new ArrayList<>(trainPathMap.entrySet());

        sortedArray.sort((o1, o2) -> {
            int distance1 =
                    FloydWarshallSearchComponent.getInstance()
                            .getDistance(o1.getKey().getStation(), o1.getValue().getInitialDest()) +
                            FloydWarshallSearchComponent.getInstance()
                                    .getDistance(o1.getValue().getInitialDest(), o1.getValue().getFinalDest());

            int distance2 =
                    FloydWarshallSearchComponent.getInstance()
                            .getDistance(o2.getKey().getStation(), o2.getValue().getInitialDest()) +
                            FloydWarshallSearchComponent.getInstance()
                                    .getDistance(o2.getValue().getInitialDest(), o2.getValue().getFinalDest());

            return distance2 - distance1;
        });

        return sortedArray;
    }

    /**
     *
     * @param trainDistList
     * @return
     */
    private static int findLongestDeliveryTime(List<Map.Entry<Train, Letter>> trainDistList) {
        int maxTime = 0;

        for (Map.Entry<Train, Letter> pair : trainDistList) {
            Train train = pair.getKey();
            Letter letter = pair.getValue();
            int distance =
                    FloydWarshallSearchComponent.getInstance()
                            .getDistance(train.getStation(), letter.getInitialDest()) +
                    FloydWarshallSearchComponent.getInstance()
                            .getDistance(letter.getInitialDest(), letter.getFinalDest());
            if (distance > maxTime) {
                maxTime = distance;
            }
        }

        return maxTime;
    }

    private void setSearchTimeout(int seconds) {

        this.stopSearchSignal = false;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                stopSearchSignal = true;
            }
        }, seconds * 1000L, 1);
    }
}
