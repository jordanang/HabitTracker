package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;
import static java.lang.Integer.parseInt;

public class HabitOverview extends AppCompatActivity {
    int position;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_habit_overview);

        //Set toolbar back button and set listener
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get item clicked
        position = getIntent().getExtras().getInt("POSITION");
        final HabitItem currentItem = habitList.get(position);

        //Define views
        TextView habitQuestion_tv = (TextView) findViewById(R.id.habitQuestion);
        CheckBox completedTodayCheckBox = (CheckBox) findViewById(R.id.completedTodayCheckbox);
        TextView daysToRepeat_tv = (TextView) findViewById(R.id.daysToRepeat);
        TextView timeToRepeat_tv = (TextView) findViewById(R.id.timeToRepeat);
        TextView monthPercentage_tv = (TextView) findViewById(R.id.monthPercentage);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.overviewProgressBar);
        TextView total_tv = (TextView) findViewById(R.id.total_textview);
        TextView best_tv = (TextView) findViewById(R.id.bestStreak_textview);
        TextView currentStreak_tv = (TextView) findViewById(R.id.currentStreak_textview);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        //------------Set-up views with proper information from habit item-----------

        //Setup habit question
        String habitTitle = currentItem.habitTitle;
        habitTitle = habitTitle.substring(0, habitTitle.length()).toLowerCase();
        String question = "Did you " + habitTitle + " today?";
        habitQuestion_tv.setText(question);

        //Set initial check and set listener for checkbox
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        currentItem.completedHabitToday =  sqLiteHelper.checkCompleted(habitTitle, dateFormat.format(date));
        completedTodayCheckBox.setChecked(currentItem.completedHabitToday);

        completedTodayCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentItem.completedHabitToday == true){
                    currentItem.uncomplete();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();

                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                    sqLiteHelper.deleteCompleted(currentItem.habitTitle, dateFormat.format(date));
                    sqLiteHelper.viewDb();

                    //Refresh activity
                    Intent intent = getIntent();
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                } else {
                    currentItem.complete();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();

                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                    sqLiteHelper.addCompletedHabit(currentItem.habitTitle, dateFormat.format(date));
                    sqLiteHelper.viewDb();

                    //Refresh activity
                    Intent intent = getIntent();
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }

            }
        });

        //Setup days to repeat
        if (currentItem.someDayChosen() == false) {
            daysToRepeat_tv.setText("No days to remind chosen");
        } else {
            daysToRepeat_tv.setText(currentItem.createRepeatDaysString());
        }

        //Setup time to repeat
        timeToRepeat_tv.setText(currentItem.createTimeString());

        //Setup current streak
        String currentStreak_string = "" + currentItem.currentStreak;
        currentStreak_tv.setText(currentStreak_string);

        //Setup best streak
        String bestStreak_string = "" + currentItem.bestStreak;
        best_tv.setText(bestStreak_string);

        //Setup total
        String total_string = "" + currentItem.total;
        total_tv.setText(total_string);

        //Set up graph
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(7);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(8);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Week #");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Times completed");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, 1),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 4),
                new DataPoint(5, 1),
                new DataPoint(6, 5),
                new DataPoint(7, 4),
                new DataPoint(8, 7),
        });
        series.setColor(Color.parseColor("#00bcd4"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        graph.addSeries(series);

        //Set calendar
        int completedThisMonth = 0;
        String[] dateArray = dateFormat.format(date).split("-");
        int currYear = parseInt(dateArray[0]);
        int currMonth = parseInt(dateArray[1]);
        calendarView.setClickable(false);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        ArrayList<String> completedDates = sqLiteHelper.getCompletedDates(habitTitle);
        for(String d: completedDates) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date completedDate = simpleDateFormat.parse(d);
                calendarView.setDateSelected(completedDate, true);
                int completeYear = Integer.parseInt(dateFormat.format(completedDate).split("-")[0]);
                int completeMonth = Integer.parseInt(dateFormat.format(completedDate).split("-")[1]);
                Log.d("Calendar", Integer.toString(completeYear) + "-" + Integer.toString(completeMonth) + "=="
                        + Integer.toString(currYear) + "-" + Integer.toString(currMonth));
                if( completeYear == currYear && completeMonth == currMonth){
                    completedThisMonth++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //Setup month percentage
        int numberOfDaysInMonth = numberOfDaysInMonth(currYear, currMonth);
        int currentMonthPercentage = percentageOfMonthComplete(completedThisMonth, numberOfDaysInMonth);
        String currentMonthPercentage_string = "" + currentMonthPercentage + "%";
        monthPercentage_tv.setText(currentMonthPercentage_string);

        //Setup progress bar
        progressBar.setProgress(currentMonthPercentage);



        //----------------------------------------------------------------------------

    }

    public static int percentageOfMonthComplete(int numberDaysComplete, int numberDaysInMonth)
    {
        return (int) ((numberDaysComplete / (double) numberDaysInMonth) * 100);
    }

    public static int numberOfDaysInMonth(int year, int month) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habit_over_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            DeleteFragment deleteFragment = new DeleteFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION", position);
            deleteFragment.setArguments(bundle);
            deleteFragment.show(getSupportFragmentManager(), "deleteDialog");
        } else if (id == R.id.editButton){
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION", position);
            EditHabitFragment newDialogFragment = new EditHabitFragment();
            newDialogFragment.setArguments(bundle);
            newDialogFragment.show(getSupportFragmentManager(), "dialog");
        } else if (id == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
