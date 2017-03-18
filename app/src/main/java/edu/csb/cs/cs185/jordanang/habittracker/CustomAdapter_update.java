package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jordan Ang on 3/18/2017.
 */

public class CustomAdapter_update extends ArrayAdapter<HabitItem>{

    ArrayList<HabitItem> habitList;

    public CustomAdapter_update(@NonNull Context context, ArrayList<HabitItem> habitList) {
        super(context, 0, habitList);
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HabitItem item = habitList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.update_list_item, parent, false);
        }

        TextView habitTitle_tv = (TextView) convertView.findViewById(R.id.updateHabitTextView);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.todaysDone_checkbox);

        habitTitle_tv.setText(item.habitTitle);

        if(item.completedHabitToday == true){
            checkBox.setChecked(true);
        }

        return convertView;
    }
}
