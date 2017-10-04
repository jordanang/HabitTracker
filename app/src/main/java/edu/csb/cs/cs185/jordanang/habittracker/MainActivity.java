package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<HabitItem> habitList= new ArrayList<HabitItem>();
    CustomAdapter customAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Define floating action button and set listener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(habitList.size() < 6) {
                    CreateHabitFragment newDialogFragment = new CreateHabitFragment();
                    newDialogFragment.show(getSupportFragmentManager(), "dialog");
                } else {
                    Toast.makeText(getApplicationContext(), "You can only have 6 habits!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Set up update todays habit button
        Button updateTodaysHabitButton = (Button) findViewById(R.id.updateTodaysHabitButton);

        updateTodaysHabitButton.setText("LIST OF HABITS");
        /*//Set up listener for update today's button
        updateTodaysHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(habitList.size() == 0){
                    Toast.makeText(getApplicationContext(), "There are no habit items created", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateHabitsFragment newDialogFragment = new UpdateHabitsFragment();
                    newDialogFragment.show(getSupportFragmentManager(), "updateDialog");
                }
            }
        });*/

        //Grab habits from database
        SQLiteHelper  sqLiteHelper = new SQLiteHelper(getApplicationContext());
        try {
            habitList = sqLiteHelper.getHabitList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set up list and connect adapter
        ListView listView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter(getApplicationContext(), habitList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), HabitOverview.class);
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
