package edu.csb.cs.cs185.jordanang.habittracker;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;

import java.util.Random;

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
    int total;
    int monthPercentage;

    boolean completedHabitToday;

    HabitItem(){
        //Default Constructor
    }

    HabitItem(String title, boolean[] checked, int hour, int minute){
        habitTitle = title;
        daysToRepeat = checked;
        hourToRepeat = hour;
        minuteToRepeat = minute;

        Random rand = new Random();

        currentStreak = rand.nextInt(14) + 1;
        bestStreak = 14;
        total = rand.nextInt(30) + 20;
        monthPercentage = (currentStreak*100)/30 ;

        completedHabitToday = false;
    }

    void complete(){
            currentStreak++;
            total++;

            if(currentStreak > bestStreak){
                bestStreak = currentStreak;
            }

            if(monthPercentage < 97){
                monthPercentage = monthPercentage + 3;
            } else {
                monthPercentage = 100;
            }

            completedHabitToday = true;
    }

    void uncomplete(){
            if (currentStreak > 0){
                currentStreak--;
            }

            if(total > 0){
                total--;
            }

            if(currentStreak > bestStreak){
                bestStreak = currentStreak;
            }

            if(monthPercentage >= 3){
                monthPercentage = monthPercentage - 3;
            } else {
                monthPercentage = 0;
            }

            completedHabitToday = false;
    }

    boolean someDayChosen(){
        boolean someDayChosen = false;
        for(int i=0; i<7; i++){
            if(daysToRepeat[i] == true){
                someDayChosen = true;
            }
        }
        return someDayChosen;
    }

    String createTimeString(){
        int repeatHour = hourToRepeat;
        int repeatMinute = minuteToRepeat;

        String hourString;
        String minuteString;
        String AMPM;

        if(repeatHour == 0){
            hourString = "12";
            AMPM = "AM";
        } else if(repeatHour == 12) {
            hourString = "12";
            AMPM = "PM";
        } else if(repeatHour > 12){
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

        String complete;

        complete = hourString + ":" + minuteString + " " + AMPM;

        return complete;
    }

    String createRepeatDaysString(){
        String days = "";
        String[] dayAcronym = {"SU", "M", "T", "W", "R", "F", "SA"};

        for(int i = 0; i < 7; i++){
            if(daysToRepeat[i] == true){
                days = days + dayAcronym[i];
                if(i < 6 ){
                    days = days + " ";
                }
            }
        }

        return days;
    }

    @TargetApi(Build.VERSION_CODES.N)
    int getThisMonthsPercentage(){
        //TODO: Setup percentage of month
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int numDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return monthPercentage;
    }
}
