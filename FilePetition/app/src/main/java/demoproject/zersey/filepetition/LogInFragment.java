package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;


import android.widget.Toast;

import demoproject.zersey.filepetition.library.DatabaseHandler;
import demoproject.zersey.filepetition.library.UserFunctions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LogInFragment extends Fragment {
	
	public LogInFragment(){}

    Button btnLogin;
    Button Btnregister;
    Button passreset;
    EditText inputEmail;
    EditText inputPassword;
    private TextView loginErrorMsg;
    MainActivity activity;
    /**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_login, container, false);


        inputEmail = (EditText) rootView.findViewById(R.id.email);
        inputPassword = (EditText) rootView.findViewById(R.id.pword);
        Btnregister = (Button) rootView.findViewById(R.id.registerbtn);
        btnLogin = (Button) rootView.findViewById(R.id.login);
        passreset = (Button)rootView.findViewById(R.id.passres);
        loginErrorMsg = (TextView) rootView.findViewById(R.id.loginErrorMsg);
        activity = (MainActivity)getArguments().get("activity");

        passreset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment f1 = new PasswordResetFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    //getActivity().finish();

                    Bundle b = new Bundle();
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);
                    activity.setTitle("Password Reset");



                }
            }});

        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment f1 = new SignUpFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    Bundle b = new Bundle();
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);

                    activity.mDrawerList.setItemChecked(2, true);


                    activity.setTitle(activity.navMenuTitles[2]);


                }
            }});


        /**
         * Login button click event
         * A Toast is set to alert when the Email and Password field is empty
         **/


        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                    login();
                    inputEmail.setText("");
                    inputPassword.setText("");

                }
                else if ( ( !inputEmail.getText().toString().equals("")) )
                {
                    Toast.makeText(LogInFragment.this.getActivity(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(LogInFragment.this.getActivity(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LogInFragment.this.getActivity(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }





    /**
     * Async Task to get and send data to My Sql database through JSON respone.
     **/



    private void login()
    {
        class ProcessLogin extends AsyncTask<Void,Void,String> {

            private ProgressDialog pDialog;

            String email,password;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                pDialog = new ProgressDialog(LogInFragment.this.getActivity());
                pDialog.setTitle("Contacting Servers");
                pDialog.setMessage("Logging in ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                Toast.makeText(LogInFragment.this.getActivity(),s,Toast.LENGTH_LONG).show();

                if(s.equals("Login Successfull"))
                {
                    Fragment f1 = new StartPetitionFragment();

                    if(f1!=null)
                    {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, f1).commit();


                        activity.mDrawerList.setItemChecked(4, true);


                        activity.setTitle(activity.navMenuTitles[4]);

                        Bundle b = new Bundle();
                        b.putString("createdBy",email);
                        b.putSerializable("activity",activity);
                        f1.setArguments(b);

                    }
                }

                if(s.equals("error"))
                {
                    Log.d("ani56","aniiii");
                }
            }


            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();

                params.put(Config.KEY_U_EMAIL,email);
                params.put(Config.KEY_U_PASSWORD,password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_USER_LOGIN, params);
                return res;
            }


        }

        ProcessLogin pl = new ProcessLogin();
        pl.execute();
    }



}
