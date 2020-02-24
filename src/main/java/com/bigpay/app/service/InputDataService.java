package com.bigpay.app.service;

import com.bigpay.app.domain.input.InputDataMap;
import com.bigpay.app.component.InputValidatorComponent;
import com.bigpay.app.constants.Constants;
import com.bigpay.app.domain.input.LetterInputData;
import com.bigpay.app.domain.input.RoadInputData;
import com.bigpay.app.domain.input.StationInputData;
import com.bigpay.app.domain.input.TrainInputData;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Contains all input data as java object
 */
public class InputDataService {

    /**
     * Reads input data from default input
     *
     * @return input data in object format
     */
    public static InputDataMap readFromStdInput() {

        StationInputData[] stationList;
        RoadInputData[] roadList;
        LetterInputData[] letterList;
        TrainInputData[] trainList;

        try(Scanner scanner = new Scanner(System.in)) {

            // Reads Stations data
            int stationCount = readIntegerLine(scanner);
            stationList = new StationInputData[stationCount];
            for (int i = 0; i < stationCount; i++) {
                stationList[i] = readStation(scanner);
            }

            // Reads Roads data
            int roadCount = readIntegerLine(scanner);
            roadList = new RoadInputData[roadCount];
            for (int i = 0; i < roadCount; i++) {
                roadList[i] = readRoad(scanner);
            }

            // Reads Letters data
            int letterCount = readIntegerLine(scanner);
            letterList = new LetterInputData[letterCount];
            for (int i = 0; i < letterCount; i++) {
                letterList[i] = readLetter(scanner);
            }

            // Reads Trains data
            int trainCount = readIntegerLine(scanner);
            trainList = new TrainInputData[trainCount];
            for (int i = 0; i < trainCount; i++) {
                trainList[i] = readTrain(scanner);
            }

        }

        return new InputDataMap(stationList, roadList, letterList, trainList);
    }

    /**
     * Read input and skips line that are empty or starts with // (comment)
     *
     * @param scanner
     * @return read line from input
     */
    private static String readLine(Scanner scanner) {

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine().trim();

            if (!line.isEmpty() && !line.startsWith("//")) {
                return line;
            }
        }

        return null;
    }

    /**
     * read only if we expect next line to be integer value
     *
     * @return integer value
     */
    private static int readIntegerLine(Scanner scanner) {

        String value = readLine(scanner);

        if (value != null) {
            return Integer.parseInt(value);
        } else {
            throw new NumberFormatException("Non Integer value is not expected");
        }
    }

    /**
     *
     * Reads and coverts line from string(with delimiter) to array e.g. delimiter comma(,) A,B,C,D
     *
     * @param scanner
     * @param delimiter
     * @return
     */
    private static String[] readArrayLine(Scanner scanner, String delimiter) {

        String value = readLine(scanner);

        if (value != null) {
            return Arrays.stream(value.split(delimiter)).map(String::trim).toArray(String[]::new); // trims whitespaces from array items
        } else {
            throw new NullPointerException("Unexpected end of input data");
        }
    }

    /**
     * Reads Station line data from the input
     *
     * @param scanner
     * @return
     */
    private static StationInputData readStation(Scanner scanner) {

        String stationName = readLine(scanner);

        InputValidatorComponent.validateStation(stationName);

        return new StationInputData(stationName);
    }

    /**
     * Reads Road line data from the input
     *
     * @param scanner
     * @return
     */
    private static RoadInputData readRoad(Scanner scanner) {

        String[] roadData = readArrayLine(scanner, Constants.ROAD_INPUT_DATA_DELIMITER);

        InputValidatorComponent.validateRoad(roadData);

        return new RoadInputData(roadData[0], roadData[1], Integer.parseInt(roadData[2]));
    }

    /**
     * Reads Letter line data from the input
     *
     * @param scanner
     * @return
     */
    private static LetterInputData readLetter(Scanner scanner) {

        String[] roadData = readArrayLine(scanner, Constants.LETTER_INPUT_DATA_DELIMITER);

        InputValidatorComponent.validateLetter(roadData);

        return new LetterInputData(roadData[0], roadData[1], roadData[2], Integer.parseInt(roadData[3]));
    }
    /**
     * Reads Train line data from the input
     *
     * @param scanner
     * @return
     */
    private static TrainInputData readTrain(Scanner scanner) {

        String[] trainData = readArrayLine(scanner, Constants.TRAIN_INPUT_DATA_DELIMITER);

        InputValidatorComponent.validateTrain(trainData);

        return new TrainInputData(trainData[0], trainData[1], Integer.parseInt(trainData[2]));
    }
}
