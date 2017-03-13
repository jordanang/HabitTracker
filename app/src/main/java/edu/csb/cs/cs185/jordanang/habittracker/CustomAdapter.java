package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jordan Ang on 3/13/2017.
 */

public class CustomAdapter extends ArrayAdapter<HabitItem> {

    private ArrayList<HabitItem> habits;

    public CustomAdapter(Context context, ArrayList<HabitItem> habits){
        super(context, 0, habits);
        this.habits = habits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HabitItem item = habits.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView habitTextView = (TextView) convertView.findViewById(R.id.habitTextView);
        TextView bestTextView = (TextView) convertView.findViewById(R.id.bestTextView);
        TextView currentTextView = (TextView) convertView.findViewById(R.id.currentTextView);
        TextView monthTextView = (TextView) convertView.findViewById(R.id.monthTextView);

        habitTextView.setText(item.habitTitle);
        bestTextView.setText("Best: " + item.bestStreak);
        currentTextView.setText("Current " + item.currentStreak);

        //TODO: implement month percentage
        monthTextView.setText("Month 0%");
        //int monthlyPercentage;
        //monthTextView.setText("Month: " + monthlyPercentage + "%")

        return convertView;
    }
}
