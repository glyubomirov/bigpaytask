package com.bigpay.app.component;

import com.bigpay.app.constants.Constants;

public class InputValidatorComponent {

    /**
     * Validate Station input data
     *
     * @param station
     */
    public static void validateStation(String station) {
        if (station.trim().length() != 1) {
            throw new IllegalArgumentException(
                    String.format("Unexpected Station string length! Expected %s, but found %s", 1, station.length()));
        }
    }

    /**
     * Validates road input data
     *
     * @param roadData
     */
    public static void validateRoad(String[] roadData) {

        // Number of arguments validation
        if (roadData.length != Constants.ROAD_NUMBER_OF_ARGUMENTS) {

            throw new IllegalArgumentException(String.format(
                    "Unexpected number of arguments for Road! Expects %d, but found %d number of arguments",
                    Constants.ROAD_NUMBER_OF_ARGUMENTS, roadData.length));
        }

        // 1st argument validation
        validateStation(roadData[0]);

        // 2nd argument validation
        validateStation(roadData[1]);

        if (roadData[0].equals(roadData[1])) {
            throw new IllegalArgumentException(
                    "Stations are the same for a Road! Expects source Station different then target Station");
        }

        // 3rd argument validation
        try {
            Integer.parseInt(roadData[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(String.format("Unexpected Road time steps value! Expects Integer number, but found %s", roadData[2]));
        }
    }

    /**
     * Validates road input data
     *
     * @param letterData
     */
    public static void validateLetter(String[] letterData) {

        // Number of arguments validation
        if (letterData.length != Constants.LETTER_NUMBER_OF_ARGUMENTS) {

            throw new IllegalArgumentException(String.format(
                    "Unexpected number of arguments for Road! Expects %d, but found %d number of arguments",
                    Constants.LETTER_NUMBER_OF_ARGUMENTS, letterData.length));
        }

        // 1st argument validation
        if (letterData[0].isEmpty()) {
            throw new IllegalArgumentException(
                    "Empty Letter name! Expects non empty Letter name");
        }

        // 2nd argument validation
        validateStation(letterData[1]);

        // 3rd argument validation
        validateStation(letterData[2]);

        if (letterData[1].equals(letterData[2])) {
            throw new IllegalArgumentException(
                    "Source and target Station for Letter are the same! Expects source Station different then target Station");
        }


        // 4th argument validation
        try {
            Integer.parseInt(letterData[3]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(String.format("Unexpected Letter weight value! Expects Integer number, but found %s", letterData[2]));
        }
    }

    /**
     * Validates train input data
     *
     * @param trainData
     */
    public static void validateTrain(String[] trainData) {

        // Number of arguments validation
        if (trainData.length != Constants.TRAIN_NUMBER_OF_ARGUMENTS) {

            throw new IllegalArgumentException(String.format(
                    "Unexpected number of arguments for Train! Expects %d, but found %d number of arguments",
                    Constants.TRAIN_NUMBER_OF_ARGUMENTS, trainData.length));
        }

        // 1st argument validation
        if (trainData[0].isEmpty()) {
            throw new IllegalArgumentException(
                    "Empty Train name! Expects non empty Train name");
        }

        // 2nd argument validation
        validateStation(trainData[1]);

        // 3rd argument validation
        try {
            Integer.parseInt(trainData[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(String.format("Unexpected Train capacity value! Expects Integer number, but found %s", trainData[2]));
        }
    }
}
