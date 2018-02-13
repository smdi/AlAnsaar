package al_muntaqimcrescent2018.com.al_ansar;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Fundamentals extends AppCompatActivity {



   private boolean web = true , text = true;
   private ProgressBar progressBar;
   private String url;
   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference dbreference;
   private StorageReference storageReference;
   private FirebaseStorage firebaseStorage;
   private FloatingActionButton fab;
   private  WebSettings webSettings;
   private WebView webView ;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED ,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_fundamentals);

        getFab();
        getSupportActionBar().hide();

        url = getIntent().getExtras().getString("link");

        progressBar =  (ProgressBar) findViewById(R.id.loadprogress);


        webView = (WebView) findViewById(R.id.my_web_view);
        webSettings = webView.getSettings();
        webView.setWebViewClient(new MyBrowser(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });



        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(url);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
       webSettings.getUseWideViewPort();
        webSettings.setLoadWithOverviewMode(true) ;
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.getSaveFormData();
        webSettings.setEnableSmoothTransition(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setBuiltInZoomControls(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }
        else{
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }

        this.registerForContextMenu(webView);
    }

    public void getFab() {

        fab = (FloatingActionButton) findViewById(R.id.fab_delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fireDb="" ;
                String fireStr="";

                SharedPreferences preferences = getSharedPreferences("delfab", Context.MODE_PRIVATE);
                int del  = preferences.getInt("del",0);

                if(del == 0)
                {

                    fireDb = "Al-Ansar-Home";
                    fireStr = "Al-Ansar-homeStorage";

                }
                else  if(del == 1)
                {
                     fireDb = "Up-Comingevents" ;
                     fireStr = "Up-Comingevents-Storage";
                }


                firebaseDatabase = FirebaseDatabase.getInstance();
                dbreference = firebaseDatabase.getReference().child(fireDb);
                firebaseStorage = FirebaseStorage.getInstance();
                storageReference = firebaseStorage.getReference().child(fireStr);


                StorageReference storageReference =firebaseStorage.getReferenceFromUrl(url);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                });

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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId() == R.id.my_web_view){
            this.getMenuInflater().inflate(R.menu.download,menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case  R.id.pull :



                Toast.makeText(getApplicationContext() ,"Pulling link",Toast.LENGTH_SHORT).show();


                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.allowScanningByMediaScanner();

                Toast.makeText(getApplicationContext(),"pulling visiblity",Toast.LENGTH_SHORT).show();

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

                request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS,"/Download/Al-Ansaar/");

                Toast.makeText(getApplicationContext(),"checking viruses",Toast.LENGTH_SHORT).show();

                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                dm.enqueue(request);

                Toast.makeText(getApplicationContext(),"downloading file",Toast.LENGTH_SHORT).show();


                break;
            default:

                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }


}


