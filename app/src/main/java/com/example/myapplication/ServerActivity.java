package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

public class ServerActivity extends AppCompatActivity implements RequestOperator.RequestOperatorListener {

    Button sendRequestbutton;
    Button sendRequestbutton2;
    Button progressbar;
    TextView title;
    TextView bodyText;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serveractivitydesign);
        indicator = (IndicatingView) findViewById(R.id.generated_graphic);
        sendRequestbutton = (Button) findViewById(R.id.send_request);
        sendRequestbutton.setOnClickListener(requestButtonClicked);
        progressbar = (Button) findViewById(R.id.progressbar);
        progressbar.setOnClickListener(startProgressActivity);
        sendRequestbutton2 = (Button) findViewById(R.id.send_request2);
        sendRequestbutton2.setOnClickListener(startS2Activity);
        title = (TextView) findViewById(R.id.s_title);
        bodyText = (TextView) findViewById(R.id.body_text);
    }

    public void runProgressActivity(boolean b){
        Intent intent = new Intent(context, ProgressActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startProgressActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runProgressActivity(true);
        }
    };
    public void runS2Activity(boolean b){
        Intent intent = new Intent(context, Serveractivity2.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startS2Activity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runS2Activity(true);
        }
    };
    View.OnClickListener requestButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendRequest();

        }
    };

    private void sendRequest(){
        RequestOperator ro = new RequestOperator();
        ro.setListener(this);
        ro.start();
    }

    private ModelPost publication;

    private void updatePublication() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(publication != null){
                    title.setText(publication.getTitle());
                    bodyText.setText(publication.getBodyText());
                } else {
                    title.setText("");
                    bodyText.setText("");
                }
            }
        });
    }

    @Override
    public void success(ModelPost publication) {
        this.publication = publication;
        setIndicatorStatus(IndicatingView.SUCCESS);
        updatePublication();
    }

    @Override
    public void failed(int responseCode) {
        this.publication = null;
        setIndicatorStatus(IndicatingView.FAILED);
        updatePublication();
    }

    private IndicatingView indicator;
    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }
}
