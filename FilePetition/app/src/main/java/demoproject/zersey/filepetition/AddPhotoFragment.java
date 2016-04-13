package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by anirudh on 10/4/16.
 */
public class AddPhotoFragment extends Fragment {

    private Button petition;
    //private EditText text;
    public String title,dmaker,explain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_addphoto, container, false);


        petition = (Button) rootView.findViewById(R.id.button4);


        //explain = text.getText().toString();



        petition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f1 = new StartPetitionFragment();

                if(f1!=null) {

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();



                }

            }
        });

        return rootView;


    }
}
