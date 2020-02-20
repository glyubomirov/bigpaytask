package com.bigpay.app.service;

import com.bigpay.app.domain.*;
import com.bigpay.app.domain.input.*;

import java.util.*;

/**
 * This class is used for testing purposes. It generates random RoadMap based on some basic criteria
 */
public class RandomRoadMapService {

    public static RoadMap generator(int stationCount, int roadCount, int letterCount, int trainCount, int seed) {

        Random rand = new Random(seed);

        Station[] stationList = new Station[stationCount];
        for (int i = 0; i < stationCount; i++) {
            stationList[i] = new Station((char)(i + 65));
        }

        // create fully connected graph with random weights
        Road[] stationMatrix = new Road[stationCount * stationCount];
        Set<Road> roadSet = new HashSet<>();
        for (int i = 0; i < stationCount; i++) {
            for (int j = i + 1; j < stationCount; j++) {
                int nextInt = rand.nextInt(300) + 1;
                Road road = new Road(nextInt, stationList[i], stationList[j]);
                stationMatrix[i * stationCount + j] = road;
                stationMatrix[i + j * stationCount] = road;
                roadSet.add(road);
            }
        }

        while(roadSet.size() > roadCount) {
            roadSet.remove(stationMatrix[rand.nextInt(stationCount * stationCount)]);
        }

        Letter[] letterList = new Letter[letterCount];
        for (int i = 0; i < letterCount; i++) {
            shuffleStationList(stationList, rand);
            letterList[i] = new Letter(String.format("D%d", i + 1), stationList[0], stationList[1], rand.nextInt(20));
        }

        Train[] trainList = new Train[trainCount];
        for (int i = 0; i < trainCount; i++) {
            shuffleStationList(stationList, rand);
            trainList[i] = new Train(String.format("T%d", i + 1), stationList[0], rand.nextInt(80) + 20);
        }

        return new RoadMap(stationList, roadSet.toArray(Road[]::new), letterList, trainList);
    }

    static void shuffleStationList(Station[] stations, Random rand)
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
