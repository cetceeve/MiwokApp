package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new Word("father", "epe", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "eta", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        //create ArrayAdapter for the words ArrayList
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        //sets the Adapter onto ListView
        ListView listView = (ListView) findViewById(R.id.list);
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
                    //call method to request AudioFocus, the method returns boolean
                    if (requestAudioFocus()) {
                        //media player is created getting the correct id from the word object
                        mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                        mMediaPlayer.start();
                        //Listener triggers releaseMediaPlayer Method to release memory
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
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

            //abandons AudioFocus
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    //Memory gets released when activity stops
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    //Method called to request AudioFocus from System
    private boolean requestAudioFocus() {
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
