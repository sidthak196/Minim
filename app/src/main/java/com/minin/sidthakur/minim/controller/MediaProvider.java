package com.minin.sidthakur.minim.controller;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by siddharth_thakur on 5/3/17.
 */

public class MediaProvider {

    private static ArrayList<Song> songArrayList;

    public MediaProvider(){
        songArrayList = new ArrayList<Song>();
    }
    public ArrayList<Song> getSongArrayList(Context context) {
        if (songArrayList.isEmpty()) {
            songArrayList = getSongsFromStorage(context);
        }
        return songArrayList;
    }

    private ArrayList<Song> getSongsFromStorage(Context context) {
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = context.getContentResolver().query(allsongsuri, null, selection, null, null);
        ArrayList<Song> songList =new ArrayList<Song>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Song song = new Song();
                    song.setmTitle(cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                    song.setmId(cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID)));

                    song.setPath(cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA)));

                    song.setmAlbum(cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM)));

                    song.setmArtists(cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST)));

                    songList.add(song);
                } while (cursor.moveToNext());

            }
            cursor.close();
    }
        return songList;
    }

    public Song getSong(ArrayList<Song> songArrayList ,int songId)
    {
       for(Song song : songArrayList){
           if(song.getmId() == songId)
               return song;
       }
       return null;
    }
}