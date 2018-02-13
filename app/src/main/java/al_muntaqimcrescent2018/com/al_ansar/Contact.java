package al_muntaqimcrescent2018.com.al_ansar;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 06-02-2018.
 */

public class Contact extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ContactInitialiser> listViewH;
    private static final String email = "smdimran838@gmail.com";
    private FloatingActionButton fab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbreference;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Contact Us");

        initialise(view);
        initialiseClicks(view);
//        firebaseInitialise();

        GetContacts getContacts = new GetContacts();
        getContacts.execute();

    }

    private void firebaseInitialise() {


        String fireDb = corrector( "Contact-list");

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbreference = firebaseDatabase.getReference().child(fireDb);
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

    private void initialise(View view) {


        fab = (FloatingActionButton) view.findViewById(R.id.fab_contact);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String emailUser = user.getEmail();

        if(email.equals(emailUser)) {

            fab.setVisibility(View.VISIBLE);
        }
        recyclerView = (RecyclerView)  view.findViewById(R.id.text_contact);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listViewH = new ArrayList();

    }

    private void initialiseClicks(View view) {

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "contact float", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),Contact_Creator.class);
                startActivity(intent);

            }
        });
    }

    public class GetContacts extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                Toast.makeText(getActivity(),"Loading",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            String fireDb = corrector( "Contact-list");

            firebaseDatabase = FirebaseDatabase.getInstance();
            dbreference = firebaseDatabase.getReference().child(fireDb);


             dbreference.addChildEventListener(new ChildEventListener() {
                 @Override
                 public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                     ContactInitialiser contact = dataSnapshot.getValue(ContactInitialiser.class);

                     listViewH.add(contact);
                     adapter = new ContactAdapter(getActivity(),listViewH);
                     recyclerView.setAdapter(adapter);

//
//                     Toast.makeText(getActivity(), ""+contact.getMail(), Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getActivity(), ""+contact.getProfession(), Toast.LENGTH_SHORT).show();
//                     Toast.makeText(getActivity(), ""+contact.getName(), Toast.LENGTH_SHORT).show();

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


            return null ;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


//            dbreference.addValueEventListener(valueEventListener);

        }
    }


}
