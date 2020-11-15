package com.example.fnews.app;

import android.app.Application;
import android.content.Context;

/**
 * @author Feng Zhaohao
 * Created on 2019/10/28
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
