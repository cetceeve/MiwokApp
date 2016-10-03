package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fzeih on 03.10.2016.
 * custom Adapter to add Word Objects to ListViews
 */

//this Adapter extends from the ArrayAdapter class
public class WordAdapter extends ArrayAdapter<Word> {

    //stuff i do not know - likely unnecessary
    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    private int mColorResourceId;

    //custom constructor with correct inputs
    public WordAdapter (Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    //overwritten getView method to create the correct view for the ListView
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //find the correct View to be reused
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //initializing variable currentWord of type Word
        Word currentWord = getItem(position);

        //writing text from Word object into correct TextViews
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        if (currentWord !=null) {
            miwokTextView.setText(currentWord.getMiwokTranslation());
        }

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        if (currentWord !=null) {
            defaultTextView.setText(currentWord.getDefaultTranslation());
        }

        //getting image resource ID for the ImageView
        //passed to ListView only when Word.java provides ResourceId
        if (currentWord !=null) {
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.list_image_view);
            if (currentWord.getImageCheck()) {
                imageView.setImageResource(currentWord.getImageResourceId());
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        //set background color
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.list_horizontal_linear_layout);
        linearLayout.setBackgroundResource(mColorResourceId);

        //returning the custom View to be displayed
        return listItemView;
    }
}
