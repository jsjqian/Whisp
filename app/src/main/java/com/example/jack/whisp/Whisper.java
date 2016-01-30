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

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    public Whisper(long t){

        time = t;
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

            s = String.valueOf((int)diff / DAY);
        }
        else if (diff > HOUR){

            s = String.valueOf((int)diff / HOUR);
        }
        else if (diff > MINUTE){

            s = String.valueOf((int)diff / MINUTE);
        }
        else{

            s = "Less than a minute ago";
        }

        return s;
    }
}
