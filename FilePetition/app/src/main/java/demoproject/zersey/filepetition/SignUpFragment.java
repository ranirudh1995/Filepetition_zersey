package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import demoproject.zersey.filepetition.library.DatabaseHandler;
import demoproject.zersey.filepetition.library.UserFunctions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class SignUpFragment extends Fragment {

    /**
     *  JSON Response node names.
     **/

    private String JSON_STRING;

//    private static String KEY_SUCCESS = "success";
//    private static String KEY_UID = "uid";
//    private static String KEY_FIRSTNAME = "fname";
//    private static String KEY_LASTNAME = "lname";
//    private static String KEY_USERNAME = "uname";
//    private static String KEY_EMAIL = "email";
//    private static String KEY_CREATED_AT = "created_at";
//    private static String KEY_ERROR = "error";

    /**
     * Defining layout items.
     **/

    EditText inputFirstName;
    EditText inputLastName;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;
    TextView registerErrorMsg;
    MainActivity activity;
	
	public SignUpFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register, container, false);


        /**
        * Defining all layout items
        **/
        inputFirstName = (EditText) rootView.findViewById(R.id.fname);
        inputLastName = (EditText) rootView.findViewById(R.id.lname);
        inputUsername = (EditText) rootView.findViewById(R.id.uname);
        inputEmail = (EditText) rootView.findViewById(R.id.email);
        inputPassword = (EditText) rootView.findViewById(R.id.pword);
        btnRegister = (Button) rootView.findViewById(R.id.register);
        registerErrorMsg = (TextView) rootView.findViewById(R.id.register_error);

        activity = (MainActivity)getArguments().get("activity");

        /**
         * Button which Switches back to the login screen on clicked
         **/

        Button login = (Button) rootView.findViewById(R.id.bktologin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment f1 = new LogInFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    Bundle b = new Bundle();
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);


                    activity.mDrawerList.setItemChecked(3, true);


                    activity.setTitle(activity.navMenuTitles[3]);


                }
            }

        });

        /**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         * Another toast is set to alert Username must be 5 characters.
         **/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (  ( !inputUsername.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) && ( !inputFirstName.getText().toString().equals("")) && ( !inputLastName.getText().toString().equals("")) && ( !inputEmail.getText().toString().equals("")) )
                {
                    if ( inputUsername.getText().toString().length() > 4 ){
                        Register();
//                        inputFirstName.setText("");
//                        inputLastName.setText("");
//                        inputEmail.setText("");
//                        inputPassword.setText("");
//                        inputUsername.setText("");

                    }
                    else
                    {
                        Toast.makeText(SignUpFragment.this.getActivity(),
                                "Username should be minimum 5 characters", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUpFragment.this.getActivity(),
                            "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    private void Register()
    {
        class ProcessRegister extends AsyncTask<Void,Void,String>  {

            /**
             * Defining Process dialog
             */
            private ProgressDialog pDialog;


            String email, password, fname, lname, uname;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                fname = inputFirstName.getText().toString();
                lname = inputLastName.getText().toString();
                email = inputEmail.getText().toString();
                uname = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                pDialog = new ProgressDialog(SignUpFragment.this.getActivity());
                pDialog.setTitle("Contacting Servers");
                pDialog.setMessage("Registering ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                Toast.makeText(SignUpFragment.this.getActivity(),s,Toast.LENGTH_LONG).show();
                //email = inputEmail.getText().toString();
                Log.d("ani61",email);
                if(s.equals("User Added Successfully"))
                {
                    Fragment f1 = new StartPetitionFragment();

                    if(f1!=null)
                    {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, f1).commit();


                        activity.mDrawerList.setItemChecked(4, true);


                        activity.setTitle("Petition Title");

                        Bundle b = new Bundle();
                        b.putString("createdBy",email);
                        b.putSerializable("activity",activity);
                        f1.setArguments(b);

                    }
                }
            }


            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();


                params.put(Config.KEY_U_FIRSTNAME,fname);
                params.put(Config.KEY_U_LASTNAME,lname);
                params.put(Config.KEY_U_EMAIL,email);
                params.put(Config.KEY_U_PASSWORD,password);
                params.put(Config.KEY_U_USERNAME,uname);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_USER_ADD, params);
                return res;
            }
        }

        ProcessRegister pr = new ProcessRegister();
        pr.execute();
    }



}
