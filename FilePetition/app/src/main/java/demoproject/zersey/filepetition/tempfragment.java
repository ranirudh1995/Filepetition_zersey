package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by anirudh on 12/4/16.
 */
public class tempfragment extends Fragment {

    Button b1;
    MainActivity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_temp, container, false);
        activity = (MainActivity)getArguments().get("activity");

        b1 = (Button)rootView.findViewById(R.id.buttonp);

        b1.setOnClickListener(new View.OnClickListener() {
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

        return rootView;
    }
}
