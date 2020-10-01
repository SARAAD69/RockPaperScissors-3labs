package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private ListView mylist;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdactivitylayout);
        mylist = (ListView) findViewById(R.id.listView);

        List<ListItem> items = new ArrayList<>();
        Intent intent = getIntent();
        items = (List<ListItem>) getIntent().getExtras().getSerializable("item");
        adapter = new ListAdapter(this, items);
        mylist.setAdapter(adapter);

    }
}