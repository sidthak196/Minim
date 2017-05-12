package layout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minin.sidthakur.minim.R;
import com.minin.sidthakur.minim.controller.Song;

import java.util.ArrayList;

/**
 * Created by siddharth_thakur on 5/4/17.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {

    ArrayList<Song> songArrayList ;

   public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public MyViewHolder(View view)
        {
            super(view);
            titleText = (TextView) view.findViewById(R.id.titleTextView);
        }

    }

    public MediaAdapter(ArrayList<Song> songs) {
        songArrayList=songs;
    }

    @Override
    public MediaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_media,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MediaAdapter.MyViewHolder holder, int position) {
        String title = songArrayList.get(position).getmTitle();
        holder.titleText.setText(title);
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public Song getItem(int position) {
        return songArrayList.get(position);
    }
}
