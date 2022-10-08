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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Button play;
        Button settings;
        Button exit;
        play = findViewById(R.id.play);
        settings = findViewById(R.id.settings);
        exit= findViewById(R.id.exit);

        play.setOnClickListener(view -> {
            Intent intent = new Intent(this, ImageLoading.class);
            startActivity(intent);});

        settings.setOnClickListener(view -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);});

        exit.setOnClickListener(view -> {
           finish();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}