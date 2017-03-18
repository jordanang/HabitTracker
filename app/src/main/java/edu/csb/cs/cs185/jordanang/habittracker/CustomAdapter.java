package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        ImageView repeatIndicatorThemed = (ImageView) convertView.findViewById(R.id.repeatIndicatorThemed);
        ImageView repeatIndicatorGrey = (ImageView) convertView.findViewById(R.id.repeatIndicatorGrey);
        TextView habitTextView = (TextView) convertView.findViewById(R.id.habitTextView);
        TextView bestTextView = (TextView) convertView.findViewById(R.id.bestStreak_textview);
        TextView currentTextView = (TextView) convertView.findViewById(R.id.currentStreak_textview);
        TextView totalTextView = (TextView) convertView.findViewById(R.id.total_textview);
        TextView monthTextView = (TextView) convertView.findViewById(R.id.monthPercentage_textview);

        if(item.someDayChosen() == true){
            repeatIndicatorThemed.setVisibility(View.VISIBLE);
        } else {
            repeatIndicatorGrey.setVisibility(View.VISIBLE);
        }

        //Setup habit title
        habitTextView.setText(item.habitTitle);

        //Setup current streak
        String currentStreak_string = "" + item.currentStreak;
        currentTextView.setText(currentStreak_string);

        //Setup best streak
        String bestStreak_string = "" + item.bestStreak;
        bestTextView.setText(bestStreak_string);

        //Setup total
        String total_string = "" + item.total;
        totalTextView.setText(total_string);

        //Setup month percentage
        int currentMonthPercentage = item.getThisMonthsPercentage();
        String currentMonthPercentage_string = "" + currentMonthPercentage + "%";
        monthTextView.setText(currentMonthPercentage_string);

        return convertView;
    }
}
