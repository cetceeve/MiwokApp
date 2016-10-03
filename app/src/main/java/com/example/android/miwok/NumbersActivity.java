package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //create ArrayList to display the english numbers
        ArrayList<String> words = new ArrayList<>();
        words.add(0, "one");
        words.add(1, "two");
        words.add(2, "three");
        words.add(3, "four");
        words.add(4, "five");
        words.add(5, "six");
        words.add(6, "seven");
        words.add(7, "eight");
        words.add(8, "nine");
        words.add(9, "ten");

        //create ArrayAdapter for the words ArrayList
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,R.layout.list_item, words);
        //sets the Adapter onto the NumbersActivity ListView
        ListView listView = (ListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(itemsAdapter);
        }
    }
}
