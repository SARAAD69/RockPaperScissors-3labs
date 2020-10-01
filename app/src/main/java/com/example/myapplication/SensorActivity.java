package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private Context context = this;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private LocationManager locationManager;
    private Button startAndStop;
    private Button startAndStop2;
    private Button location;
    private Button compass;
    private TextView xValue;
    private TextView yValue;
    private TextView zValue;
    private TextView pitch;
    private TextView roll;
    private TextView azimuth;
    private boolean InformationObtained;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private  SensorManager rsensorManager;
    private Sensor rotationSensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        InformationObtained = false;
        startAndStop = (Button) findViewById(R.id.start_and_stop);
        startAndStop.setOnClickListener(StartAndStopButtonListener);
        startAndStop2 = (Button) findViewById(R.id.start_and_stop2);
        startAndStop2.setOnClickListener(StartAndStopButtonListener2);
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(LocationListener);
        compass = (Button) findViewById(R.id.compass);
        compass.setOnClickListener(StartCompass);
        xValue = (TextView) findViewById(R.id.x_value);
        yValue = (TextView) findViewById(R.id.y_value);
        zValue = (TextView) findViewById(R.id.z_value);
        pitch = (TextView) findViewById(R.id.pitch);
        roll = (TextView) findViewById(R.id.roll);
        azimuth = (TextView) findViewById(R.id.azimuth);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        rsensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = rsensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void runLocationListener(boolean b){
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener LocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runLocationListener(true);
        }
    };
    public void runCompass(boolean b){
        Intent intent = new Intent(context, CompassActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener StartCompass = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runCompass(true);
        }
    };

    View.OnClickListener StartAndStopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(senAccelerometer == null){
                Toast.makeText(SensorActivity.this, getString(R.string.no_sensor), Toast.LENGTH_LONG).show();
                return;
            }
            if(InformationObtained){
                startAndStop.setText(getString(R.string.start));
                senSensorManager.unregisterListener(SensorActivity.this, senAccelerometer);
                InformationObtained = false;
            } else {
                senSensorManager.registerListener(SensorActivity.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                startAndStop.setText(getString(R.string.stop));
                InformationObtained = true;
            }
        }

    };
    View.OnClickListener StartAndStopButtonListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(rotationSensor == null){
                Toast.makeText(SensorActivity.this, getString(R.string.no_sensor), Toast.LENGTH_LONG).show();
                return;
            }
            if(InformationObtained){
                startAndStop2.setText(getString(R.string.start));
                rsensorManager.unregisterListener(SensorActivity.this, rotationSensor);
                InformationObtained = false;
            } else {
                rsensorManager.registerListener(SensorActivity.this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
                startAndStop.setText("Stop");
                InformationObtained = true;
            }
        }

    };
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if(mAccel > 0.25) {
                // do something
                xValue.setText(String.valueOf(event.values[0]));
                yValue.setText(String.valueOf(event.values[1]));
                zValue.setText(String.valueOf(event.values[2]));
            }
        }
        if(event.sensor.getType() ==  Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotMatrix = new float[9];
            float[] rotVals = new float[3];

            SensorManager.getRotationMatrixFromVector(rotMatrix, event.values);
            SensorManager.remapCoordinateSystem(rotMatrix,
                    SensorManager.AXIS_X, SensorManager.AXIS_Y, rotMatrix);

            SensorManager.getOrientation(rotMatrix, rotVals);
            float azimuthf = (float) Math.toDegrees(rotVals[0]);
            float pitchf = (float) Math.toDegrees(rotVals[1]);
            float rollf = (float) Math.toDegrees(rotVals[2]);
            azimuth.setText(String.valueOf(azimuthf));
            pitch.setText(String.valueOf(pitchf));
            roll.setText(String.valueOf(rollf));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(senAccelerometer != null)
            senSensorManager.unregisterListener(SensorActivity.this, senAccelerometer);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        //this.locationManager.removeUpdates(this);
        if(rotationSensor != null)
        rsensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(senAccelerometer != null && InformationObtained)
            senSensorManager.registerListener(SensorActivity.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
       // this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        if(rotationSensor != null && InformationObtained) {
            rsensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

   @Override
    public void onLocationChanged(Location location) {
      //  if(location != null){
     //       coordinates.setText(getString(R.string.Latitude_text)+ " " + location.getLatitude() +" " + getString(R.string.Longitude_text) + location.getLongitude());
     //   }
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
