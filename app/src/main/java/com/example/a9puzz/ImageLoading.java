package com.example.a9puzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ImageLoading extends AppCompatActivity {
    public ImageView myImage;
    public static ArrayList<Bitmap> parts;
    private     Bitmap currentBitmap = null;
    static int COLUMNS;
    static int DIMENSION =COLUMNS*COLUMNS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_main);
        if(Settings.difficulty==1) {
            COLUMNS = 3;
            DIMENSION = COLUMNS * COLUMNS;
        }
        if(Settings.difficulty==2) {
            COLUMNS = 3;
            DIMENSION = COLUMNS * COLUMNS;
        }

        if(Settings.difficulty==3) {
            COLUMNS = 9;
            DIMENSION = COLUMNS * COLUMNS;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loading);
        int SELECT_IMAGE_CODE=1;
        Button gallery;
        Button app;
        Button backward;
        gallery = findViewById(R.id.gallery);
        app = findViewById(R.id.app);
        backward= findViewById(R.id.backward);
        myImage = findViewById(R.id.srcimage);

        int[] images;
        int[] football = new int[] {R.drawable.football01, R.drawable.football02};
        int[] food = new int[] {R.drawable.food01, R.drawable.food02};
        int[] landscape = new int[] {R.drawable.landscape01,R.drawable.landscape02,R.drawable.landscape03};
        int[] anime = new int[] {R.drawable.image01, R.drawable.image02, R.drawable.image03, R.drawable.image04, R.drawable.image05};
// Get a random between 0 and images.length-1
        List <int[]> allPacks= Arrays.asList(food,anime,football,landscape);
        if(Settings.indexCat!=0)
        {
            images=allPacks.get(Settings.indexCat-1);
        }
        else
        {
            int packId = (int)(Math.random() * Settings.categories.size()-1);
            images=allPacks.get(packId);
        }




        int imageId = (int)(Math.random() * images.length);

// Set the image




        gallery.setOnClickListener(view -> {
            MainMenu.selectsfx.start();
            Handler handler = new Handler();

            String[] projection = new String[]{
                    MediaStore.Images.Media.DATA,
            };

            Uri imagess = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cur = managedQuery(imagess,
                    projection,
                    "",
                    null,
                    ""
            );

            final ArrayList<String> imagesPath = new ArrayList<String>();
            if (cur.moveToFirst()) {

                int dataColumn = cur.getColumnIndex(
                        MediaStore.Images.Media.DATA);
                do {
                    imagesPath.add(cur.getString(dataColumn));
                } while (cur.moveToNext());
            }
            cur.close();
            final Random random = new Random();
            final int count = imagesPath.size();


            int number = random.nextInt(count);
            String path = imagesPath.get(number);
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
            if (currentBitmap != null)
                currentBitmap.recycle();
            currentBitmap = BitmapFactory.decodeFile(path);


            myImage.setImageBitmap(currentBitmap);






            parts = splitImage(myImage,DIMENSION);
            Intent intent = new Intent(this, Starting.class);
            startActivity(intent); });


        app.setOnClickListener(view -> {
            MainMenu.selectsfx.start();
            myImage.setImageResource(images[imageId]);
            Log.d("DIMENSIOnnnN", String.valueOf(DIMENSION));

            parts = splitImage(myImage, DIMENSION);
            Log.d("PARTSS", String.valueOf(parts.size()));
            Intent intent = new Intent(this, Starting.class);
            startActivity(intent);

        });

        backward.setOnClickListener(view -> {
            MainMenu.mp.start();
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        });
//Lottie
        LottieAnimationView lottie;
        RelativeLayout rl;
        rl=findViewById(R.id.lay);
        lottie=findViewById(R.id.lottie2);
        pageChange(lottie,rl);


    }



    private static ArrayList<Bitmap> splitImage(ImageView image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x = 0; x < rows; x++) {
            int xCoord = 0;
            for(int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        /* Now the chunkedImages has all the small image chunks in the form of Bitmap class.
         * You can do what ever you want with this chunkedImages as per your requirement.
         * I pass it to a new Activity to show all small chunks in a grid for demo.
         * You can get the source code of this activity from my Google Drive Account.
         */
        //Start a new activity to show these chunks into a grid
        return chunkedImages;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public  void pageChange(LottieAnimationView l,RelativeLayout rl)
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

            }
        },1000);

    }
}