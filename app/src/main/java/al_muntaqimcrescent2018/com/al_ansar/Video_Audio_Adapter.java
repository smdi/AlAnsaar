package al_muntaqimcrescent2018.com.al_ansar;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

/**
 * Created by Imran on 08-02-2018.
 */

public class Video_Audio_Adapter extends RecyclerView.Adapter<Video_Audio_Adapter.ViewHolder> {

    private Context context;
    private List<Video_Audio_Initialiser> listitem;

    public Video_Audio_Adapter(Context context, List<Video_Audio_Initialiser> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Video_Audio_Initialiser video_audio_initialiser = listitem.get(position);

        holder.mediades.setText(""+video_audio_initialiser.getDescription());

        StringBuilder date = getTheTime(""+video_audio_initialiser.getDate());

        holder.datemedia.setText(""+date);

        holder.videoView.setWebViewClient(new MyBrowser(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                holder.progressBar.setVisibility(View.GONE);
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context,AV_display.class);
                intent.putExtra("link",""+video_audio_initialiser.getUri());
                context.startActivity(intent);

            }
        });

        holder.videoView.getSettings().setJavaScriptEnabled(true);
        holder.videoView.setVisibility(View.VISIBLE);
        holder.videoView.loadUrl(video_audio_initialiser.getUri());
        holder.videoView.getSettings().setBuiltInZoomControls(false);
        holder.videoView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        holder.videoView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        holder.videoView.getSettings().setAppCacheEnabled(true);
        holder.videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        holder.videoView.setScrollbarFadingEnabled(true);
        holder.webSettings.setDomStorageEnabled(true);
        holder.webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webSettings.getUseWideViewPort();
        holder.webSettings.setLoadWithOverviewMode(true);
        holder.webSettings.setUseWideViewPort(true);
        holder.webSettings.setSupportZoom(true);
        holder.webSettings.getSaveFormData();
        holder.webSettings.setEnableSmoothTransition(true);
        holder.videoView.getSettings().setJavaScriptEnabled(true);
        holder.videoView.setVisibility(View.VISIBLE);
        holder.videoView.getSettings().setBuiltInZoomControls(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            holder.videoView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }
        else{
            holder.videoView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }


        holder.videoView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {

                Toast.makeText(context ,"Pulling link",Toast.LENGTH_SHORT).show();


                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.allowScanningByMediaScanner();

                Toast.makeText(context,"pulling visiblity",Toast.LENGTH_SHORT).show();

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"Al-Ansaar");

                Toast.makeText(context,"checking viruses",Toast.LENGTH_SHORT).show();

                DownloadManager dm = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

                dm.enqueue(request);

                Toast.makeText(context ,"downloading file",Toast.LENGTH_SHORT).show();


            }
        });
    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
        public ProgressBar progressBar;
        public RelativeLayout relativeLayout ,mainrelay;
        public TextView mediades,datemedia;
        public ImageView imageView;
        public WebSettings webSettings;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.enlarge);
            videoView = (WebView) itemView.findViewById(R.id.CircularImageOntop);
            mediades  = (TextView)  itemView.findViewById(R.id.descriptionmedia);
            datemedia = (TextView)  itemView.findViewById(R.id.datemedia);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.Layout_inCard);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadvideo);
            webSettings = videoView.getSettings();
            mainrelay = (RelativeLayout) itemView.findViewById(R.id.mainrelay);
        }
    }
}
