package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class CreateHabitFragment extends DialogFragment {

    int TIME_PICKER_REQUEST_CODE = 100;

    EditText habitEditText;
    CheckBox sundayCheckbox;
    CheckBox mondayCheckbox;
    CheckBox tuesdayCheckbox;
    CheckBox wednesdayCheckBox;
    CheckBox thursdayCheckbox;
    CheckBox fridayCheckbox;
    CheckBox saturdayCheckbox;
    TimePicker timePicker;
    Button discardButton;
    Button createButton;

    int repeatHour;
    int repeatMinute;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_habit, container, false);

        habitEditText = (EditText) v.findViewById(R.id.habitEditText);
        sundayCheckbox = (CheckBox) v.findViewById(R.id.sundayCheckbox);
        mondayCheckbox = (CheckBox) v.findViewById(R.id.mondayCheckbox);
        tuesdayCheckbox = (CheckBox) v.findViewById(R.id.tuesdayCheckbox);
        wednesdayCheckBox = (CheckBox) v.findViewById(R.id.wednesdayCheckbox);
        thursdayCheckbox = (CheckBox) v.findViewById(R.id.thursdayCheckbox);
        fridayCheckbox = (CheckBox) v.findViewById(R.id.fridayCheckbox);
        saturdayCheckbox = (CheckBox) v.findViewById(R.id.saturdayCheckbox);
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        discardButton = (Button) v.findViewById(R.id.discardButton);
        createButton = (Button) v.findViewById(R.id.createButton);

        repeatHour = 9;
        repeatMinute = 30;
        timePicker.setHour(repeatHour);
        timePicker.setMinute(repeatMinute);

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get values from widget and store in variables
                String habitTitle = habitEditText.getText().toString();

                repeatHour = timePicker.getCurrentHour();
                repeatMinute = timePicker.getCurrentMinute();

                if(habitTitle.length() >= 2) {
                    habitTitle = habitTitle.substring(0, 1).toUpperCase() + habitTitle.substring(1);
                }

                boolean[] checked = new boolean[7];
                checked[0] = sundayCheckbox.isChecked();
                checked[1] = mondayCheckbox.isChecked();
                checked[2] = tuesdayCheckbox.isChecked();
                checked[3] = wednesdayCheckBox.isChecked();
                checked[4] = thursdayCheckbox.isChecked();
                checked[5] = fridayCheckbox.isChecked();
                checked[6] = saturdayCheckbox.isChecked();

                if(habitTitle.equals("")) {
                    Toast.makeText(getContext(), "Please enter a habit title", Toast.LENGTH_SHORT).show();
                }
                 else {
                    //Create new instance of habit item
                    HabitItem newHabitItem = new HabitItem(habitTitle, checked, repeatHour, repeatMinute);

                    //Add new item to list
                    habitList.add(newHabitItem);

                    Toast.makeText(getContext(), habitTitle + " has been added as a new habit!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity().getIntent());
                    dismiss();
                    getActivity().finish();
                    getActivity().overridePendingTransition(0,0);
                    startActivity(intent);
                }
            }
        });


        return v;
    }
}
