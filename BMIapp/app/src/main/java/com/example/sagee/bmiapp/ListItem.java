package com.example.sagee.bmiapp;

import android.text.SpannableString;

public class ListItem {
    private String mId, mDate;
    private SpannableString mHeight, mWeight, mBMIresult;

    public ListItem(String id, String date, SpannableString weight, SpannableString height, SpannableString BMIresult) {
        this.mDate = date;
        this.mWeight = weight;
        this.mHeight = height;
        this.mId = id;
        this.mBMIresult = BMIresult;
    }

    public String getId() {
        return mId;
    }

    public String getDate() {
        return mDate;
    }

    public SpannableString getWeight() {
        return mWeight;
    }

    public SpannableString getHeight() {
        return mHeight;
    }

    public SpannableString getBMIresult() {
        return mBMIresult;
    }

}