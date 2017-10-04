package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();

                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                    sqLiteHelper.deleteCompleted(currentItem.habitTitle, dateFormat.format(date));

                    //Refresh activity
                    Intent intent = getIntent();
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();

                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                    sqLiteHelper.addCompletedHabit(currentItem.habitTitle, dateFormat.format(date));

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
        int totalComplete = 0;
        int completedThisMonth = 0;

        String[] dateArray = dateFormat.format(date).split("-");
        int currYear = parseInt(dateArray[0]);
        int currMonth = parseInt(dateArray[1]);

        calendarView.setClickable(false);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        ArrayList<String> completedDates = sqLiteHelper.getCompletedDates(habitTitle);
        try {
            sortDates(completedDates);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(String d: completedDates) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                totalComplete++;
                Date completedDate = simpleDateFormat.parse(d);
                calendarView.setDateSelected(completedDate, true);
                int completeYear = Integer.parseInt(dateFormat.format(completedDate).split("-")[0]);
                int completeMonth = Integer.parseInt(dateFormat.format(completedDate).split("-")[1]);
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

        //Setup total
        String total_string = "" + totalComplete;
        total_tv.setText(total_string);

        try {
            //Setup current streak
            String currentStreak_string = "" + getCurrStreak(completedDates);
            currentStreak_tv.setText(currentStreak_string);

            //Setup best streak
            String bestStreak_string = "" + getBestStreak(completedDates);
            best_tv.setText(bestStreak_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }





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

    public static void sortDates(ArrayList<String> dates) throws ParseException {
        for(int i=0; i<dates.size(); i++){
            for(int j=i; j>0; j--) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date a = simpleDateFormat.parse(dates.get(j));
                Date b = simpleDateFormat.parse(dates.get(j - 1));
                if (a.before(b)) {
                    String temp = dates.get(j);
                    dates.set(j, dates.get(j-1));
                    dates.set(j - 1, temp);
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getCurrStreak(ArrayList<String> dates) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = simpleDateFormat.format(date);
        if (dates.contains(todaysDate)) {
            int currStreak = 1;
            int numDates = dates.size() - 1;
            while(numDates > 0) {
                android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                date = simpleDateFormat.parse(dates.get(numDates));
                c.setTime(date);
                c.add(android.icu.util.Calendar.DATE, -1);
                String prevDay = simpleDateFormat.format(c.getTime());
                if (prevDay.equals(dates.get(numDates - 1))) {
                    currStreak++;
                    Log.d("streak", prevDay);
                } else {
                    break;
                }
                numDates--;
            }
            return currStreak;
        }
        else
        {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getBestStreak(ArrayList<String> dates) throws ParseException {
        int bestStreak = 1;
        int currStreak = 1;

        if(dates.size() == 0) {
            bestStreak = 0;
            return bestStreak;
        }

        for(int i=0; i<dates.size()-1; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
            Date d = simpleDateFormat.parse(dates.get(i));
            c.setTime(d);
            c.add(android.icu.util.Calendar.DATE, 1);
            String nextDay = simpleDateFormat.format(c.getTime());
            if(dates.get(i+1).equals(nextDay)){
                currStreak++;
                if(currStreak > bestStreak) {
                    bestStreak = currStreak;
                }
            } else {
                currStreak = 1;
            }
        }

        return bestStreak;
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
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
