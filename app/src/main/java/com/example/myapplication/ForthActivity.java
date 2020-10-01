package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ForthActivity extends AppCompatActivity {
    private ListView mylist;
    private ListAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forthactivitylayout);
        mylist = (ListView) findViewById(R.id.listView);
        List<ListItem> items = new ArrayList<>();

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        int imageId = intent.getIntExtra("imageId", 0);
        String description = intent.getExtras().getString("description");
        items.add(new ListItem(title, imageId, description));
        adapter = new ListAdapter2(this, items);
        mylist.setAdapter(adapter);
    }
}
