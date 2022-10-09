package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
static float volumeValue=8;
static int difficulty=1;
    Button starter;
    Button average;
    Button genius;
    SeekBar sound;
    Button backward;
    Button save;
    TextView volume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



         starter= findViewById(R.id.starter);
        average= findViewById(R.id.average);
         genius= findViewById(R.id.genius);
        backward = findViewById(R.id.button8);
        save = findViewById(R.id.button7);

        sound = findViewById(R.id.sound);
        volume= findViewById(R.id.volume);


        loadSettings();



        backward.setOnClickListener(view -> {
            MainMenu.mp.start();
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);});
        save.setOnClickListener(view -> {
            MainMenu.mp.start();
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "SETTINGS SAVED", Toast.LENGTH_SHORT).show();
            finish();
        });

        starter.setOnClickListener(view -> {
                    MainMenu.selectsfx.start();
                    difficulty = 1;
            diff1();
                });

        average.setOnClickListener(view -> {
            MainMenu.selectsfx.start();

                difficulty=2;
            diff2();


        });


        genius.setOnClickListener(view -> {
            MainMenu.selectsfx.start();

            difficulty=3;
            diff3();

        });
        sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean b)
        {
            volume.setText(String.valueOf(progress*10)+"%");
            volumeValue=progress;


        }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });


    }
    private void diff1(){
        starter.setTextColor(Color.parseColor("#F7AA1C"));
        average.setTextColor(Color.parseColor("#8B348B"));
        genius.setTextColor(Color.parseColor("#8B348B"));
    }

    private void diff2(){
        starter.setTextColor(Color.parseColor("#8B348B"));
        average.setTextColor(Color.parseColor("#F7AA1C"));
        genius.setTextColor(Color.parseColor("#8B348B"));
    }
    private void diff3(){
        starter.setTextColor(Color.parseColor("#8B348B"));
        average.setTextColor(Color.parseColor("#8B348B"));
        genius.setTextColor(Color.parseColor("#F7AA1C"));
    }

    private void loadSettings()
    {
        if(difficulty==1)
            diff1();
        if(difficulty==2)
            diff2();
        if(difficulty==3)
            diff3();

        sound.setProgress((int)(volumeValue/10));
        volume.setText(String.valueOf((int)volumeValue)+"%");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}