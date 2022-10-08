package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
static float volumeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SeekBar sound;
        Button backward;
        Button save;
        TextView volume;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);





        backward = findViewById(R.id.button8);
        save = findViewById(R.id.button7);

        sound = findViewById(R.id.sound);
        volume= findViewById(R.id.volume);

        backward.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);});
        save.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "SETTINGS SAVED", Toast.LENGTH_SHORT).show();
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

    public void onDestroy() {
        super.onDestroy();

    }


}