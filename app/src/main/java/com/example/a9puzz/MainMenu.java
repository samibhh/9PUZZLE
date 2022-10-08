package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    static MediaPlayer mp;
    static MediaPlayer selectsfx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int maxVolume = 50;
        mp = MediaPlayer.create(this, R.raw.button);
        selectsfx = MediaPlayer.create(this, R.raw.select);
        mp.setVolume(10,10);
        selectsfx.setVolume(10,10);



        setContentView(R.layout.activity_main_menu);
        Button play;
        Button settings;
        Button exit;
        play = findViewById(R.id.play);
        settings = findViewById(R.id.settings);
        exit= findViewById(R.id.exit);

        play.setOnClickListener(view -> {
            mp.start();
            Intent intent = new Intent(this, ImageLoading.class);
            startActivity(intent);});

        settings.setOnClickListener(view -> {
            mp.start();
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);});

        exit.setOnClickListener(view -> {
            mp.start();
           finish();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}