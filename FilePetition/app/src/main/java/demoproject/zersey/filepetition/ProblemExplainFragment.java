package demoproject.zersey.filepetition;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anirudh on 10/4/16.
 */
public class ProblemExplainFragment extends Fragment{

    public ProblemExplainFragment(){

    }

    RequestQueue queue;
    private Button petition,back;
    private ImageButton logout;
    private EditText text3;
    public String title,dmaker,explain,createdBy;
    MainActivity activity;

    String url = "http://192.168.113.5/orders/take_order.php";
    String item_name;

    EditText item_et;
    ProgressDialog PD;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        queue = Volley.newRequestQueue(getActivity());
        final View rootView = inflater.inflate(R.layout.fragment_problemexplain, container, false);
        activity = (MainActivity)getArguments().get("activity");

        createdBy = getArguments().getString("createdBy");


//        PD = new ProgressDialog(this.getActivity().getApplicationContext());
//        PD.setMessage("Loading.....");
//        PD.setCancelable(false);

        //item_et = (EditText) findViewById(R.id.item_et_id);


        petition = (Button) rootView.findViewById(R.id.button3);
        back = (Button) rootView.findViewById(R.id.button5);
        logout = (ImageButton)rootView.findViewById(R.id.imageButton3);
        item_et = (EditText) rootView.findViewById(R.id.editText3);


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

                addPetition();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f1 = new DecisionMakerFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    Bundle b = new Bundle();
                    b.putString("createdBy",createdBy);
                    b.putSerializable("activity",activity);
                    f1.setArguments(b);

                    //activity.mDrawerList.setItemChecked(2, true);


                    activity.setTitle("Decision Groups");


                }
            }
        });

        return rootView;


    }

    private void addPetition(){

        explain = item_et.getText().toString();


        //item_et = text3;

        title = this.getArguments().getString("title");
        dmaker = this.getArguments().getString("maker");
        //title = "and";
        //dmaker = "jkajfjf";
        Log.d("ani1",title);


        class AddPetition extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(ProblemExplainFragment.this.getActivity(),"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(ProblemExplainFragment.this.getActivity(),s,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                Log.d("ani15",createdBy);

                params.put(Config.KEY_P_TITLE,title);
                params.put(Config.KEY_P_DECISION_MAKER,dmaker);
                params.put(Config.KEY_P_EXPLANATION,explain);
                params.put(Config.KEY_P_CREATEDBY,createdBy);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddPetition ae = new AddPetition();
        ae.execute();
    }


}
