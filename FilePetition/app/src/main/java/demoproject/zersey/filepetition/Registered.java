package demoproject.zersey.filepetition;

import android.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import demoproject.zersey.filepetition.library.DatabaseHandler;

import java.util.HashMap;

/**
 * Created by anirudh on 10/4/16.
 */
public class Registered extends Fragment {



    public Registered(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.registered, container, false);



        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        HashMap user = new HashMap();
        user = db.getUserDetails();

        /**
         * Displays the registration details in Text view
         **/

        final TextView fname = (TextView)rootView.findViewById(R.id.fname);
        final TextView lname = (TextView)rootView.findViewById(R.id.lname);
        final TextView uname = (TextView)rootView.findViewById(R.id.uname);
        final TextView email = (TextView)rootView.findViewById(R.id.email);
        final TextView created_at = (TextView)rootView.findViewById(R.id.regat);
        fname.setText(user.get("fname").toString());
        lname.setText(user.get("lname").toString());
        uname.setText(user.get("uname").toString());
        email.setText(user.get("email").toString());
        created_at.setText(user.get("created_at").toString());

        Button login = (Button) rootView.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), Login.class);
//                startActivityForResult(myIntent, 0);

                Fragment f1 = new LogInFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();


                }
                //getActivity().finish();
            }

        });

        return rootView;
    }


}
