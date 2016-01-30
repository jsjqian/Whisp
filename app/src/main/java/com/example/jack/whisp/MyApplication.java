package com.example.jack.whisp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Jack on 1/30/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate(){

        super.onCreate();
        Parse.initialize(this);

    }
}
