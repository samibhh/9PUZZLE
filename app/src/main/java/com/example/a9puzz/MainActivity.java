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
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kotlin.random.Random;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.util.Log;
import android.os.CountDownTimer;
import android.util.Log;
public class MainActivity extends AppCompatActivity {

    private static RelativeLayout mTimeWinLose;
    private static final long START_TIME_IN_MILLIS = 66000;
    private TextView mTextViewCountDown;
    private static TextView mTextViewWinLose;
    private static Button mButtonAgain;
    private CountDownTimer mCountDownTimer;
    private static TextView mTextViewMoves;
    private static int countTries = 0;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis =  START_TIME_IN_MILLIS;

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
        setContentView(R.layout.activity_main);
        mTextViewMoves = findViewById(R.id.text_moves);
        mTimeWinLose =  findViewById(R.id.winlose);
        mTextViewWinLose = findViewById(R.id.textwon);
        mButtonAgain = findViewById(R.id.buttonAgain);
        mTextViewCountDown = findViewById(R.id.text_countdown);
        startTimer();
        music = MediaPlayer.create(getApplicationContext(), R.raw.musicbg);




        init();
        scramble();
        setDimensions();


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




//ArrayList<Bitmap> parts = splitImage(myImage, 9);



       for (int i = 0; i < tileList.length; i++) {
           button = new Button(context);

           if (tileList[i].equals("0"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(0)));
           else if (tileList[i].equals("1"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(1)));
           else if (tileList[i].equals("2"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(2)));
           else if (tileList[i].equals("3"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(3)));
           else if (tileList[i].equals("4"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(4)));
           else if (tileList[i].equals("5"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(5)));
           else if (tileList[i].equals("6"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(6)));
           else if (tileList[i].equals("7"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(7)));
           else if (tileList[i].equals("8"))
               button.setBackground(new BitmapDrawable(context.getResources(), ImageLoading.parts.get(8)));

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
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);
        countTries++;
        mTextViewMoves.setText(countTries+" Counts");


       if (isSolved()){
           mTextViewWinLose.setText("YOU WIN !!");
           //  mTextViewWinLose.setTextColor(getResources().getColor(com.google.android.material.R.color.material_dynamic_primary0));
           mTimeWinLose.setVisibility(View.VISIBLE);
           Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
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


    public void onDestroy() {
        super.onDestroy();
        if(music.isPlaying())
            music.stop();
    }

    }