package al_muntaqimcrescent2018.com.al_ansar;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Imran on 09-02-2018.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private Context context;
    private List<Video_Audio_Initialiser> listitem;

    public AudioAdapter(Context context, List<Video_Audio_Initialiser> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_list,parent,false);
        return new AudioAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final AudioAdapter.ViewHolder holder, int position) {

        final Video_Audio_Initialiser video_audio_initialiser = listitem.get(position);


        holder.videoView.loadUrl(video_audio_initialiser.getUri());

//        holder.videoView.setVideoURI(Uri.parse(video_audio_initialiser.getUri()));


//
//        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//
//                Toast.makeText(context,"video view started looping",Toast.LENGTH_SHORT).show();
//                holder.videoView.start();
//                mediaPlayer.setLooping(true);
//            }
//        });



        holder.videoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context,Fundamentals.class);
                intent.putExtra("link",""+video_audio_initialiser.getUri());
                context.startActivity(intent);
                return true;
            }
        });

        holder.mediades.setText(""+video_audio_initialiser.getDescription());

        StringBuilder date = getTheTime(""+video_audio_initialiser.getDate());

        holder.datemedia.setText(""+date);
    }

    public StringBuilder getTheTime(String time) {


        StringBuilder stringBuilder = new StringBuilder();
        String split[]  =  time.split("\\s");
        for(int i=0; i<3; i++)
        {

            stringBuilder.append("  "+split[i]);
        }

        stringBuilder.append(" "+split[5]);


        return stringBuilder;
    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public WebView videoView;
        public TextView mediades,datemedia;
        public ViewHolder(View itemView) {
            super(itemView);

            videoView = (WebView) itemView.findViewById(R.id.CircularImageOntop);
            mediades  = (TextView)  itemView.findViewById(R.id.descriptionmedia);
            datemedia = (TextView)  itemView.findViewById(R.id.datemedia);

        }
    }
}
