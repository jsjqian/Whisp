package com.example.jack.whisp;

import android.location.Location;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jack on 1/30/2016.
 */
// wrapper so the adapter looks nicer
public class Whisper {

    private long time;
    public String objectId;

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    public Whisper(long t, String id){

        time = t;
        objectId = id;
    }

    @Override
    public String toString(){

        long diff = System.currentTimeMillis() - time;

        String s;
        long l;
        if (diff > 5 * DAY){

            Date d = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            return format.format(d);
        }
        else if (diff > DAY){

            l = DAY;
            s = "day";
        }
        else if (diff > HOUR){

            l = HOUR;
            s = "hour";
        }
        else if (diff > MINUTE){

            l = MINUTE;
            s = "minute";
        }
        else{

            return "Less than a minute ago";
        }

        int i = (int) diff / (int) l;
        return i == 1 ? String.format("1 %s ago", s) : String.format("%s %ss ago", i, s);
    }
}
