package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ListView mylist;
    private ListAdapter adapter;
    private EditText inputSearch;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivitylayout);
        mylist = (ListView) findViewById(R.id.listView);
        inputSearch = (EditText) findViewById(R.id.inputSearch);


        List<ListItem> items = new ArrayList<>();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("flag", true)){

            items.add(new ListItem("Player 5", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 2", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 4", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 3", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 1", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 10", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 6", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 8", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 9", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 7", R.drawable.untitled, "Something" + "Something"));
        }else{
            items.add(new ListItem("Player 5", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 2", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 4", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 3", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player 1", R.drawable.untitled, "Something, Something"));
            items.add(new ListItem("Player ?", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 6", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 8", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 9", R.drawable.untitled, "Something" + "Something"));
            items.add(new ListItem("Player 7", R.drawable.untitled, "Something" + "Something"));
            Collections.sort(items, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
        }
        adapter = new ListAdapter(this, items);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem listItem = (ListItem) adapter.getItem(i);
                items.remove(i);
                adapter.notifyDataSetChanged();
            }
            });

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SecondActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

}
