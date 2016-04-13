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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import demoproject.zersey.filepetition.library.DatabaseHandler;
import demoproject.zersey.filepetition.library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by anirudh on 10/4/16.
 */
public class ChangePasswordFragment extends Fragment {

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";

    EditText newpass;
    TextView alert;
    Button changepass;
    Button cancel;

    public ChangePasswordFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.changepassword, container, false);


        cancel = (Button) rootView.findViewById(R.id.btcancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                Fragment f1 = new Main();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();


                }
                //getActivity().finish();
            }

        });

        newpass = (EditText) rootView.findViewById(R.id.newpass);
        alert = (TextView) rootView.findViewById(R.id.alertpass);
        changepass = (Button) rootView.findViewById(R.id.btchangepass);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetAsync(view);
            }
        });
        return rootView;
    }

    private class NetCheck extends AsyncTask
    {
        private ProgressDialog nDialog;


        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity().getApplicationContext());
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

       // @Override
        protected Boolean doInBackground(String... args){
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }

        public NetCheck() {
            super();
        }

        //@Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
                alert.setText("Error in Network Connection");
            }
        }
    }

    private class ProcessRegister extends AsyncTask {

        private ProgressDialog pDialog;

        String newpas,email;

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

            HashMap user = new HashMap();
            user = db.getUserDetails();

            newpas = newpass.getText().toString();
            email = user.get("email").toString();

            pDialog = new ProgressDialog(getActivity().getApplicationContext());
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

       // @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.chgPass(newpas, email);
            Log.d("Button", "Register");
            return json;

        }

        //@Override
        protected void onPostExecute(JSONObject json) {

            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    alert.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    String red = json.getString(KEY_ERROR);

                    if (Integer.parseInt(res) == 1) {
                        /**
                         * Dismiss the process dialog
                         **/
                        pDialog.dismiss();
                        alert.setText("Your Password is successfully changed.");

                    } else if (Integer.parseInt(red) == 2) {
                        pDialog.dismiss();
                        alert.setText("Invalid old Password.");
                    } else {
                        pDialog.dismiss();
                        alert.setText("Error occured in changing Password.");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
    }

    public void NetAsync(View view){
        new NetCheck().execute();
    }
}
