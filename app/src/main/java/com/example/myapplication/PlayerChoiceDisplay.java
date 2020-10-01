package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerChoiceDisplay extends AppCompatActivity {

    static String playerChoiceS3 = "";
    private ImageView mImageView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mImageView = (ImageView) findViewById(R.id.imageViewId);
        TextView choice = (TextView) findViewById(R.id.p1_choice_text);
        playerChoiceS3 = PlayerChoice.player1Choice.toString();
        if (playerChoiceS3.equals("rock") || playerChoiceS3.equals("Rock") || playerChoiceS3.equals("fuck") || playerChoiceS3.equals("ok")|| playerChoiceS3.equals("Ok") || playerChoiceS3.equals("Oak")) {
            choice.setText("Rock");
            mImageView.setImageResource(R.drawable.stone);
        } else if (playerChoiceS3.equals("paper") || playerChoiceS3.equals("Paper") || playerChoiceS3.equals("beeper") || playerChoiceS3.equals("papers")) {
            choice.setText("Paper");
            mImageView.setImageResource(R.drawable.paper);
        } else if (playerChoiceS3.equals("scissors") || playerChoiceS3.equals("Scissors") || playerChoiceS3.equals("ceasers") || playerChoiceS3.equals("Caesars") || playerChoiceS3.equals("seether") || playerChoiceS3.equals("scissor")) {
            choice.setText("Scissors");
            mImageView.setImageResource(R.drawable.scissors);
        }
// ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                if (!MyApplication.isSinglePlayer) {
                    sendChoiceToOtherPlayer(playerChoiceS3);
                }
                Intent i = new Intent(PlayerChoiceDisplay.this, GameResult.class);
                startActivity(i);
            }
        });

    }

    private void sendChoiceToOtherPlayer(String choice) {
        byte[] send = choice.getBytes();
        MyApplication.mChatService.write(send);
    }

    public void goToSecondActivity(View v) {
        Intent i = new Intent(PlayerChoiceDisplay.this, PlayerChoice.class);
        startActivity(i);
    }

    public void goToFourthActivity(View v) {
        if (!MyApplication.isSinglePlayer) {
            sendChoiceToOtherPlayer(playerChoiceS3);
        }
        Intent i = new Intent(PlayerChoiceDisplay.this, GameResult.class);
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
