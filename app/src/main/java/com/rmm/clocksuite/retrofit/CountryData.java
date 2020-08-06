package com.rmm.clocksuite.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryData {

    @SerializedName("name")      private String       mName;
    @SerializedName("region")    private String       mRegion;
    @SerializedName("capital")   private String       mCapital;
    @SerializedName("timezones") private List<String> mTimezones;
    @SerializedName("flag")      private String       mFlagImgLink;

    @NonNull
    @Override
    public String toString() {
        return String.format("(%s) %s (%s): %s, Flag link: %s", mCapital, mName, mRegion, mTimezones.toString(), mFlagImgLink);
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String mRegion) {
        this.mRegion = mRegion;
    }

    public List<String> getTimezones() {
        return mTimezones;
    }

    public void setTimezones(List<String> mTimezones) {
        this.mTimezones = mTimezones;
    }

    public String getFlagImgLink() {
        return mFlagImgLink;
    }

    public void setFlagImgLink(String mFlagImgLink) {
        this.mFlagImgLink = mFlagImgLink;
    }

    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String mCapital) {
        this.mCapital = mCapital;
    }
}
