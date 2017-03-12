package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView timeTextView;
    Button editButton;
    Button discardButton;
    Button saveButton;

    int repeatHour;
    int repeatMinute;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TIME_PICKER_REQUEST_CODE){
            if(resultCode == 1) {
                Toast.makeText(getContext(), "New time set!", Toast.LENGTH_SHORT ).show();

                repeatHour = data.getExtras().getInt("HOUR");
                repeatMinute = data.getExtras().getInt("MINUTE");

                String hourString;
                String minuteString;
                String AMPM;

                if(repeatHour == 0){
                    hourString = "12";
                    AMPM = "AM";
                } else if(repeatHour >= 13){
                    hourString = "" + (repeatHour - 12);
                    AMPM = "PM";
                } else {
                    hourString = "" + repeatHour;
                    AMPM = "AM";
                }

                minuteString = "" + repeatMinute;

                timeTextView.setText(hourString + ":" + minuteString + " " + AMPM);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_create_habit, container, false);

        habitEditText = (EditText) v.findViewById(R.id.habitEditText);
        sundayCheckbox = (CheckBox) v.findViewById(R.id.sundayCheckbox);
        mondayCheckbox = (CheckBox) v.findViewById(R.id.mondayCheckbox);
        tuesdayCheckbox = (CheckBox) v.findViewById(R.id.tuesdayCheckbox);
        wednesdayCheckBox = (CheckBox) v.findViewById(R.id.wednesdayCheckBox);
        thursdayCheckbox = (CheckBox) v.findViewById(R.id.thursdayCheckbox);
        fridayCheckbox = (CheckBox) v.findViewById(R.id.fridayCheckbox);
        saturdayCheckbox = (CheckBox) v.findViewById(R.id.saturdayCheckbox);
        timeTextView = (TextView) v.findViewById(R.id.timeTextView);
        editButton = (Button) v.findViewById(R.id.editButton);
        discardButton = (Button) v.findViewById(R.id.discardButton);
        saveButton = (Button) v.findViewById(R.id.saveButton);

        repeatHour = 8;
        repeatMinute = 0;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newTimePickerFragment = new TimePickerFragment();
                newTimePickerFragment.setTargetFragment(CreateHabitFragment.this, TIME_PICKER_REQUEST_CODE);
                newTimePickerFragment.show(getFragmentManager(), "timeFragment");
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get values from widget and store in variables
                String habit = habitEditText.getText().toString();
                boolean[] checked = new boolean[7];
                checked[0] = sundayCheckbox.isChecked();
                checked[1] = mondayCheckbox.isChecked();
                checked[2] = tuesdayCheckbox.isChecked();
                checked[3] = wednesdayCheckBox.isChecked();
                checked[4] = thursdayCheckbox.isChecked();
                checked[5] = fridayCheckbox.isChecked();
                checked[6] = saturdayCheckbox.isChecked();

                //addItem(title, detail, checked, hour, minute);

                dismiss();
            }
        });


        return v;
    }
}
