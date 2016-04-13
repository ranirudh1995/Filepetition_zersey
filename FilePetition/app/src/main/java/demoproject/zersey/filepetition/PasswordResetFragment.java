package demoproject.zersey.filepetition;

import android.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import demoproject.zersey.filepetition.library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anirudh on 10/4/16.
 */
public class PasswordResetFragment extends Fragment {

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";

    MainActivity activity;

    EditText email;
    TextView alert;
    Button resetpass;

    public PasswordResetFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.passwordreset, container, false);
        activity = (MainActivity)getArguments().get("activity");


        Button login = (Button) rootView.findViewById(R.id.bktolog);
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
                //getActivity().finish();
            }

        });

        email = (EditText) rootView.findViewById(R.id.forpas);
        alert = (TextView) rootView.findViewById(R.id.alert);
        resetpass = (Button) rootView.findViewById(R.id.respass);
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //func();
                email.setText("");

            }

        });
        return rootView;
    }



    private void func()
    {
        class ProcessRegister extends AsyncTask {

            private ProgressDialog pDialog;

            String forgotpassword;

            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                forgotpassword = email.getText().toString();

                pDialog = new ProgressDialog(getActivity().getApplicationContext());
                pDialog.setTitle("Contacting Servers");
                pDialog.setMessage("Getting Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            //@Override
            protected JSONObject doInBackground(String... args) {

                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.forPass(forgotpassword);
                return json;

            }

            //@Override
            protected void onPostExecute(JSONObject json) {
                /**
                 * Checks if the Password Change Process is sucesss
                 **/
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        alert.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        String red = json.getString(KEY_ERROR);

                        if(Integer.parseInt(res) == 1){
                            pDialog.dismiss();
                            alert.setText("A recovery email is sent to you, see it for more details.");

                        }
                        else if (Integer.parseInt(red) == 2)
                        {    pDialog.dismiss();
                            alert.setText("Your email does not exist in our database.");
                        }
                        else {
                            pDialog.dismiss();
                            alert.setText("Error occured in changing Password");
                        }

                    }}
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }

        ProcessRegister pr = new ProcessRegister();
        pr.execute();
    }




}
