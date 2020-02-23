package com.bigpay.app.component;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Station;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class FloydWarshallSearchComponent {

    /**
     * Implementation of Floyd Warshall to find shortest path between each 2 Stations
     *
     * @param roadMap
     * @return
     */
    public static Road[][][] generateShortestPath(RoadMap roadMap) {
        int[] stationMatrix = roadMap.getStationMatrix();
        int stationMatrixRawCount = roadMap.getStationMatrixRawCount();

        Station[] stations = roadMap.getStations();
        Set<Road> roads = Set.of(roadMap.getRoads());

        // Initial matrix
        Road [][][]firstRoadMatrix = new Road[stationMatrixRawCount][stationMatrixRawCount][];
        for (int i = 0; i < stationMatrixRawCount; i++) {
            for (int j = i; j < stationMatrixRawCount; j++) {
                if (i == j) {
                    firstRoadMatrix[i][j] = new Road[0];
                } else {
                    Set<Road> sourceStationRoad = stations[i].getRoads();
                    Set<Road> targetStationRoad = stations[j].getRoads();

                    Set<Road> hasRoad = sourceStationRoad.stream().filter(targetStationRoad::contains)
                            .collect(Collectors.toSet());

                    if (!hasRoad.isEmpty()) {
                        firstRoadMatrix[i][j] = new Road[]{hasRoad.iterator().next()};
                        firstRoadMatrix[j][i] = firstRoadMatrix[i][j];
                    }
                }
            }
        }

        // find final solution
        Road [][][]resultRoadMatrix = null;
        for (int k = 0; k < stationMatrixRawCount; k++) {
            resultRoadMatrix = new Road[stationMatrixRawCount][stationMatrixRawCount][];
            for (int i = 0; i < stationMatrixRawCount; i++) {
                for (int j = i; j < stationMatrixRawCount; j++) {
                    if ((i == k) || (k == j)) {
                        resultRoadMatrix[i][j] = firstRoadMatrix[i][j];
                    } else if (i == j) {
                        resultRoadMatrix[i][j] = new Road[0];
                    } else {
                        resultRoadMatrix[i][j] = firstRoadMatrix[i][j];
                        // Convert one number to long because we want long sum for case Integer.MAX_VALUE + Integer.MAX_VALUE
                        if (roadTimeSteps(firstRoadMatrix[i][j]) >
                                (long)roadTimeSteps(firstRoadMatrix[i][k]) + roadTimeSteps(firstRoadMatrix[k][j])) {
                            resultRoadMatrix[i][j] = mergeRoadArrays(firstRoadMatrix[i][k], firstRoadMatrix[k][j]);
                        }
                    }
                    if (resultRoadMatrix[i][j] != null) {
                        resultRoadMatrix[j][i] = resultRoadMatrix[i][j].clone();
                        ArrayUtils.reverse(resultRoadMatrix[i][j]);
                    }
                }
            }
            firstRoadMatrix = resultRoadMatrix;
        }

        for (int i = 0; i < stationMatrixRawCount; i++) {
            for (int j = i + 1; j < stationMatrixRawCount; j++) {
                if (!resultRoadMatrix[i][j][0].getStations().contains(stations[i])) {
                    if (!resultRoadMatrix[i][j][resultRoadMatrix[i][j].length - 1].getStations().contains(stations[j])) {
                        resultRoadMatrix[j][i] = resultRoadMatrix[i][j].clone();
                        ArrayUtils.reverse(resultRoadMatrix[i][j]);
                    }
                }
            }
        }

        return resultRoadMatrix;
    }

    /**
     * Calculate total time steps for list of roads
     *
     * @param roads list of roads
     * @return total time
     */
    private static int roadTimeSteps(Road[] roads) {
        if (roads == null) {
            return Integer.MAX_VALUE;
        }
        return Arrays.stream(roads).map(Road::getTime).mapToInt(Integer::valueOf).sum();
    }

    /**
     *
     *
     * @param roadArray1
     * @param roadArray2
     * @return
     */
    private static Road[] mergeRoadArrays(Road[] roadArray1, Road[] roadArray2) {
        if (roadArray1 == null || roadArray2 == null){
            return null;
        }

        // TODO: optimize this code
        // Merge Roads
        Road[] mergedRoads = new Road[roadArray1.length + roadArray2.length];

        // check is beginning of second array has the same station as end of first array
        Set<Station> firstArrayStation = roadArray1[roadArray1.length - 1].getStations();
        Set<Station> secondArrayStation = roadArray2[0].getStations();
        Set<Station> commonStations = new HashSet<>(firstArrayStation);
        commonStations.addAll(secondArrayStation);
        if (commonStations.size() < firstArrayStation.size() + secondArrayStation.size()) {
            System.arraycopy(roadArray1, 0, mergedRoads, 0, roadArray1.length);
            System.arraycopy(roadArray2, 0, mergedRoads, roadArray1.length, roadArray2.length);
            return mergedRoads;
        }

        // check is beginning of second array has the same station as end of first array
        firstArrayStation = roadArray1[0].getStations();
        secondArrayStation = roadArray2[roadArray2.length - 1].getStations();
        commonStations = new HashSet<>(firstArrayStation);
        commonStations.addAll(secondArrayStation);
        if (commonStations.size() < firstArrayStation.size() + secondArrayStation.size()) {
            System.arraycopy(roadArray2, 0, mergedRoads, 0, roadArray2.length);
            System.arraycopy(roadArray1, 0, mergedRoads, roadArray2.length, roadArray1.length);
            return mergedRoads;
        }

        //Reverse array
        ArrayUtils.reverse(roadArray2);

        // check is beginning of second array has the same station as end of first array
        firstArrayStation = roadArray1[roadArray1.length - 1].getStations();
        secondArrayStation = roadArray2[0].getStations();
        commonStations = new HashSet<>(firstArrayStation);
        commonStations.addAll(secondArrayStation);
        if (commonStations.size() < firstArrayStation.size() + secondArrayStation.size()) {
            System.arraycopy(roadArray1, 0, mergedRoads, 0, roadArray1.length);
            System.arraycopy(roadArray2, 0, mergedRoads, roadArray1.length, roadArray2.length);
            return mergedRoads;
        }

        // check is beginning of second array has the same station as end of first array
        firstArrayStation = roadArray1[0].getStations();
        secondArrayStation = roadArray2[roadArray2.length - 1].getStations();
        commonStations = new HashSet<>(firstArrayStation);
        commonStations.addAll(secondArrayStation);
        if (commonStations.size() < firstArrayStation.size() + secondArrayStation.size()) {
            System.arraycopy(roadArray2, 0, mergedRoads, 0, roadArray2.length);
            System.arraycopy(roadArray1, 0, mergedRoads, roadArray2.length, roadArray1.length);
            return mergedRoads;
        }

        return null;
    }
}
