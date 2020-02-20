package com.bigpay.app;

import static org.junit.Assert.assertTrue;

import com.bigpay.app.domain.RoadMap;
import com.bigpay.app.service.RandomRoadMapService;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        RoadMap randomRoadMap = RandomRoadMapService.generator(10, 25, 10, 5, 1);

        System.out.println(randomRoadMap);
    }
}
