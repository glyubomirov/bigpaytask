package com.bigpay.app.service;

import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;

public class SearchService {

    private RoadMap roadMap;
    private Road[][][] shortestRoadsMap;

    private static SearchService instance;

    public static synchronized SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }

        return instance;
    }

    public void search(RoadMap roadMap, Road[][][] shortestRoadsMap) {
        this.roadMap = roadMap;
        this.shortestRoadsMap = shortestRoadsMap;
    }
}
