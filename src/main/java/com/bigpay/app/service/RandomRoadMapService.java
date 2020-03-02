package com.bigpay.app.service;

import com.bigpay.app.domain.*;

import java.util.*;

/**
 * This class is used for testing purposes. It generates random RoadMap based on some basic criteria
 *
 * @author ggeorgiev
 */
public class RandomRoadMapService {

    public static RoadMap generate(int stationCount, int roadCount, int letterCount, int trainCount, int seed) {

        Random rand = new Random(seed);

        Station[] stations = new Station[stationCount];
        for (int i = 0; i < stationCount; i++) {
            stations[i] = new Station(getStationName(i), i);
        }

        // create fully connected graph with random weights
        Road[] stationMatrix = new Road[stationCount * stationCount];
        Set<Road> roadSet = new HashSet<>();
        for (int i = 0; i < stationCount; i++) {
            for (int j = i + 1; j < stationCount; j++) {
                int nextInt = rand.nextInt(10) + 1;
                Road road = new Road(nextInt, stations[i], stations[j]);
                stationMatrix[i * stationCount + j] = road;
                stationMatrix[i + j * stationCount] = road;
                roadSet.add(road);
            }
        }

        while(roadSet.size() > roadCount) {
            roadSet.remove(stationMatrix[rand.nextInt(stationCount * stationCount)]);
        }

        Station[] shuffledStationsOrder = stations.clone();

        Letter[] letters = new Letter[letterCount];
        for (int i = 0; i < letterCount; i++) {
            shuffleStationList(shuffledStationsOrder, rand);
            letters[i] = new Letter(String.format("D%d", i + 1), shuffledStationsOrder[0], shuffledStationsOrder[1], rand.nextInt(20));
        }

        Train[] trains = new Train[trainCount];
        for (int i = 0; i < trainCount; i++) {
            shuffleStationList(shuffledStationsOrder, rand);
            trains[i] = new Train(String.format("T%d", i + 1), shuffledStationsOrder[0], rand.nextInt(80) + 20);
        }

        // Bind roads to each Station
        roadSet.forEach(road -> road.getStations().forEach(station -> station.addRoad(road)));

        // Bind letters to each Station
        Arrays.stream(letters).forEach(letter -> letter.getInitialDest().unload(Set.of(letter)));

        return new RoadMap(stations, roadSet.toArray(Road[]::new), letters, trains);
    }


    public static String getStationName(int number) {
        StringBuilder resultBuilder = new StringBuilder();
        int rem;
        do {
            rem = number % 26;
            resultBuilder.append((char)(rem + 65));
            number = number / 26 - 1;
        } while(number > -1);

        return resultBuilder.reverse().toString();
    }

    private static void shuffleStationList(Station[] stations, Random rand)
    {
        for (int i = 0; i < stations.length; i++)
        {
            int index = rand.nextInt(i + 1);
            Station station = stations[index];
            stations[index] = stations[i];
            stations[i] = station;
        }
    }
}
