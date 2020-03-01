package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Train;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to store plan for each train what action it will take
 *
 * @author ggeorgiev
 *
 */
public class TimeStep {

    /**
     * List of actions that have to be performed in sequential order
     */
    private List<TrainActionable> actionList;

    /**
     * Map of list of actions per train
     */
    private Map<Train, List<TrainActionable>> actionMap;

    /**
     * Time step sequential number
     */
    private int id;

    /**
     *
     * Constructor for new time step creation
     *
     * @param id time step sequential number
     * @param trains list of trains that this step contains
     */
    public TimeStep(int id, Set<Train> trains) {
        this.actionList = new ArrayList<>();
        this.actionMap = new HashMap<>();
        this.id = id;
        trains.forEach(train -> this.actionMap.put(train, new ArrayList<>(1)));
    }

    /**
     * Method responsible to process actions from the action list
     */
    public void process() {
        this.actionList.forEach(TrainActionable::process);
    }

    /**
     *
     * @return list wof actions
     */
    public List<TrainActionable> getActionList() {
        return Collections.unmodifiableList(this.actionList);
    }

    /**
     *
     * @return map of list of actions per train
     */
    public Map<Train, List<TrainActionable>> getActionMap() {
        Map<Train, List<TrainActionable>> result = new HashMap<>(this.actionMap.size());
        this.actionMap.forEach((key, value) -> result.put(key, Collections.unmodifiableList(value)));
        return Collections.unmodifiableMap(result);
    }

    /**
     * Add action in action list
     *
     * @param action
     */
    public void addAction(TrainActionable action) {
        if (this.actionMap.containsKey(action.getTrain())) {
            this.actionMap.get(action.getTrain()).add(action);
        } else {
            this.actionMap.put(action.getTrain(), new ArrayList<>(List.of(action)));
        }
        this.actionList.add(action);
    }

    /**
     *
     * Calculate train free space based on planed actions for this step
     *
     * @param train train for which free space has to be calculated
     * @return free space for train
     */
    public int getTrainFreeSpace(Train train){

        return train.getCapacity() - this.actionMap.get(train)
                .stream()
                .filter(item -> item instanceof TrainLoadActionType)
                .mapToInt(item -> ((TrainLoadActionType) item).getLetter().getWeight())
                .sum();
    }

    /**
     * Get letters for a train after step has been executed
     *
     * @param train train
     * @return set of letters
     */
    public Set<Letter> getTrainLetters(Train train) {
        Set<Letter> resultSet = new HashSet(train.getLetters());
        for (TrainActionable action : actionMap.get(train)) {
            if (action.getActionType() == TrainActionType.LOAD) {
                resultSet.add(((TrainLoadActionType)(action)).getLetter());
            }
        }

        return resultSet;
    }

    /**
     * Print action step in readable format
     */
    public void print() {
        System.out.println(String.format("-------------------------- Time Step %d --------------------------", this.id));

        this.getActionMap().entrySet().forEach(item -> item.getValue().stream().forEach(action -> {
                if (action.getActionType() == TrainActionType.LOAD) {

                    System.out.println(String.format("Train %s loads letter %s", item.getKey().getName(),
                            ((TrainLoadActionType)action).getLetter().getName()));

                } else if (action.getActionType() == TrainActionType.UNLOAD) {
                    TrainUnloadActionType unloadAction = ((TrainUnloadActionType)action);

                    if (unloadAction.getLetters().size() > 0) {
                        Set<String> letterList = unloadAction.getLetters().stream().map(Letter::getName).collect(Collectors.toSet());

                        System.out.println(String.format("Train %s unloads %s letters at station %s",
                                item.getKey().getName(), String.join(",", letterList), unloadAction.getStation().getName()));
                    }


                } else if (action.getActionType() == TrainActionType.DEPART) {

                    System.out.println(String.format("Train %s departs from station %s for station %s", item.getKey().getName(),
                            ((TrainDepartActionType)action).getFromStation().getName(),
                            ((TrainDepartActionType)action).getToStation().getName()));

                } else if (action.getActionType() == TrainActionType.ARRIVE) {

                    System.out.println(String.format("Train %s arrives at station %s", item.getKey().getName(),
                            ((TrainArriveActionType)action).getStation().getName()));

                } else if (action.getActionType() == TrainActionType.MOVE) {

                    System.out.println(String.format("Train %s moves to station %s", item.getKey().getName(),
                            ((TrainMoveActionType)action).getToStation().getName()));
                }
            }));
    }
}
