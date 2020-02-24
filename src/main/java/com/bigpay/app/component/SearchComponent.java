package com.bigpay.app.component;

public class SearchComponent {

    private static SearchComponent instance;

    public static synchronized SearchComponent getInstance() {
        if (instance == null) {
            instance = new SearchComponent();
        }

        return instance;
    }
}
