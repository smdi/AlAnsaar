package al_muntaqimcrescent2018.com.al_ansar;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Imran on 08-02-2018.
 */

public class Monthly_Video_Downloads extends Fragment {
    private FloatingActionButton fab;
    private static final String email = "smdimran838@gmail.com";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbreference;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private ProgressBar progressBar;

    private ChildEventListener childEventListener;

    View viewfor;

    ArrayList<Video_Audio_Initialiser> listViewH;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_downloads,container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise(view);
        initialiseClicks(view);

        GetVideos getVideos = new GetVideos();
        getVideos.execute();

    }
    private void initialise(View view) {

        recyclerView = (RecyclerView)  view.findViewById(R.id.text_contact);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listViewH = new ArrayList();

    }


    private void initialiseClicks(View view) {

    }

    private class GetVideos extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            String fireDb = corrector( "monthly-video-downloads") ;
            String fireStr =  corrector("monthly-video-downloads-storage");

            firebaseDatabase = FirebaseDatabase.getInstance();
            dbreference = firebaseDatabase.getReference().child(fireDb);
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference().child(fireStr);

            dbreference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    Video_Audio_Initialiser video_audio_initialiser = dataSnapshot.getValue(Video_Audio_Initialiser.class);

                    listViewH.add(video_audio_initialiser);

                    adapter = new Video_Audio_Adapter(getActivity(),listViewH);

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            return null;
        }
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

}
