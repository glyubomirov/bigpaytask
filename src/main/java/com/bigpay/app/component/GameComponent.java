package com.bigpay.app.component;

import com.bigpay.app.domain.Letter;

import java.util.Set;

public class GameComponent {
    private static GameComponent instance;

    private GameComponent() {

    }

    public static synchronized GameComponent getInstance() {
        if (instance == null) {
            instance = new GameComponent();
        }

        return instance;
    }

    /**
     * Calculates if all letters are delivered
     *
     * @param letters set of letters
     * @return true if all letters are delivered
     */
    public boolean finished(Set<Letter> letters) {
        return letters.stream().allMatch(Letter::isArrived);
    }
}
