package edu.csb.cs.cs185.jordanang.habittracker;

/**
 * Created by Jordan Ang on 3/12/2017.
 */

public class HabitItem {

    String habitTitle;
    boolean[] daysToRepeat;
    int hourToRepeat;
    int minuteToRepeat;

    int currentStreak;
    int bestStreak;

    HabitItem(){
        //Default Constructor
    }

    HabitItem(String title, boolean[] checked, int hour, int minute){
        habitTitle = title;
        daysToRepeat = checked;
        hourToRepeat = hour;
        minuteToRepeat = minute;

        currentStreak = 0;
        bestStreak = 0;
    }
}
