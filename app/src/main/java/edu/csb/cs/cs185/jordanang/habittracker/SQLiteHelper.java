package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jordan Ang on 9/30/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "habit_database.db";

    private static final String CREATE_TABLE_COMPLETED_HABITS =
            "CREATE TABLE completed_habits ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "habit_id INTEGER, "
            + "date TEXT);";

    private static final String CREATE_TABLE_HABITS =
            "CREATE TABLE habit ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT, "
            + "days TEXT, "
            + "hour INTEGER, "
            + "min INTEGER, "
            + "remind TEXT);";


    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HABITS);
        db.execSQL(CREATE_TABLE_COMPLETED_HABITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS habit");
        db.execSQL("DROP TABLE IF EXISTS completed_habits");
        onCreate(db);
        return;
    }

    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM habit");
        db.execSQL("DELETE FROM completed_habits");
        db.close();
    }

    public void deleteHabit(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String lowerCaseName = name.toLowerCase();
        String query = "SELECT * FROM habit WHERE name = '" + lowerCaseName + "';";
        Log.d("SQLite query", query);
        Cursor res = db.rawQuery(query, null);
        if (res != null && res.getCount() > 0)
        {
            res.moveToFirst();
            int habit_id = res.getInt(res.getColumnIndex("id"));
            query = "DELETE FROM completed_habits WHERE habit_id is '" + habit_id + "';";
            Log.d("SQLite query", query);
            db.execSQL(query);
        }

        query = "DELETE FROM habit WHERE name is '" + lowerCaseName + "';";
        db.execSQL(query);
        Log.d("SQLite query", query);
        viewDb();
    }

    public boolean checkHabitExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String lowerCaseName = name.toLowerCase();
        String query = "SELECT * FROM habit WHERE name = '" + lowerCaseName + "';";
        Cursor res = db.rawQuery(query, null);
        if(res.getCount() == 0)
        {
            db.close();
            return false;
        }
        else
        {
            db.close();
            return true;
        }
    }

    public void addHabit(String name, String days, int hour, int min) {
        SQLiteDatabase db = this.getWritableDatabase();
        String lowerCaseName = name.toLowerCase();
        String query = "INSERT INTO habit ('name', 'days', 'hour', 'min') VALUES ("
                + "'" + lowerCaseName + "',"
                + "'" + days + "',"
                + "'" + hour + "',"
                + "'" + min + "');";
        db.execSQL(query);
        db.close();

        Log.d("SQLite query", query);
        viewDb();
    }

    public void updateHabit(String currName, String name, String days, int hour, int min) {
        SQLiteDatabase db = this.getWritableDatabase();
        String lowerCaseName = name.toLowerCase();
        String query = "UPDATE habit SET "
                + "'name' = " + "'" + lowerCaseName + "', "
                + "'days' = " + "'" + days + "', "
                + "'hour' = " + "'" + hour + "', "
                + "'min' = " + "'" + min + "' "
                + "WHERE name = '" + currName + "'";
        db.execSQL(query);
        db.close();

        Log.d("SQlite query", query);
        viewDb();
    }

    public String encodeDays(boolean[] checked) {
        String days = "";

        char[] daysList = {'u', 'm', 't', 'w', 'r', 'f', 's'};

        for(int i=0; i<checked.length; i++)
        {
            if(checked[i]){
                days += daysList[i] + ",";
            }
        }

        return days;
    }

    public boolean[] decodeDays(String days) {
        boolean[] checked = new boolean[7];
        Arrays.fill(checked, false);

        String[] daysList = {"u", "m", "t", "w", "r", "f", "s"};

        String[] checkedDays = days.split(",");

        for(int i=0; i<checkedDays.length; i++)
        {
            for(int j=0; j<daysList.length; j++){
                if(daysList[j].equals(checkedDays[i]))
                {
                    checked[j] = true;
                }
            }
        }

        return checked;
    }

    public ArrayList<HabitItem> getHabitList()
    {
        ArrayList<HabitItem> habits = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM habit";
        Cursor res = db.rawQuery(query,null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            String name = res.getString(res.getColumnIndex("name"));

            String days = res.getString(res.getColumnIndex("days"));
            boolean[] checked = decodeDays(days);

            int hour = res.getInt(res.getColumnIndex("hour"));

            int min = res.getInt(res.getColumnIndex("min"));

            HabitItem habit = new HabitItem(name, checked, hour, min);

            habits.add(habit);

            res.moveToNext();
        }

        db.close();

        return habits;

    }

    public void addCompletedHabit(String name, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String lowerCaseName = name.toLowerCase();
        String getHabit_query = "SELECT id FROM habit WHERE name='" + lowerCaseName + "';";
        Cursor res = db.rawQuery(getHabit_query, null);
        res.moveToFirst();
        if(res.isAfterLast() == false) {
            String habit_id = res.getString(res.getColumnIndex("id"));
            String addCompleted_query = "INSERT INTO completed_habits ('habit_id', 'date') VALUES ("
                    + habit_id + ", "
                    + "'" + date + "');";
            db.execSQL(addCompleted_query);

            Log.d("SQLite query", addCompleted_query);
            viewDb();

            db.close();
        }
    }

    public void viewDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM completed_habits", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            Log.d("SQLite view completed",
                    "id: " + res.getString(res.getColumnIndex("id"))
                    + " | habit_id: " + res.getString(res.getColumnIndex("habit_id"))
                    + " | date: " + res.getString(res.getColumnIndex("date"))
                    );
            res.moveToNext();
        }

        res = db.rawQuery("SELECT * FROM habit", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            Log.d("SQLite view habit",
                    "id: " + res.getString(res.getColumnIndex("id"))
                    + " | name: " + res.getString(res.getColumnIndex("name"))
                    + " | days: " + res.getString(res.getColumnIndex("days"))
                    + " | hour: " + res.getString(res.getColumnIndex("hour"))
                    + " | min: " + res.getString(res.getColumnIndex("min"))
                    );
            res.moveToNext();
        }

        db.close();

        return;
    }
}
