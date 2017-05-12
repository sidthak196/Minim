package com.minin.sidthakur.minim.controller;

/**
 * Created by siddharth_thakur on 5/3/17.
 */

public class Song {

    public static final String SONG_TITLE = "SongTitle";
    public static final String SONG_ALBUM = "SongAlbum";
    public static final String SONG_PATH = "SongPath";
    public static final String SONG_ID = "SongID";

    String mTitle;
    String mArtists;
    String path;
    String mAlbum;
    int mId;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }



    public String getmArtists() {
        return mArtists;
    }

    public void setmArtists(String mArtists) {
        this.mArtists = mArtists;
    }



    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
