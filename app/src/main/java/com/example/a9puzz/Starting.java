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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

public class Starting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);

        //lottie
        LottieAnimationView lottie;

        lottie=findViewById(R.id.lottie);

        //end lottie

        VideoView startingcounter = (VideoView) findViewById(R.id.textView123);
        pageChange(lottie,startingcounter);
        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.count;

        Uri uri = Uri.parse(uriPath);
        startingcounter .setVideoURI(uri);
        startingcounter .requestFocus();



        startingcounter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                startingcounter.setEnabled(false);

                goToMainActivity();
                finish();
                //write your code after complete video play
            }
        });


    }
    void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();


    }
    public  void pageChange(LottieAnimationView l, VideoView rl)
    {
        rl.setAlpha(0);
        l.setScale(4);

        l.bringToFront();
        l.loop(false);


        l.playAnimation();
        l.animate().scaleX(4).setDuration(0);
        l.animate().scaleY(4).setDuration(0);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                l.animate().alpha(0).setDuration(200);
                rl.animate().alpha(1).setDuration(200);

            }
        },1400);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {

                rl.animate().alpha(1).setDuration(200);
                rl.start();

            }
        },1000);

    }
}