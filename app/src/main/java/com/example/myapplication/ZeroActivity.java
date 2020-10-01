package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ZeroActivity extends AppCompatActivity {
    private Button myButton;
    private Button button2;
    private Button button3;
    private Button pr;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zeroactivitylayout);
        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(startFirstActivity);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(startServerActivity);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(startSensorActivity);
        pr = (Button) findViewById(R.id.pr);
        pr.setOnClickListener(startLogIn);

    }
    public void runFirstActivity(boolean b){
        Intent intent = new Intent(context, FirstActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startFirstActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runFirstActivity(true);
        }
    };

    public void runServerActivity(boolean b){
        Intent intent = new Intent(context, ServerActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startServerActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runServerActivity(true);
        }
    };

    public void runSensorActivity(boolean b){
        Intent intent = new Intent(context, SensorActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startSensorActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSensorActivity(true);
        }
    };

    public void runLogIn(boolean b){
        Intent intent = new Intent(context, LogIn.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startLogIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runLogIn(true);
        }
    };
}
