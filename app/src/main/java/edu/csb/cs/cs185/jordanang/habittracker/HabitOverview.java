package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static edu.csb.cs.cs185.jordanang.habittracker.MainActivity.habitList;

public class HabitOverview extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

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
        TextView total_tv = (TextView) findViewById(R.id.total_textview);
        TextView best_tv = (TextView) findViewById(R.id.bestStreak_textview);
        TextView currentStreak_tv = (TextView) findViewById(R.id.currentStreak_textview);
        GraphView graph = (GraphView) findViewById(R.id.graph);
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
        String habitTitle = currentItem.habitTitle;
        habitTitle = habitTitle.substring(0, habitTitle.length()).toLowerCase();
        String question = "Did you " + habitTitle + " today?";
        habitQuestion_tv.setText(question);

        //Setup days to repeat
        if(currentItem.someDayChosen() == false){
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

        //Setup month percentage
        int currentMonthPercentage = currentItem.getThisMonthsPercentage();
        String currentMonthPercentage_string = "" + currentMonthPercentage + "%";
        monthPercentage_tv.setText(currentMonthPercentage_string);

        //Setup progress bar
        progressBar.setProgress(currentMonthPercentage);

        //Set up graph
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(7);
        graph.getViewport().setMinX(-8);
        graph.getViewport().setMaxX(0);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("# weeks ago");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(-8, 1),
                new DataPoint(-7, 5),
                new DataPoint(-6, 3),
                new DataPoint(-5, 4),
                new DataPoint(-4, 1),
                new DataPoint(-3, 5),
                new DataPoint(-2, 3),
                new DataPoint(-1, 4),
                new DataPoint(0, 7)
        });
        series.setColor(Color.parseColor("#00bcd4"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        graph.addSeries(series);

        //----------------------------------------------------------------------------

    }
}
