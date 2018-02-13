package al_muntaqimcrescent2018.com.al_ansar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.Calendar;

public class VideoCreator extends AppCompatActivity {

    private ImageView videoView;
    private EditText editText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbreference;
    private static final int RC_PHOTO_PICKER = 1;
    private static final int RESULT_OK = -1;
    private Uri photouri;
    int media;
    private ProgressDialog progressDialog;
    private String fireDb ,fireStr;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_creator);

        SharedPreferences preferences = getSharedPreferences("chooser", MODE_PRIVATE);
         media = preferences.getInt("media",0);

        Toast.makeText(getApplicationContext(),""+media,Toast.LENGTH_SHORT).show();

        initialise();
        initialiseclick(media);
        firbaseinitialise();

    }

    private void initialise() {
        videoView = (ImageView) findViewById(R.id.videoChooser);
        editText  = (EditText)  findViewById(R.id.videodescription);
    }

    private void AndroidSystempusher() {

        final String descriptiontext = " "+editText.getText();

        final String datei = getSystemDate();

        StorageReference mref = storageReference.child(photouri.getLastPathSegment());


        mref.putFile(photouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri uri = taskSnapshot.getDownloadUrl();

                Video_Audio_Initialiser video_audio_initialiser = new Video_Audio_Initialiser("" + uri, descriptiontext, datei);
                dbreference.push().setValue(video_audio_initialiser);

                Toast.makeText(getApplicationContext(), "pushed", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

            }
        });

    }


      private void firbaseinitialise() {


        if(media == 0) {
             fireDb = corrector("video-downloads");
             fireStr = corrector("video-downloads-storage");
        }
        else if(media == 1){

             fireDb = corrector("audio-downloads");
             fireStr = corrector("audio-downloads-storage");
        }
        else if(media == 2)
        {
            fireDb = corrector("monthly-video-downloads");
            fireStr = corrector("monthly-video-downloads-storage");
        }
        else if(media == 3) {
            fireDb = corrector("monthly-audio-downloads");
            fireStr = corrector("monthly-audio-downloads-storage");
        }
          firebaseDatabase = FirebaseDatabase.getInstance();
          dbreference = firebaseDatabase.getReference().child(fireDb);
          firebaseStorage = FirebaseStorage.getInstance();
          storageReference = firebaseStorage.getReference().child(fireStr);

    }


    private String corrector(String master) {

        String fin = "";

        String mod = "" ,mod1 = "",mod2 = "",mod3 = "",mod4="",mod5 = "";



        if(master.contains("."))
        {
            mod = ""+master.replace(".","dot") ;
        }
        else{
            mod = master;
        }
        if(mod.contains("$"))
        {


            mod1 = ""+mod.replace("$","dollar");
        }
        else{
            mod1 = mod;
        }
        if(mod1.contains("["))
        {

            mod2 = ""+mod1.replace("[","lsb");
        }
        else{
            mod2 = mod1;
        }
        if(mod2.contains("]"))
        {

            mod3 = ""+mod2.replace("]","rsb");
        }
        else{
            mod3 = mod2;
        }
        if(mod3.contains("#"))
        {

            mod4 = ""+mod3.replace("#","hash");
        }
        else{
            mod4 = mod3;
        }
        if(mod4.contains("/"))
        {

            mod5 = ""+mod4.replace("/","fs");

            fin = mod5;
        }
        else{
            mod5 = mod4;

            fin = mod5;

        }


        System.out.println(""+fin);

        return   fin;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK)
        {

            Toast.makeText(getApplicationContext(),"inside picker",Toast.LENGTH_SHORT).show();
            photouri = data.getData();

        }
    }


    private void initialiseclick(int media) {

        if((media == 0) ||(media == 2) ){
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getApplicationContext(), "video view", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("video/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "documents"), RC_PHOTO_PICKER);

                }
            });

        }
        else if((media == 1) || (media == 3)) {

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getApplicationContext(), "video view", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "documents"), RC_PHOTO_PICKER);

                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.push,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.push :

                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Pushing Data ...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                Toast.makeText(getApplicationContext(),"pushing",Toast.LENGTH_SHORT).show();
                AndroidSystempusher();
                break;
        }
        return true;
    }

    public String getSystemDate()
    {

        Calendar calendar =Calendar.getInstance();

        return ""+calendar.getTime();
    }

}
