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
import android.widget.TimePicker;

/**
 * Created by Jordan Ang on 3/12/2017.
 */

public class TimePickerFragment extends DialogFragment{

    TimePicker timePicker;
    Button submitTimeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_picker, container, false);

        int initialHour = getArguments().getInt("INITIAL_HOUR");
        int initialMinute = getArguments().getInt("INITIAL_MINUTE");

        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        submitTimeButton = (Button) v.findViewById(R.id.submitTimeButton);

        timePicker.setHour(initialHour);
        timePicker.setMinute(initialMinute);

        submitTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Intent timeDataPackage = new Intent();
                timeDataPackage.putExtra("HOUR", hour);
                timeDataPackage.putExtra("MINUTE", minute);

                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, timeDataPackage);

                dismiss();
            }
        });

        return v;
    }
}
