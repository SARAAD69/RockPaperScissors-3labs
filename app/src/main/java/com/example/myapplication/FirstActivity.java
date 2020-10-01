package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private Button myButton;
    private TextView mytextField;
    private Button secondActivityButton;
    private Button pass;
    private Context context = this;
    private ListView mylist;
    private ListAdapter adapter;
    private EditText title;
    private EditText description;
    private Button add;
    List<ListItem> items = new ArrayList<>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivitylayout);

        myButton = (Button) findViewById(R.id.button);
        pass = (Button) findViewById(R.id.pass);
        secondActivityButton = (Button) findViewById(R.id.secondActivitybutton);
        mytextField = (TextView) findViewById(R.id.textfield);
        title = (EditText) findViewById(R.id.title_input);
        description = (EditText) findViewById(R.id.description_input);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(addButton);

        myButton.setOnClickListener(myButtonClick);
        pass.setOnClickListener(startThirdActivity);
        secondActivityButton.setOnClickListener(startSecondActivity);
        secondActivityButton.setOnLongClickListener(startSecondActivityLong);

        mylist = (ListView) findViewById(R.id.listView);

        items.add(new ListItem("Rock", R.drawable.stone, "Beats scissors, loses to paper"));
        items.add(new ListItem("Paper", R.drawable.paper, "Beats rock, loses to scissors"));
        items.add(new ListItem("Scissors", R.drawable.scissors, "Beats paper, loses to rock"));
        adapter = new ListAdapter(this, items);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem listItem = (ListItem) adapter.getItem(i);
                Intent intent = new Intent(context, ForthActivity.class);
                intent.putExtra("title", listItem.getTitle());
                intent.putExtra("imageId", listItem.getImageId());
                intent.putExtra("description", listItem.getDescription());
                context.startActivity(intent);
            }
        });
    }

        View.OnClickListener myButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mytextField.setVisibility(View.GONE);
            }
        };

    public void runSecondActivity(boolean b){
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("flag", b);
        context.startActivity(intent);
    }

    View.OnClickListener startSecondActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSecondActivity(true);
        }
    };

    View.OnLongClickListener startSecondActivityLong = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            runSecondActivity(false);
            return true;
        }
    };
    public void runThirdActivity(boolean b){
        Intent intent = new Intent(context, ThirdActivity.class);
        intent.putExtra("flag", b);

        Collections.sort(items, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));

        Bundle bundle = new Bundle();
        bundle.putSerializable("item", (Serializable) items);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    View.OnClickListener startThirdActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runThirdActivity(true);
        }
    };

    View.OnClickListener addButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            items.add(new ListItem(title.getText().toString(),R.drawable.paper,description.getText().toString()));
            adapter = new ListAdapter(getApplicationContext(), items);
            mylist.setAdapter(adapter);
        }
    };
}