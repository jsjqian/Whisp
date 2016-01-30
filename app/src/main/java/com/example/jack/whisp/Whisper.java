package com.example.jack.whisp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jack on 1/30/2016.
 */
// model used by adapter in main listview
public class Whisper {

    private long time;
    private File file;
    private String description;

    private static final long MINUTE = 1000 * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    public Whisper(long t, File f){

        time = t;
        file = f;
    }

    /*
    @Override
    public String toString(){

        long diff = System.currentTimeMillis() - time;

        String ago;
        if (diff > 7 * DAY){

            Date d = new Date(time);

        }

        return null;
    }*/
}
