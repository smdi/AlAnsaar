package al_muntaqimcrescent2018.com.al_ansar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Imran on 28-01-2018.
 */

public class MonthlyBayans extends Fragment {


    private FloatingActionButton fab;
    private Button video,audio;
    private RelativeLayout relativeLayout;
    android.app.FragmentTransaction fv,fa ;
    private Fragment fragmentVideo ,fragmentAudio;
    private String fullName = "smdimran838@gmail.com";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.downloads,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Monthly Bayans");
        initialise(view);
        fragmentInitialise();
        setFragmet(view);
    }

    synchronized private void  fragmentInitialise() {


        fragmentVideo = new Monthly_Video_Downloads();
        fragmentAudio = new Monthly_Audio_downloads();

        fa = getChildFragmentManager().beginTransaction();
        fa.add(R.id.downloadsfrag ,fragmentAudio).setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out).show(fragmentAudio).commit();

        relativeLayout.setBackgroundColor(Color.WHITE);

        fv = getChildFragmentManager().beginTransaction();
        fv.add(R.id.downloadsfrag ,fragmentVideo).setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out).show(fragmentVideo).commit();
    }


    private void initialise(View view) {


        relativeLayout = (RelativeLayout) view.findViewById(R.id.downloadsfrag);
        video = (Button) view.findViewById(R.id.video);
        video.setEnabled(false);
        getActivity().setTitle("Monthly Videos");
//        video.setBackgroundColor(Color.CYAN);

        audio = (Button) view.findViewById(R.id.audio);
//        audio.setBackgroundColor(Color.YELLOW);
        audio.setEnabled(true);


        fab = (FloatingActionButton) view.findViewById(R.id.fab_montly_downloads);

        fab.setImageResource(R.drawable.videocamera);
        getFab("video" ,view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(fullName.equals(user.getEmail())) {

            fab.setVisibility(View.VISIBLE);
        }

    }


    private void  setFragmet(View view){

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().setTitle("Monthly Audios");
//                setFragmetToVisible(fragmentAudio );
                fragmentVideo.getView().setVisibility(View.GONE);
                fragmentAudio.getView().setVisibility(View.VISIBLE);

//                   fa.show(fragmentAudio);

                relativeLayout.setBackgroundColor(Color.WHITE);
                fab.setImageResource(R.drawable.microphone);
                video.setEnabled(true);
                audio.setEnabled(false);
//                    fv.hide(fragmentVideo);
                getFab("audio",view);
//                video.setEnabled(true);
//                video.setBackgroundColor(Color.YELLOW);
//                audio.setEnabled(false);
//                audio.setBackgroundColor(Color.CYAN);

            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().setTitle("Monthly Videos");
                getFab("video" ,view);

                relativeLayout.setBackgroundColor(Color.WHITE);
                video.setEnabled(false);
                audio.setEnabled(true);
//                fa.hide(fragmentAudio);
                fab.setImageResource(R.drawable.videocamera);
//                fv.show(fragmentVideo);
                fragmentAudio.getView().setVisibility(View.GONE);
                fragmentVideo.getView().setVisibility(View.VISIBLE);
//                audio.setEnabled(true);
//                audio.setBackgroundColor(Color.YELLOW);
//                video.setEnabled(false);
//                video.setBackgroundColor(Color.CYAN);

            }
        });
    }

    private void getFab(String s,View view) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("chooser", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = preferences.edit();

        if(s.equals("video"))
        {


            fab.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                    Toast.makeText(getActivity(), "video", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(),VideoCreator.class);
                    startActivity(intent);

                    editor.putInt("media",2);
                    editor.commit();
                }
            });

        }
        else {

            fab.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                    Toast.makeText(getActivity(), "audio", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(),VideoCreator.class);
                    startActivity(intent);

                    editor.putInt("media",3);
                    editor.commit();
                }
            });
        }


    }
}
