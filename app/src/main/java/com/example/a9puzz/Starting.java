package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class Starting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);
        VideoView startingcounter = (VideoView) findViewById(R.id.textView123);
        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.count;

        Uri uri = Uri.parse(uriPath);
        startingcounter .setVideoURI(uri);
        startingcounter .requestFocus();
        startingcounter .start();


        startingcounter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                startingcounter.setEnabled(false);
                goToMainActivity();
                //write your code after complete video play
            }
        });


    }
    void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}