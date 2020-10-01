package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Serveractivity2 extends AppCompatActivity {

    // json array response url
    private String urlJsonArry = "http://jsonplaceholder.typicode.com/posts";
    private TextView count;

    private static String TAG = Serveractivity2.class.getSimpleName();
    private Button btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveractivity2);

        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        txtResponse = (TextView) findViewById(R.id.txtResponse);
        count = (TextView) findViewById(R.id.count);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);



        btnMakeArrayRequest.setOnClickListener(MakeRequest);



    }
    View.OnClickListener MakeRequest = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            makeJsonArrayRequest();

        }
    };

    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                response -> {
                    Log.d(TAG, response.toString());

                    try {
                        // Parsing json array response
                        // loop through each json object
                        jsonResponse = "";
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject lead = (JSONObject) response
                                    .get(i);
                            String userId = lead.getString("userId");
                            String id = lead.getString("id");
                            String title = lead.getString("title");
                            String body = lead.getString("body");

                            jsonResponse += "userId: " + userId + "\n\n";
                            jsonResponse += "id: " + id + "\n\n";
                            jsonResponse += "title: " + title + "\n\n";
                            jsonResponse += "body: " + body + "\n\n\n";

                        }
                        int number = response.length();
                        count.setText(String.valueOf(number));
                        txtResponse.setText(jsonResponse);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                    hidepDialog();
                }, error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    hidepDialog();
                });

        AppController.getInstance(this).addToRequestQueue(req);

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

