package com.example.jack.whisp;

import android.location.Location;

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
    private Location location;

    private static final long MINUTE = 1000 * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    public Whisper(long t, File f, String d, Location l){

        time = t;
        file = f;
        description = d;
        location = l;
    }

    @Override
    public String toString(){

        long diff = System.currentTimeMillis() - time;

        String s;
        if (diff > 5 * DAY){

            Date d = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            s = format.format(d);
        }
        else if (diff > DAY){


        }

        return null;
    }
}
