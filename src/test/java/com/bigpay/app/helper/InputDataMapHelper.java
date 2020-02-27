package com.bigpay.app.helper;

import com.bigpay.app.domain.input.*;

public class InputDataMapHelper {
    public static InputDataMap getSimpleDataMap() {
        return new InputDataMap(
                new StationInputData[]{
                        new StationInputData("A"),
                        new StationInputData("B"),
                        new StationInputData("C"),
                        new StationInputData("D"),
                        new StationInputData("E"),
                        new StationInputData("F"),
                        new StationInputData("G"),
                        new StationInputData("H"),
                        new StationInputData("I"),
                        new StationInputData("J"),
                        new StationInputData("K"),
                        new StationInputData("L"),
                        new StationInputData("S")},
                new RoadInputData[]{
                        new RoadInputData("A", "B", 3),
                        new RoadInputData("A", "D", 4),
                        new RoadInputData("A", "S", 7),
                        new RoadInputData("B", "D", 4),
                        new RoadInputData("B", "S", 2),
                        new RoadInputData("B", "H", 1),
                        new RoadInputData("C", "S", 3),
                        new RoadInputData("C", "L", 2),
                        new RoadInputData("D", "F", 5),
                        new RoadInputData("E", "G", 2),
                        new RoadInputData("E", "K", 5),
                        new RoadInputData("F", "H", 3),
                        new RoadInputData("G", "H", 2),
                        new RoadInputData("I", "J", 6),
                        new RoadInputData("I", "K", 4),
                        new RoadInputData("I", "L", 4),
                        new RoadInputData("J", "K", 4),
                        new RoadInputData("J", "L", 4)
                },
                new LetterInputData[]{
                        new LetterInputData("D1", "S", "E", 5)
                },
                new TrainInputData[]{
                        new TrainInputData("T1", "S", 5)
                }
        );
    }

    public static InputDataMap getSimpleDataMap2() {
        return new InputDataMap(
                new StationInputData[]{
                        new StationInputData("A"),
                        new StationInputData("B"),
                        new StationInputData("C"),
                        new StationInputData("D"),
                        new StationInputData("E"),
                        new StationInputData("F"),
                        new StationInputData("G"),
                        new StationInputData("H"),
                        new StationInputData("I"),
                        new StationInputData("J"),
                        new StationInputData("K"),
                        new StationInputData("L"),
                        new StationInputData("S")},
                new RoadInputData[]{
                        new RoadInputData("A", "B", 3),
                        new RoadInputData("A", "D", 4),
                        new RoadInputData("A", "S", 7),
                        new RoadInputData("B", "D", 4),
                        new RoadInputData("B", "S", 2),
                        new RoadInputData("B", "H", 1),
                        new RoadInputData("C", "S", 3),
                        new RoadInputData("C", "L", 2),
                        new RoadInputData("D", "F", 5),
                        new RoadInputData("E", "G", 2),
                        new RoadInputData("E", "K", 5),
                        new RoadInputData("F", "H", 3),
                        new RoadInputData("G", "H", 2),
                        new RoadInputData("I", "J", 6),
                        new RoadInputData("I", "K", 4),
                        new RoadInputData("I", "L", 4),
                        new RoadInputData("J", "K", 4),
                        new RoadInputData("J", "L", 4)
                },
                new LetterInputData[]{
                        new LetterInputData("D1", "S", "E", 5),
                        new LetterInputData("D2", "S", "K", 2),
                        new LetterInputData("D3", "S", "F", 6),
                        new LetterInputData("D4", "A", "G", 4),
                        new LetterInputData("D5", "A", "J", 1),
                        new LetterInputData("D6", "B", "L", 2),
                        new LetterInputData("D7", "D", "I", 3),
                        new LetterInputData("D8", "H", "I", 5),
                        new LetterInputData("D9", "F", "C", 1),
                        new LetterInputData("D10", "F", "C", 2),
                        new LetterInputData("D11", "C", "E", 1)
                },
                new TrainInputData[]{
                        new TrainInputData("T1", "S", 12)
                }
        );
    }

    public static InputDataMap getExtendedDataMap() {
        return new InputDataMap(
                new StationInputData[]{
                        new StationInputData("A"),
                        new StationInputData("B"),
                        new StationInputData("C"),
                        new StationInputData("D"),
                        new StationInputData("E"),
                        new StationInputData("F"),
                        new StationInputData("G"),
                        new StationInputData("H"),
                        new StationInputData("I"),
                        new StationInputData("J"),
                        new StationInputData("K"),
                        new StationInputData("L"),
                        new StationInputData("S")},
                new RoadInputData[]{
                        new RoadInputData("A", "B", 3),
                        new RoadInputData("A", "D", 4),
                        new RoadInputData("A", "S", 7),
                        new RoadInputData("B", "D", 4),
                        new RoadInputData("B", "S", 2),
                        new RoadInputData("B", "H", 1),
                        new RoadInputData("C", "S", 3),
                        new RoadInputData("C", "L", 2),
                        new RoadInputData("D", "F", 5),
                        new RoadInputData("E", "G", 2),
                        new RoadInputData("E", "K", 5),
                        new RoadInputData("F", "H", 3),
                        new RoadInputData("G", "H", 2),
                        new RoadInputData("I", "J", 6),
                        new RoadInputData("I", "K", 4),
                        new RoadInputData("I", "L", 4),
                        new RoadInputData("J", "K", 4),
                        new RoadInputData("J", "L", 4)
                },
                new LetterInputData[]{
                        new LetterInputData("D1", "S", "E", 5),
                        new LetterInputData("D2", "S", "K", 2),
                        new LetterInputData("D3", "S", "F", 6),
                        new LetterInputData("D4", "A", "G", 4),
                        new LetterInputData("D5", "A", "J", 1),
                        new LetterInputData("D6", "B", "L", 2),
                        new LetterInputData("D7", "D", "I", 3),
                        new LetterInputData("D8", "H", "I", 5),
                        new LetterInputData("D9", "F", "C", 1),
                        new LetterInputData("D10", "F", "C", 2),
                        new LetterInputData("D11", "C", "E", 1)
                },
                new TrainInputData[]{
                        new TrainInputData("T1", "S", 12),
                        new TrainInputData("T2", "I", 6),
                        new TrainInputData("T3", "J", 8),
                        new TrainInputData("T4", "L", 5)
                }
        );
    }
}
