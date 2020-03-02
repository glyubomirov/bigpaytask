package com.bigpay.app.component;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Floyd-Warshall algorithm with one major modification. Result are two matrices:
 * 1. First one: Element is number with  minimal distance between two Stations(Vertices).
 * 2. Second one: Element is list of Roads(Edges) from Station(Vertex) to Station(Vertex)
 *
 * @author ggeorgiev
 *
 */
public class FloydWarshallSearchComponent {

    private static FloydWarshallSearchComponent instance;

    /**
     * Private constructor
     */
    private FloydWarshallSearchComponent() {

    }

    /**
     * Get the single instance of this component
     * @return instance of this component
     */
    public static synchronized FloydWarshallSearchComponent getInstance() {
        if (instance == null) {
            instance = new FloydWarshallSearchComponent();
        }

        return instance;
    }

    /**
     * Implementation of Floyd Warshall to find shortest path between each 2 Stations
     *
     * @param roadMap road map
     * @return 3D road map of (Station, Station, list of Roads between each two stations)
     */
    public Road[][][] generateShortestPath(RoadMap roadMap) {
        int stationCount = roadMap.getStations().length;

        Station[] stations = roadMap.getStations();

        // Initial matrix
        Road [][][]firstRoadMatrix = new Road[stationCount][stationCount][];
        for (int i = 0; i < stationCount; i++) {
            for (int j = i; j < stationCount; j++) {
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

        // Find final solution
        Road [][][]resultRoadMatrix = null;
        for (int k = 0; k < stationCount; k++) {
            resultRoadMatrix = new Road[stationCount][stationCount][];
            for (int i = 0; i < stationCount; i++) {
                for (int j = i; j < stationCount; j++) {
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
                        reverseRoad(resultRoadMatrix[i][j]);
                    }
                }
            }
            firstRoadMatrix = resultRoadMatrix;
        }

        // Check if there is path between each two stations
        for (int i = 0; i < stationCount; i++) {
            for (int j = i + 1; j < stationCount; j++) {
                if (resultRoadMatrix[i][j] == null) {
                    System.err.println(String.format("Path between %s and %s does not exists", stations[i].getName(), stations[j].getName()));
                    System.exit(-1);
                }
            }
        }

        // Reverse path for value under first matrix diagonal
        for (int i = 0; i < stationCount; i++) {
            for (int j = i + 1; j < stationCount; j++) {
                if (!resultRoadMatrix[i][j][0].getStations().contains(stations[i])) {
                    if (!resultRoadMatrix[i][j][resultRoadMatrix[i][j].length - 1].getStations().contains(stations[j])) {
                        resultRoadMatrix[j][i] = resultRoadMatrix[i][j].clone();
                        reverseRoad(resultRoadMatrix[i][j]);
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
        return Arrays.stream(roads).map(Road::getTimeSteps).mapToInt(Integer::valueOf).sum();
    }

    /**
     * Reverse road list
     *
     * @param roads list of roads that has to be reversed
     */
    private static void reverseRoad(Road[] roads) {
        for (int i = 0; i < roads.length / 2; i++) {
            Road tempRoad = roads[i];
            roads[i] = roads[roads.length - i - 1];
            roads[roads.length - i - 1] = tempRoad;
        }
    }

    /**
     * Smart merge of two Connected Road Lists. Result is Merged Road List.
     *
     * @param roadArray1 array of roads
     * @param roadArray2 array of roads
     * @return Merged Road List
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
        reverseRoad(roadArray2);

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
