package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import demoproject.zersey.filepetition.library.UserFunctions;
import demoproject.zersey.filepetition.library.DatabaseHandler;

import java.util.HashMap;

/**
 * Created by anirudh on 10/4/16.
 */
public class Main extends Fragment {

    Button btnLogout;
    Button changepas;

    public Main(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main, container, false);

        changepas = (Button) rootView.findViewById(R.id.btchangepass);
        btnLogout = (Button) rootView.findViewById(R.id.logout);

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        /**
         * Hashmap to load data from the Sqlite database
         **/
        HashMap user = new HashMap();
        user = db.getUserDetails();

        /**
         * Change Password Activity Started
         **/
        changepas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                Fragment f1 = new ChangePasswordFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();


                }
            }

        });

        /**
         *Logout from the User Panel which clears the data in Sqlite database
         **/
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                UserFunctions logout = new UserFunctions();
                logout.logoutUser(getActivity().getApplicationContext());
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
/**
 * Sets user first name and last name in text view.
 **/
        final TextView login = (TextView) rootView.findViewById(R.id.textwelcome);
        login.setText("Welcome  "+user.get("fname"));
        final TextView lname = (TextView) rootView.findViewById(R.id.lname);
        lname.setText(user.get("lname").toString());
        return rootView;
    }
}
