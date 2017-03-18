package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class HabitOverview extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_overview);

        //Get item clicked
        final int position = getIntent().getExtras().getInt("POSITION");
        HabitItem currentItem = habitList.get(position);

        //Define views
        TextView habitQuestion_tv = (TextView) findViewById(R.id.habitQuestion);
        TextView daysToRepeat_tv = (TextView) findViewById(R.id.daysToRepeat);
        TextView timeToRepeat_tv = (TextView) findViewById(R.id.timeToRepeat);
        TextView monthPercentage_tv = (TextView) findViewById(R.id.monthPercentage);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.overviewProgressBar);
        TextView currentStreak_tv = (TextView) findViewById(R.id.currentStreak_textview);
        FloatingActionButton editFab = (FloatingActionButton) findViewById(R.id.editFab);

        //------------Set-up views with proper information from habit item-----------

        //Setup edit floating action bottom
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);
                EditHabitFragment newDialogFragment = new EditHabitFragment();
                newDialogFragment.setArguments(bundle);
                newDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        //Setup habit question
        String question = "Did you ";
        question = question + currentItem.habitTitle + " today?";
        habitQuestion_tv.setText(question);

        //Setup days to repeat
        String days = "";
        String[] dayAcronym = {"SU", "M", "T", "W", "R", "F", "SA"};
        boolean noDays = true;
        for(int i = 0; i < 7; i++){
            if(currentItem.daysToRepeat[i] == true){
                days = days + dayAcronym[i];
                if(i < 6 ){
                    days = days + " ";
                }
                noDays = false;
            }
        }
        if(noDays){
            daysToRepeat_tv.setText("No days to remind chosen");
        } else {
            daysToRepeat_tv.setText(days);
        }

        //Setup time to repeat
        int repeatHour = currentItem.hourToRepeat;
        int repeatMinute = currentItem.minuteToRepeat;

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

        if(repeatMinute <10) {
            minuteString = "0" + repeatMinute;
        } else {
            minuteString = "" + repeatMinute;
        }

        timeToRepeat_tv.setText(hourString + ":" + minuteString + " " + AMPM);

        //TODO: Setup percentage of month
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int numDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int currentMonthPercentage = 66;
        String currentMonthPercentage_string = "" + currentMonthPercentage + "%";
        monthPercentage_tv.setText(currentMonthPercentage_string);

        //Setup current streak
        String currentStreak_string = "" + currentItem.currentStreak;
        currentStreak_tv.setText(currentStreak_string);

        //Setup progress bar
        progressBar.setProgress(currentMonthPercentage);

        //----------------------------------------------------------------------------

    }
}
