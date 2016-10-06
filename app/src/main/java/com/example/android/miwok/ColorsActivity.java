package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    //global MediaPlayer Variable
    private MediaPlayer mMediaPlayer;
    //create OnCompletionListener object that calls the releaseMediaPlayer Method
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mMediaPlayer) {
            releaseMediaPlayer();
        }
    };
    //create AudioManager Object
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //create ArrayList to call word class constructor
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //create ArrayAdapter for the words ArrayList
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
        //sets the Adapter onto ListView
        final ListView listView = (ListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        //set ItemClickListener on ListView
        if (listView !=null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //variable of type word is created using the position in words array provided by adapter via onItemClick method
                    Word word = words.get(position);
                    //release MediaPlayer resources to save memory
                    releaseMediaPlayer();
                    //media player is created getting the correct id from the word object
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    //Listener triggers releaseMediaPlayer Method to release memory
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            });
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    //Memory gets released when activity stops
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
        //abandons AudioFocus
        mAudioManager.abandonAudioFocus(afChangeListener);
    }

    //Method called to request AudioFocus from System
    public boolean requestAudioFocus() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        //result of AudioFocus Request is returned as boolean
        return (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
    }

    //Listener to detect any AudioFocus changes
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            //on temporary AudioFocus Loss the audio will be paused
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mMediaPlayer.pause();
                //on AudioFocus Loss the MediaPlayer gets stopped and MediaPlayer Memory is released
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mMediaPlayer.stop();
                releaseMediaPlayer();
                mAudioManager.abandonAudioFocus(afChangeListener);
                //on temporary AudioFocus Loss the audio will be paused
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                //on AudioFocus gain after loss the MediaPlayer starts again
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                releaseMediaPlayer();
                mMediaPlayer.start();
            }
        }
    };
}
