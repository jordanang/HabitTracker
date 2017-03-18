package edu.csb.cs.cs185.jordanang.habittracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class UpdateHabitsFragment extends DialogFragment {

    ListView list;
    Button submitButton;
    CustomAdapter_update customAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_update_habits_fragment, container, false);

        list = (ListView) v.findViewById(R.id.updateListView);
        submitButton = (Button) v.findViewById(R.id.submitButton);

        //Set listView adapter
        customAdapter = new CustomAdapter_update(getActivity().getApplicationContext(), habitList);
        list.setAdapter(customAdapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Submit button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
