package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PetitionListFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;
	
	public PetitionListFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_view_all_petition, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();



        return rootView;
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PetitionListFragment.this.getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                Log.d("ani12",s);
                showPetition();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);

                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        Intent intent = new Intent(this, ViewEmployee.class);
//        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
//        String empId = map.get(Config.TAG_ID).toString();
//        intent.putExtra(Config.EMP_ID,empId);
//        startActivity(intent);
    }



    private void showPetition(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            Log.d("ani11",JSON_STRING);
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_TITLE);
                String dmaker = jo.getString(Config.TAG_DECISION_MAKER);
                String explain = jo.getString(Config.TAG_EXPLANATION);
                String createdBy = jo.getString(Config.TAG_U_CREATEDBY);


                HashMap<String,String> petitions = new HashMap<>();
                petitions.put(Config.TAG_ID,id);
                petitions.put(Config.TAG_TITLE,name);
                petitions.put(Config.TAG_DECISION_MAKER,dmaker);
                petitions.put(Config.TAG_EXPLANATION,explain);
                petitions.put(Config.TAG_U_CREATEDBY,createdBy);

                list.add(petitions);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                PetitionListFragment.this.getActivity(), list, R.layout.list_item_petition,
                new String[]{Config.TAG_ID,Config.TAG_TITLE,Config.TAG_DECISION_MAKER,Config.TAG_EXPLANATION,Config.TAG_U_CREATEDBY},
                new int[]{R.id.id, R.id.name,R.id.decision_maker,R.id.explanation,R.id.createdBy});

        listView.setAdapter(adapter);
    }
}
