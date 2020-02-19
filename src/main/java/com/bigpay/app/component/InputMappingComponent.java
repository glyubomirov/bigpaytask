package com.bigpay.app.component;

import java.util.Map;

/**
 * Class is used to to prepare input data in more suitable format:
 *  Map stations A,B,C to indexes 0,1,2
 */
public class InputMappingComponent {

    /**
     * Stations is a mapping represents mapping between Node name and index in stationIndex. This will be used only for
     * reading the input.
     */
    private Map<Character, Integer> stationMap;

    /**
     * Stations is a mapping represents mapping between index and Node name. This will be used only for writing the
     * output.
     */
    private char[] stationIndex;
}
