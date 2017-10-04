package edu.csb.cs.cs185.jordanang.habittracker;

/**
 * Created by Jordan Ang on 3/12/2017.
 */

public class HabitItem {

    String habitTitle;
    boolean[] daysToRepeat;
    int hourToRepeat;
    int minuteToRepeat;

    boolean completedHabitToday;

    HabitItem(String title, boolean[] checked, int hour, int minute){
        habitTitle = title;
        daysToRepeat = checked;
        hourToRepeat = hour;
        minuteToRepeat = minute;

        completedHabitToday = false;
    }

    HabitItem(String title, boolean[] checked, int hour, int minute, boolean completed){
        habitTitle = title;
        daysToRepeat = checked;
        hourToRepeat = hour;
        minuteToRepeat = minute;

        completedHabitToday = completed;
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

}
