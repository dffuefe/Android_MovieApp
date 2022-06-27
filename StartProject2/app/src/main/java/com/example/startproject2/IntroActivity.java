package com.example.startproject2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    private Handler handler;
    private Handler handler2;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();

        handler.postDelayed(runnable, 2000);

        final ImageView imageView1 = (ImageView)findViewById(R.id.imageView2);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn);
        imageView1.startAnimation(animation1);

        final TextView textView = (TextView)findViewById(R.id.textView5);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        textView.startAnimation(animation2);
    }

    public void init() {
        handler = new Handler();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

}
