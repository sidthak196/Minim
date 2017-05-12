package layout;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.minin.sidthakur.minim.R;
import com.minin.sidthakur.minim.controller.MediaPlayerInstance;
import com.minin.sidthakur.minim.controller.MediaProvider;
import com.minin.sidthakur.minim.controller.Song;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment {

    private static final String TAG="PLAYER_FRAGMENT";
    //private static final String ARG_PARAM2 = "param2";

    private Song mSong;
    private ArrayList<Song> songArrayList;
    private TextView mTitleView;
    private Switch mSwitch;
    private SeekBar mSeekBar;
    private ImageButton mShuffle ,mPrevious ,mPlay ,mNext ,mRepeat;
    private  BufferedReader brSubtitle;

    private OnFragmentInteractionListener mListener;
    private MediaPlayer mediaPlayer;

    public PlayerFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerFragment.
     */
    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songArrayList = new MediaProvider().getSongArrayList(getContext());
        if (getArguments() != null) {
           int songId = getArguments().getInt(Song.SONG_ID);
           mSong = new MediaProvider().getSong(songArrayList , songId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mTitleView = (TextView) view.findViewById(R.id.titleTextView);
        mSwitch = (Switch) view.findViewById(R.id.lyricsToggleBtn);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mShuffle = (ImageButton) view.findViewById(R.id.btn_shuffle);
        mPrevious = (ImageButton) view.findViewById(R.id.btn_previous);
        mPlay = (ImageButton) view.findViewById(R.id.btn_play);
        mNext = (ImageButton) view.findViewById(R.id.btn_next);
        mRepeat = (ImageButton) view.findViewById(R.id.btn_repeat);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onPlayerFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onPlayerFragmentInteraction(Bundle bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mSong!=null) {
            mediaPlayer =  MediaPlayerInstance.getMediaPlayerInstance(getContext()).mediaPlayer;
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    initViews();
                    updateUI();
                }
            });
            MediaPlayerInstance.getMediaPlayerInstance(getContext()).prepareMedia(getContext() ,  mSong.getPath());
            try {
                initSRTFile("Dummy");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d(TAG , "mSong is null");
        }
    }

    private void initViews()
    {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });

        mSeekBar.setMax(mediaPlayer.getDuration());
        Log.d(TAG , ""+ mediaPlayer.getDuration());
        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer !=null) {
                    // Log.d(TAG,"In run()");
                    mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    updateSubtitles();
                    handler.postDelayed(this,1000);
                }

            }
        };
        Log.d(TAG,"before runonuithread)");
        handler.postDelayed(r,1000);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);

                }
                Log.d(TAG , "" + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    private void initSRTFile(String path) throws FileNotFoundException {
        brSubtitle = new BufferedReader(new InputStreamReader(new FileInputStream("/storage/emulated/faded.srt")));
    }

    private void updateSubtitles(String time) {
        try {
                         if(brSubtitle != null) {
                             String line = brSubtitle.readLine();
                             if(!line.contains("[")) {
                                 line = brSubtitle.readLine();
                             }
                         }

        }catch (FileNotFoundException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI()
    {
        mTitleView.setText(mSong.getmTitle());
        //TODO : update ui for album art and lyrics.

    }
}
