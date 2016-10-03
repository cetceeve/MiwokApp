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

    //constructor for word class
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    //get default translation
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //get miwok translation
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
}
