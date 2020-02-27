package com.bigpay.app.component;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Station;
import com.bigpay.app.domain.input.*;
import com.bigpay.app.helper.InputDataMapHelper;
import com.bigpay.app.service.RoadMapService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FloydWarshallSearchComponentTest {

    @Test
    public void generateShortestPathTest() {
        InputDataMap inputDataMap = InputDataMapHelper.getSimpleDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        Road[][][] actualResult = roadMap.getShortestRoadMap();

//        for (int i = 0; i < actualResult.length; i++) {
//            for (int j = 0; j < actualResult[i].length; j++) {
//                if (actualResult[i][j] != null) {
//                    int pathLength = Arrays.stream(actualResult[i][j]).map(Road::getTime).mapToInt(Integer::valueOf).sum();
//                    System.out.print(String.format("%-5d", pathLength));
//                } else {
//                    System.out.print("null ");
//                }
//            }
//            System.out.println();
//        }

        int[][] expectedResult = new int[][]{
                { 0,  3,  8,  4,  8,  7,  6,  4, 14, 14, 13, 10,  5},
                { 3,  0,  5,  4,  5,  4,  3,  1, 11, 11, 10,  7,  2},
                { 8,  5,  0,  9, 10,  9,  8,  6,  6,  6, 10,  2,  3},
                { 4,  4,  9,  0,  9,  5,  7,  5, 15, 15, 14, 11,  6},
                { 8,  5, 10,  9,  0,  7,  2,  4,  9,  9,  5, 12,  7},
                { 7,  4,  9,  5,  7,  0,  5,  3, 15, 15, 12, 11,  6},
                { 6,  3,  8,  7,  2,  5,  0,  2, 11, 11,  7, 10,  5},
                { 4,  1,  6,  5,  4,  3,  2,  0, 12, 12,  9,  8,  3},
                {14, 11,  6, 15,  9, 15, 11, 12,  0,  6,  4,  4,  9},
                {14, 11,  6, 15,  9, 15, 11, 12,  6,  0,  4,  4,  9},
                {13, 10, 10, 14,  5, 12,  7,  9,  4,  4,  0,  8, 12},
                {10,  7,  2, 11, 12, 11, 10,  8,  4,  4,  8,  0,  5},
                { 5,  2,  3,  6,  7,  6,  5,  3,  9,  9, 12,  5,  0}
        };

        Station[] stations = roadMap.getStations();

        for (int i = 0; i < actualResult.length; i++) {
            for (int j = 0; j < actualResult[i].length; j++) {
                Assert.assertEquals(expectedResult[i][j],
                        Arrays.stream(actualResult[i][j]).map(Road::getTime).mapToInt(Integer::valueOf).sum());

                // Check if all roads are in correct order
                for (int k = 1; k < actualResult[i][j].length; k++) {
                    actualResult[i][j][k-1].getStations();
                    Set<Station> firstArrayStation = actualResult[i][j][k-1].getStations();
                    Set<Station> secondArrayStation = actualResult[i][j][k].getStations();
                    Set<Station> commonStations = new HashSet<>(firstArrayStation);
                    commonStations.addAll(secondArrayStation);

                    Assert.assertEquals(commonStations.size() + 1, firstArrayStation.size() + secondArrayStation.size());
                }

                // Assert that first Road is connected to source Station and final Road to final station
                if (i != j) {
                    Station sourceStation = stations[i];
                    Station targetStation = stations[j];

//                    System.out.println(String.format("%d, %d", i, j));
                    Assert.assertTrue(actualResult[i][j][0].getStations().contains(sourceStation));
                    Assert.assertTrue(actualResult[i][j][actualResult[i][j].length - 1].getStations().contains(targetStation));
                } else {
                    Assert.assertEquals(actualResult[i][j].length, 0);
                }
            }
        }
    }
}
