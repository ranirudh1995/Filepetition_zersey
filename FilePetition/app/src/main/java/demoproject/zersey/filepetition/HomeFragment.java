package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;

public class HomeFragment extends Fragment implements Serializable{

    private Button petition;

    MainActivity activity;


//
	public HomeFragment(){

        //activity = ac;
    }



//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//        petition = (Button) findViewById(R.id.button);
//    }

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        petition = (Button) rootView.findViewById(R.id.button);
        activity = (MainActivity)getArguments().get("activity");


        petition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new LogInFragment();
                if(fragment!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();

                    // update selected item and title, then close the drawer

                    Bundle b = new Bundle();
                    b.putSerializable("activity",activity);
                    fragment.setArguments(b);


                    activity.mDrawerList.setItemChecked(3, true);


                    activity.setTitle(activity.navMenuTitles[3]);



                }

            }
        });



        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();

        Log.d("anirudh", "ani");

        if(activity!=null)
        {
            Log.d("anirudh","ani1");
            activity = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(activity==null)
        {
            activity = (MainActivity)getArguments().get("activity");
        }
    }
}
