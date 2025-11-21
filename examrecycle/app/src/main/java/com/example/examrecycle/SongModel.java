package com.example.examrecycle;


import java.io.Serializable;

public class SongModel implements Serializable{
    private String mCode;
    private String mTitle;
    private String mArtist;
    private String mLyric;

    public SongModel(String mCode, String mTitle, String mLyric, String mArtist) {
        this.mCode = mCode;
        this.mTitle = mTitle;
        this.mLyric = mLyric;
        this.mArtist = mArtist;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmLyric() {
        return mLyric;
    }

    public void setmLyric(String mLyric) {
        this.mLyric = mLyric;
    }
}
