package edu.orangecoastcollege.cs273.mpaulding.todo2day;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mpaulding on 10/11/2016.
 */

public class DBTest extends SQLiteOpenHelper {


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBTest(Context context)
    {
        super(context, "TEST", null, 1);
    }
}
