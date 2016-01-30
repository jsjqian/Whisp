package com.example.jack.whisp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
/**
 * Created by georgetong on 1/30/16.
 */
public class Pop extends Activity  {

        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            setContentView(R.layout.popoutwindow);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            getWindow().setLayout((int)(width*.8), (int)(height*.6));
        }
}
