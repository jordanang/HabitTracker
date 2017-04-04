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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class EditHabitFragment extends DialogFragment {

    int TIME_PICKER_REQUEST_CODE = 100;

    TextView dialogTitle;
    EditText habitEditText;
    CheckBox sundayCheckbox;
    CheckBox mondayCheckbox;
    CheckBox tuesdayCheckbox;
    CheckBox wednesdayCheckBox;
    CheckBox thursdayCheckbox;
    CheckBox fridayCheckbox;
    CheckBox saturdayCheckbox;
    TimePicker timePicker;
    TextView timeTextView;
    Button editButton;
    Button discardButton;
    Button saveButton;

    int repeatHour;
    int repeatMinute;
    int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Grab item being edited
        position = getArguments().getInt("POSITION");
        final HabitItem currentItem = habitList.get(position);

        View v = inflater.inflate(R.layout.fragment_create_habit, container, false);

        dialogTitle = (TextView) v.findViewById(R.id.dialogTitle);
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
        saveButton = (Button) v.findViewById(R.id.createButton);

        //Set dialog title to "Edit habit"
        dialogTitle.setText("Edit habit");

        //Set button from create to edit
        saveButton.setText("Edit");

        //Set habit title
        habitEditText.setText(currentItem.habitTitle);

        //Set days
        CheckBox[] checkBoxes = {sundayCheckbox, mondayCheckbox, tuesdayCheckbox, wednesdayCheckBox, thursdayCheckbox, fridayCheckbox, saturdayCheckbox};
        for(int i=0; i<7; i++){
            if(currentItem.daysToRepeat[i] == true){
                checkBoxes[i].setChecked(true);
            }
        }

        //Set time
        repeatHour = currentItem.hourToRepeat;
        repeatMinute = currentItem.minuteToRepeat;

        timePicker.setHour(repeatHour);
        timePicker.setMinute(repeatMinute);

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get values from widget and store in variables
                String habitTitle = habitEditText.getText().toString();

                boolean[] checked = new boolean[7];
                checked[0] = sundayCheckbox.isChecked();
                checked[1] = mondayCheckbox.isChecked();
                checked[2] = tuesdayCheckbox.isChecked();
                checked[3] = wednesdayCheckBox.isChecked();
                checked[4] = thursdayCheckbox.isChecked();
                checked[5] = fridayCheckbox.isChecked();
                checked[6] = saturdayCheckbox.isChecked();

                repeatHour = timePicker.getCurrentHour();
                repeatMinute = timePicker.getCurrentMinute();

                //Update current item
                habitList.get(position).habitTitle = habitTitle;
                habitList.get(position).daysToRepeat = checked;
                habitList.get(position).hourToRepeat = repeatHour;
                habitList.get(position).minuteToRepeat = repeatMinute;

                Toast.makeText(getContext(), habitTitle + " has been edited!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity().getIntent());
                dismiss();
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                startActivity(intent);
            }
        });


        return v;
    }
}
