package com.bigpay.app;

import com.bigpay.app.component.FloydWarshallSearchComponent;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.service.RoadMapService;
import com.bigpay.app.service.InputDataService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // 1. Reads input data
        InputDataMap inputDataMap = InputDataService.readFromStdInput();

        // 2. Generate road map from input data
        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        // 3. Calculate shortest path between each two stations

        // 4. Perform Search solution for the task

        // 5. Transforms data in output format

        // 6. Writes output result
    }
}
