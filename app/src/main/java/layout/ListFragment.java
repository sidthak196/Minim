package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minin.sidthakur.minim.R;
import com.minin.sidthakur.minim.controller.ClickListener;
import com.minin.sidthakur.minim.controller.MediaProvider;
import com.minin.sidthakur.minim.controller.Song;

import java.util.ArrayList;

/**
 * Created by siddharth_thakur on 5/4/17.
 */

public class ListFragment extends Fragment {
    private ArrayList<Song> songList ;
    private RecyclerView recyclerView;
    private MediaAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    private static final String TAG = "ListFragment ";
    private Bundle mArguments;
    public ListFragment() {
        //required empty constructor
    }

    public ListFragment getInstance(Bundle bundle)
    {
        ListFragment listFragment = new ListFragment();
        mArguments = bundle;
        return listFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view , container , false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        songList = new MediaProvider().getSongArrayList(getContext());
        Log.d("Debug",""+songList.size());
        mAdapter = new MediaAdapter(songList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new MediaItemClickListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG , "Inside onClick ");
                Bundle bundle = new Bundle();
                Song currentSong = mAdapter.getItem(position);
                bundle.putString(Song.SONG_PATH , currentSong.getPath());
                bundle.putInt(Song.SONG_ID , currentSong.getmId());
                communicateEvent(bundle);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Bundle bundle);
    }

    private void communicateEvent(Bundle bundle) {
        if(mListener != null){
            mListener.onListFragmentInteraction(bundle);
        }
    }
}
