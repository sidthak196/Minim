package com.minin.sidthakur.minim.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Created by siddharth_thakur on 5/5/17.
 */

public class MediaPlayerInstance {

    public MediaPlayer mediaPlayer ;

    private static MediaPlayerInstance mediaPlayerInstance ;

    private MediaPlayerInstance() {
        mediaPlayer = new MediaPlayer();
    }

    public static MediaPlayerInstance getMediaPlayerInstance(Context context)
    {
        if(mediaPlayerInstance == null) {
            mediaPlayerInstance = new MediaPlayerInstance();
        }
        return mediaPlayerInstance;
    }

    public void prepareMedia(Context context , String path ) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context , Uri.fromFile(new File(path)));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
