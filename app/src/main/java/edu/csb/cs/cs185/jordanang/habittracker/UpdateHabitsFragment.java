package edu.csb.cs.cs185.jordanang.habittracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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
        View v = inflater.inflate(R.layout.fragment_update_habits, container, false);

        list = (ListView) v.findViewById(R.id.updateListView);
        submitButton = (Button) v.findViewById(R.id.submitButton);

        //Set listView adapter
        customAdapter = new CustomAdapter_update(getActivity().getApplicationContext(), habitList);
        list.setAdapter(customAdapter);

     /*   submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<habitList.size(); i++) {
                    CheckBox checkBox = (CheckBox) list.getChildAt(i).findViewById(R.id.todaysDone_checkbox);
                    if(checkBox.isChecked() == true && habitList.get(i).completedHabitToday == false){
                        habitList.get(i).complete();
                    } else if (checkBox.isChecked() == false && habitList.get(i).completedHabitToday == true){
                        habitList.get(i).uncomplete();
                    }
                }
                Intent intent = new Intent(getActivity().getIntent());
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
                dismiss();
                getActivity().finish();
                Toast.makeText(getContext(), "Today's habits updated", Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }
}
