package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private ImageButton buttonMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        buttonMarket = findViewById(R.id.btnHome);

        buttonMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getAnimation(buttonMarket);
                getIntent(MarketActivity.class);
            }
        });
    }

    public void getAnimation(ImageButton btn){
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(1);
        animation.setRepeatMode(1);
        animation.setInterpolator(new LinearInterpolator());
        btn.startAnimation(animation);
    }

    public void getAnimation(ImageView btn){
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(1);
        animation.setRepeatMode(1);
        animation.setInterpolator(new LinearInterpolator());
        btn.startAnimation(animation);
    }

    public void getIntent(Class name){
        Intent intent = new Intent(getApplicationContext(), name);
        startActivity(intent);
    }
}