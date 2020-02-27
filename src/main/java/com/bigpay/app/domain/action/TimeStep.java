package com.bigpay.app.domain.action;

import com.bigpay.app.domain.Letter;
import com.bigpay.app.domain.Road;
import com.bigpay.app.domain.Train;

import java.util.*;
import java.util.stream.Collectors;

public class TimeStep {

    private List<TrainAction> actionList;
    private Map<Train, List<TrainActionable>> actionMap;

    private boolean isProcessed;

    public TimeStep(Set<Train> trains) {
        this.actionList = new ArrayList<>();
        this.actionMap = new HashMap<>();
        trains.forEach(train -> this.actionMap.put(train, new ArrayList<>(1)));
    }

    public void process() {
        this.actionList.forEach(TrainAction::process);
        this.isProcessed = true;
    }

    public List<TrainAction> getActionList() {
        return Collections.unmodifiableList(this.actionList);
    }

    public Map<Train, List<TrainActionable>> getActionMap() {
        Map<Train, List<TrainActionable>> result = new HashMap<>(this.actionMap.size());
        this.actionMap.forEach((key, value) -> result.put(key, Collections.unmodifiableList(value)));
        return Collections.unmodifiableMap(result);
    }

    public void addAction(TrainAction action) {
        if (this.actionMap.containsKey(action.getTrain())) {
            this.actionMap.get(action.getTrain()).add(action.getAction());
        } else {
            this.actionMap.put(action.getTrain(), new ArrayList<>(List.of(action.getAction())));
        }
        this.actionList.add(action);
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }

    private int getFreeTime(Train train) {
        if (this.actionMap.containsKey(train)) {
            return this.actionMap.get(train).stream().mapToInt(TrainActionable::getExecutableTime).sum();
        } else {
            return 0;
        }
    }

    public Set<Train> getTrainsWithFreeTime() {
        return this.actionMap.keySet().stream().filter(train -> getFreeTime(train) == 0).collect(Collectors.toSet());
    }

    public boolean hasTrainFreeTime(Train train) {
        if (this.actionMap.get(train).size() == 0) {
            return true;
        }
        return this.actionMap.get(train).stream().allMatch(item -> !(item instanceof TrainMoveActionType));
    }

    public TrainActionable getLastTrainAction(Train train) {
        if (actionMap.get(train).isEmpty()) {
            return null;
        }

        return actionMap.get(train).get(actionMap.get(train).size() - 1);
    }

    public Set<Letter> getPreparedForDeliveryLetter(Train train) {
        return this.actionMap.get(train)
                .stream()
                .filter(item -> item instanceof TrainLoadActionType)
                .map(action -> (((TrainLoadActionType)action).getLetter()))
                .collect(Collectors.toSet());
    }

    public int getTrainFreeSpace(Train train){

        return train.getCapacity() - this.actionMap.get(train)
                .stream()
                .filter(item -> item instanceof TrainLoadActionType)
                .mapToInt(item -> ((TrainLoadActionType) item).getLetter().getWeight())
                .sum();
    }
}
