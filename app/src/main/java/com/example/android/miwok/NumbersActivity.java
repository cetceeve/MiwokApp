package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Create ArrayList to display the english numbers
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

        //display all words in the words ArrayList in NumbersActivity
        for (int index = 0; index < words.size(); index++) {
            //find the NumbersActivity Layout and store it in a variable
            LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
            //create a new TextView
            TextView wordView = new TextView(this);
            //set a Text to be displayed in the TextView
            wordView.setText(words.get(index));
            //add the TextView to Layout if the Layout 'exists'
            if (rootView != null) {
                rootView.addView(wordView);
            }
        }
    }
}
