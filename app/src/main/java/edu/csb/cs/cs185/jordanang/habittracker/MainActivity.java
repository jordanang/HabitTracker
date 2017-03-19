package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<HabitItem> habitList= new ArrayList<HabitItem>();
    CustomAdapter customAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Definer floating action button and set listener
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

        //Set up list and connect adapter
        ListView listView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter(getApplicationContext(), habitList);
        listView.setAdapter(customAdapter);

        //Set up listener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), HabitOverview.class);
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", i);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.updateMenuSelection) {
            if(habitList.size() == 0){
                Toast.makeText(getApplicationContext(), "There are no habit items created", Toast.LENGTH_SHORT).show();
            } else {
                UpdateHabitsFragment newDialogFragment = new UpdateHabitsFragment();
                newDialogFragment.show(getSupportFragmentManager(), "updateDialog");
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
