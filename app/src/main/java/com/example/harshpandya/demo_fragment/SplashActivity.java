package com.example.harshpandya.demo_fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView myimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myimage = (ImageView)findViewById(R.id.photu);

        Handler handler = new Handler();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        myimage.startAnimation(animation);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);

                startActivity(intent);
                finish();
            }
        },5000);
    }
}
