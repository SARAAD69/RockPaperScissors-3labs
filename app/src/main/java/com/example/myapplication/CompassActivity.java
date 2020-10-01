package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class CompassActivity extends AppCompatActivity implements Compass.CompassListener {

    // Tag
    private static final String TAG = CompassActivity.class.getSimpleName();

    // Constants
    // The minimum difference in degrees with the last orientation values for the CompassListener to be notified
    private static final float MIN_AZIMUTH_DIFFERENCE_BETWEEN_COMPASS_UPDATES = 1;
    private static final float MIN_PITCH_DIFFERENCE_BETWEEN_COMPASS_UPDATES = 1;
    private static final float MIN_ROLL_DIFFERENCE_BETWEEN_COMPASS_UPDATES = 1;

    // Compass
    private Compass mCompass;

    // Views
    private CompassView mCompassView;
    private TextView mPitchTextView;
    private TextView mAzimuthTextView;
    private TextView mRollTextView;
    private TextView mScreenRotationTextView;
    private Camera camera;
    Camera.Parameters params;
    private boolean isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        Log.d(TAG, "Debug: " + BuildConfig.DEBUG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        // Views
        mCompassView = (CompassView) findViewById(R.id.compass_view);
        mAzimuthTextView = (TextView) findViewById(R.id.azimuth_text_view);
        mPitchTextView = (TextView) findViewById(R.id.roll_text_view);
        mRollTextView = (TextView) findViewById(R.id.pitch_text_view);
        mScreenRotationTextView = (TextView) findViewById(R.id.screen_rotation_text_view);

        // Compass
        mCompass = Compass.newInstance(this, this);
    }


        @Override
    protected void onResume() {
        super.onResume();
        if (mCompass != null) mCompass.start(MIN_AZIMUTH_DIFFERENCE_BETWEEN_COMPASS_UPDATES, MIN_PITCH_DIFFERENCE_BETWEEN_COMPASS_UPDATES, MIN_ROLL_DIFFERENCE_BETWEEN_COMPASS_UPDATES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCompass != null) mCompass.stop();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onOrientationChanged(float azimuth, float pitch, float roll) {

        mCompassView.updateAzimuth(azimuth);
        mAzimuthTextView.setText(String.format(getString(R.string.azimuth), azimuth));
        mPitchTextView.setText(String.format(getString(R.string.pitch), pitch));
        mRollTextView.setText(String.format(getString(R.string.roll), roll));
        if (mAzimuthTextView.getText().toString().contains("Azimuth: 360 °") || mAzimuthTextView.getText().toString().contains("Azimuth: 359 °") || mAzimuthTextView.getText().toString().contains("Azimuth: 358 °") || mAzimuthTextView.getText().toString().contains("Azimuth: 1 °") || mAzimuthTextView.getText().toString().contains("Azimuth: 2 °")){
            Intent i = new Intent(CompassActivity.this, MyCameraActivity.class);
            startActivity(i);}
        if (mPitchTextView.getText().toString().contains("Pitch: -2 °") || mPitchTextView.getText().toString().contains("Pitch: -1 °") || mPitchTextView.getText().toString().contains("Pitch: 0 °") || mPitchTextView.getText().toString().contains("Pitch: 1 °") || mPitchTextView.getText().toString().contains("Pitch: 2 °")){
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = 0;
            getWindow().setAttributes(lp);
        }
        if (mPitchTextView.getText().toString().contains("Pitch: -88 °") || mPitchTextView.getText().toString().contains("Pitch: -89 °") || mPitchTextView.getText().toString().contains("Pitch: -90 °") || mPitchTextView.getText().toString().contains("Pitch: -91 °") || mPitchTextView.getText().toString().contains("Pitch: -92 °")){
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = 1;
            getWindow().setAttributes(lp);
        }
        if (mRollTextView.getText().toString().contains("Roll: -45 °") || mRollTextView.getText().toString().contains("Roll: 45 °")|| mRollTextView.getText().toString().contains("Roll: 46 °")|| mRollTextView.getText().toString().contains("Roll: 47 °")|| mRollTextView.getText().toString().contains("Roll: 48 °")){
            Intent i = new Intent(CompassActivity.this, SOS.class);
            startActivity(i);
        }
        if ((mRollTextView.getText().toString().contains("Roll: -1 °") || mRollTextView.getText().toString().contains("Roll: 1 °") || mRollTextView.getText().toString().contains("Roll: 2 °")) && (mPitchTextView.getText().toString().contains("Pitch: -179 °") || mPitchTextView.getText().toString().contains("Pitch: -178 °") || mPitchTextView.getText().toString().contains("Pitch: -180 °"))){
            finishAffinity();
            System.exit(0);
        }
        final int screenRotation = (((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getRotation();
        if (screenRotation == Surface.ROTATION_90) {
            mScreenRotationTextView.setText(String.format(getString(R.string.screen_rotation_value), 90));
        } else if (screenRotation == Surface.ROTATION_180) {
            mScreenRotationTextView.setText(String.format(getString(R.string.screen_rotation_value), 180));
        } else if (screenRotation == Surface.ROTATION_270) {
            mScreenRotationTextView.setText(String.format(getString(R.string.screen_rotation_value), 270));
        } else {
            mScreenRotationTextView.setText(String.format(getString(R.string.screen_rotation_value), 0));
        }
    }

}

