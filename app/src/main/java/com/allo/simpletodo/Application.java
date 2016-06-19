package com.allo.simpletodo;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ALLO on 19/6/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
