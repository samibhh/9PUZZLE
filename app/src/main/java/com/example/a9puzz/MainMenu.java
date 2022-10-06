package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

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
            Intent intent = new Intent(this, MainActivity.class);
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