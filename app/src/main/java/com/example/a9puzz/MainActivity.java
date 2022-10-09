package com.example.a9puzz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.content.Context;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a9puzz.databinding.ActivityMainBinding;


import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import kotlin.random.Random;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.util.Log;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static boolean activeGrid = true;
    private static RelativeLayout mPauseLock;
    private static RelativeLayout mTimeWinLose;
    private static RatingBar mRatingBar;
    private static boolean mTimerRunning;
    private static long mTimeLeftInMillis;




    private static  long START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private static TextView mTextViewWinLose;
    private static Button mButtonAgain;
    private static CountDownTimer mCountDownTimer;


    private static TextView mTextViewMoves;
    private static int countTries = 0;








    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";
MediaPlayer music;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    static int COLUMNS=3;
    static int DIMENSION =COLUMNS*COLUMNS;
    private static int mColumnWidth,mColumnHeight;
    private static GestureDetectGridView mGridView;
    private static String[] tileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(),Settings.difficulty, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        mPauseLock = findViewById(R.id.pauselock);
        mRatingBar = findViewById(R.id.ratingBar);
        mTextViewMoves = findViewById(R.id.text_moves);
        mTimeWinLose =  findViewById(R.id.winlose);
        mTextViewWinLose = findViewById(R.id.textwon);
        mButtonAgain = findViewById(R.id.buttonAgain);
        mTextViewCountDown = findViewById(R.id.text_countdown);



        if(Settings.difficulty==1) {
            COLUMNS = 3;
            DIMENSION = COLUMNS * COLUMNS;
            START_TIME_IN_MILLIS=90000;
        }
        if(Settings.difficulty==2) {
            COLUMNS = 3;
            DIMENSION = COLUMNS * COLUMNS;
            START_TIME_IN_MILLIS=45000;
        }

        if(Settings.difficulty==3) {
            COLUMNS = 9;
            DIMENSION = COLUMNS * COLUMNS;
            START_TIME_IN_MILLIS=300000;

        }
        mTimeLeftInMillis =  START_TIME_IN_MILLIS;


        startTimer();

        music = MediaPlayer.create(getApplicationContext(), R.raw.musicbg);


        init();
        scramble();
        setDimensions();
        mButtonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeWinLose.setVisibility(View.INVISIBLE);
                resetTimerMoves();
                activeGrid = true;
                scoreCalcul(false);
                //mGridView.setBackground(Color.parseColor("#99000000"));
                mPauseLock.setVisibility(View.INVISIBLE);
                startTimer();
            }
        });

    }

    private void init(){

        mGridView=(GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);
        tileList = new String[DIMENSION];

        for(int i=0;i<tileList.length;i++)
        {

            tileList[i]=String.valueOf(i);
        }
       music = MediaPlayer.create(MainActivity.this, R.raw.musicbg);
        music.setVolume(Settings.volumeValue,Settings.volumeValue);
        music.start();

    }

    private void scramble()
    {

        int index;
        String temp;

        for(int i=tileList.length -1;i>0;i--)
        {
            index= Random.Default.nextInt(i+1);
            temp=tileList[index];
            tileList[index]=tileList[i];
            tileList[i]=temp;
        }

    }

   private static void display(Context context){
        Button button;
        ArrayList<Button> buttons= new ArrayList<>();
       int testboucle;



//ArrayList<Bitmap> parts = splitImage(myImage, 9);



       for (int i = 0; i < tileList.length; i++) {
           button = new Button(context);


           Log.d("TESTING LENGTH", String.valueOf(ImageLoading.parts.size()));
            testboucle=0;
           for(int j=0;j < tileList.length ; j++) {

               if (tileList[i].equals(Integer.toString(j))&& testboucle==0) {
                   button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(j)));
                   testboucle=1;

               }
           }

           buttons.add(button);
       }
        mGridView.setAdapter(new CustomAdapter(buttons,mColumnWidth,mColumnHeight));
    }
    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        //ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;
                display(getApplicationContext());
            }
        });





     }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    private static void swap(Context context, int currentPosition, int swap) {
        if(activeGrid){
            String newPosition = tileList[currentPosition + swap];
            tileList[currentPosition + swap] = tileList[currentPosition];
            tileList[currentPosition] = newPosition;
            display(context);
            countTries++;
            mTextViewMoves.setText(countTries+" MOVES");
            if (isSolved()){
                mTextViewWinLose.setText("YOU WIN !!");
                mTextViewWinLose.setTextColor(Color.parseColor("#ffa408"));
                activeGrid = false;
                //mGridView.setBackgroundColor(Color.parseColor("#70000000"));
                scoreCalcul(true);
                mPauseLock.setVisibility(View.VISIBLE);
                mTimeWinLose.setVisibility(View.VISIBLE);
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                pauseTimer();
            }
        }

    }


    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSION - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSION - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSION - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSION - 1 && position > DIMENSION - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }




    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis /1000 )/ 60;
        int seconds = (int) (mTimeLeftInMillis /1000 ) % 60;
        Log.d("MyApp","seconds are : "+seconds);
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = 0;
                updateCountDownText();
                mTimerRunning = false;
                mTextViewWinLose.setText("YOU LOSE !!");
                mTextViewWinLose.setTextColor(Color.parseColor("#ff3d08"));
                activeGrid = false;
                scoreCalcul(true);
                mPauseLock.setVisibility(View.VISIBLE);
                //mGridView.setBackgroundColor(Color.parseColor("#70000000"));
              //  mTextViewWinLose.setTextColor(getResources().getColor(com.google.android.material.R.color.material_dynamic_primary0));
                mTimeWinLose.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
    }

    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }


    private static void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }
    private void resetTimerMoves(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        countTries = 0;
        mTextViewMoves.setText(countTries+" MOVES");
        updateCountDownText();
    }
    private static void scoreCalcul(boolean check){
        if(check){
            int minutes = (int) (mTimeLeftInMillis /1000 )/ 60;
            int seconds = (int) (mTimeLeftInMillis /1000 ) % 60;
            int time = (minutes*60) + seconds;
            int score = 0;
            float stars;
            switch(Settings.difficulty){
                case 1:
                    score = (90-time) + (countTries*2);
                    if(score < 50) stars = 5;
                    else if(score < 70) stars = (float) 3.3;
                    else stars = (float) 1.6;
                    break;
                case 2:
                    score = (45-time) + (countTries*2);
                    if(score < 30) stars = (float) 5;
                    else if(score < 50) stars = (float) 3.3;
                    else stars = (float) 1.6;
                    break;
                case 3:
                    score = (300-time) + (countTries*2);
                    if(score < 140) stars = 5;
                    else if(score < 200) stars = (float) 3.3;
                    else stars = (float) 1.6;
                    break;
                default:
                    stars = 0;
            }
            Log.d("Score","Time is : "+time+" score is : "+score+" stars : "+stars);
            mRatingBar.setRating(stars);
        }else mRatingBar.setRating(0);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(music.isPlaying())
            music.stop();
    }

    }