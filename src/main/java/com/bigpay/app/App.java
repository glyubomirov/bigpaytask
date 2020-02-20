package com.bigpay.app;

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

        System.out.println(roadMap);

        // 3. Perform Search solution for the task

        // 4. Transforms data in output format

        // 5. Writes output result
    }
}
