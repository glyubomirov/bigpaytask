package com.bigpay.app.service;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.helper.InputDataMapHelper;
import org.junit.Test;


public class SearchServiceTest {

    @Test
    public void search(){

        InputDataMap inputDataMap = InputDataMapHelper.getExtendedDataMap();

        RoadMap roadMap = RoadMapService.generate(inputDataMap);
//        RoadMap roadMap = RandomRoadMapService.generate(30, 100, 100, 50, 1);

        Road[][][] shortestRoadMap = FloydWarshallSearchComponent.getInstance().generateShortestPath(roadMap);

        SearchService2.getInstance().search(roadMap, shortestRoadMap);


    }
}
