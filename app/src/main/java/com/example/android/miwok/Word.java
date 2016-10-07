package com.example.android.miwok;

/**
 * Created by fzeih on 03.10.2016.
 * represents vocabulary word with default and miwok translation
 */

public class Word {

    //Default translation for the word
    private String mDefaultTranslation;

    //Miwok translation for the word
    private String mMiwokTranslation;

    //imageResourceID is an Integer
    private int mImageResourceId;

    //audioResourceID is an Integer
    private int mAudioResourceId;

    //variable for if/else Statement in Adapter
    private boolean mImageCheck;

    //constructor for all Activities
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
        mImageCheck = true;
    }

    //constructor for PhrasesFragment
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
        mImageCheck = false;
    }

    //get ImageCheck
    public boolean getImageCheck() {return mImageCheck; }

    //get default translation
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //get miwok translation
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    //get imageResourceID
    public int getImageResourceId() {
        return mImageResourceId;
    }

    //get audioResourceID
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
