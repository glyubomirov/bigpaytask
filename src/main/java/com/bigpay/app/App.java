package com.bigpay.app;

import com.bigpay.app.component.TimeStepComponent;
import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.domain.action.TimeStep;
import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.domain.strategy.AbstractSearchStrategy;
import com.bigpay.app.domain.strategy.SearchStrategyFactory;
import com.bigpay.app.domain.strategy.StrategyType;
import com.bigpay.app.service.RoadMapService;
import com.bigpay.app.service.InputDataService;

/**
 * Application entry point
 *
 * @author ggeorgiev
 *
 */
public class App 
{
    /**
     * App entry method
     *
     * @param args not used
     */
    public static void main( String[] args )
    {
        // 1. Reads input data
        InputDataMap inputDataMap = InputDataService.readFromStdInput();

        // 2. Generate road map from input data
        RoadMap roadMap = RoadMapService.generate(inputDataMap);

        // 3. Use Greedy Strategy
        AbstractSearchStrategy strategy = SearchStrategyFactory.getInstance().getStrategy(StrategyType.GREEDY);

        // 4. Get TimeStepComponent instance
        TimeStepComponent timeStepComponent = TimeStepComponent.getInstance();

        // 5. Temp variable for each step
        TimeStep timeStep;

        // 6. Loops until there are no more letters for delivery
        while(!TimeStepComponent.isFinish(roadMap)) {

            // 6.1. Get next time step
            timeStep = strategy.getNextTimeStep(roadMap);

            // 6.2 Processes time step
            timeStepComponent.process(timeStep);

            // 6.3 Prints the results to standard output
            timeStep.print();
        }

        System.out.println("-------------------------- Done --------------------------");
    }
}
