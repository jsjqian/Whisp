package com.example.jack.whisp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Justin on 1/30/2016.
 */
public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView logo = (ImageView) findViewById(R.id.logo);
        final Animation wind2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.opening_animation);
        final Animation fade = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        logo.startAnimation(wind2);
        wind2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(fade);
                finish();
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
