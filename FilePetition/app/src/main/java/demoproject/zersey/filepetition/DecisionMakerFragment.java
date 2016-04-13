package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anirudh on 9/4/16.
 */
public class DecisionMakerFragment extends Fragment implements Parcelable{

    private Button petition,back;
    private ImageButton logout;
    private EditText text2;
    public String title, dmaker, createdBy;
    StartPetitionFragment start;
    MainActivity activity;



    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_decisionmaker, container, false);
        activity = (MainActivity)getArguments().getSerializable("activity");
        createdBy = getArguments().getString("createdBy");


        petition = (Button) rootView.findViewById(R.id.button2);
        back = (Button) rootView.findViewById(R.id.button4);
        logout = (ImageButton)rootView.findViewById(R.id.imageButton2);
        text2 = (EditText) rootView.findViewById(R.id.editText2);



        title = getArguments().getString("title");



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f1 = new LogInFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    activity.mDrawerList.setItemChecked(3, true);


                    activity.setTitle(activity.navMenuTitles[3]);

                    Bundle b = new Bundle();
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);


                }
            }
        });
        petition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dmaker = text2.getText().toString();

                Fragment f1 = new ProblemExplainFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    Bundle b = new Bundle();
                    b.putString("title",title);
                    b.putString("maker",dmaker);
                    b.putString("createdBy",createdBy);
                    b.putSerializable("activity",activity);

                    activity.setTitle("Problem Explanation");

                    f1.setArguments(b);
                    //con.close();
                }



            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f1 = new StartPetitionFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    Bundle b = new Bundle();
                    b.putString("createdBy",createdBy);
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);

                    //activity.mDrawerList.setItemChecked(4, true);


                    activity.setTitle("Petition Title");


                }
            }
        });

        return rootView;


    }
}
