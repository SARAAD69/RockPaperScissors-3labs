package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class SOS extends AppCompatActivity {
    private Camera camera;
    private boolean isFlashOn;
    Camera.Parameters params;
    private Button sos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_s);
        sos = (Button)findViewById(R.id.sos);
        getCamera();
        sos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                turnOnFlash();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        turnOffFlash();
                        turnOnFlash();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                turnOffFlash();
                                turnOnFlash();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        turnOffFlash();
                                        turnOnFlash();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                turnOffFlash();
                                                turnOnFlash();
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        turnOffFlash();
                                                        turnOnFlash();
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                turnOffFlash();
                                                                turnOnFlash();
                                                                Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    public void run() {
                                                                        turnOffFlash();
                                                                        turnOnFlash();
                                                                        Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            public void run() {
                                                                                turnOffFlash();
                                                                                turnOnFlash();
                                                                                Handler handler = new Handler();
                                                                                handler.postDelayed(new Runnable() {
                                                                                    public void run() {
                                                                                        turnOffFlash();

                                                                                    }
                                                                                }, 3000);
                                                                            }
                                                                        }, 3000);
                                                                    }
                                                                }, 3000);
                                                            }
                                                        }, 6000);
                                                    }
                                                }, 6000);
                                            }
                                        }, 6000);
                                    }
                                }, 3000);

                            }
                        }, 3000);
                    }
                }, 3000);
            }
        });
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error: ", e.getMessage());
            }
        }
    }
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

        }

    }


    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }


            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }
    }
}