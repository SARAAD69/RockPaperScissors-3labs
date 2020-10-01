package com.example.myapplication;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {
    private ProgressBar pgsBar;
    private ProgressBar pgsbar2;
    IndicatingView indicator;
    private int i = 0;
    private TextView txtView;
    private Handler hdlr = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        indicator = (IndicatingView) findViewById(R.id.ind_view);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsbar2 = (ProgressBar) findViewById(R.id.pBar2);
        txtView = (TextView) findViewById(R.id.tView);
        Button btn = (Button)findViewById(R.id.btnShow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = pgsBar.getProgress();
                i = pgsbar2.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            indicator.setState(3);
                            indicator.invalidate();
                            i += 1;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {
                                    pgsBar.setProgress(i);
                                    pgsbar2.setProgress(i);
                                    txtView.setText(i+"/"+pgsBar.getMax());
                                }
                            });
                            if (i==100){
                            indicator.setState(0);
                            indicator.invalidate();
                            }
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });


    }
}
