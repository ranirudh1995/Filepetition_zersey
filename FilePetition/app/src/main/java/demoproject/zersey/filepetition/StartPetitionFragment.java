package demoproject.zersey.filepetition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;

public class StartPetitionFragment extends Fragment {

    private Button petition;
    private EditText text;
    public String title, createdBy;
    private ImageButton logout;
    MainActivity activity;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_startpetition, container, false);
        activity = (MainActivity)getArguments().get("activity");



        petition = (Button) rootView.findViewById(R.id.button1);
        text = (EditText) rootView.findViewById(R.id.editText);
        createdBy = getArguments().getString("createdBy");
        logout = (ImageButton)rootView.findViewById(R.id.imageButton);

        Log.d("ani0",createdBy);


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
                title = text.getText().toString();

                Fragment f1 = new DecisionMakerFragment();

                if(f1!=null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, f1).commit();

                    final Bundle b = new Bundle();

                    b.putString("title",title);
                    b.putSerializable("activity",activity);
                    b.putString("createdBy",createdBy);

                    //b.putSerializable("decision",StartPetitionFragment.this);
                    f1.setArguments(b);

                }



            }
        });



        return rootView;
    }
}
