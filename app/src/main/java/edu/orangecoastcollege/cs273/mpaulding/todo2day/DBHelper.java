package edu.orangecoastcollege.cs273.mpaulding.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDo2Day";
    private static final String DATABASE_TABLE = "tasks";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_TASK_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";

    private int taskCount;

    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
        taskCount = 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE


    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        taskCount++;
        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        values.put(KEY_TASK_ID, taskCount);

        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        values.put(FIELD_DESCRIPTION, task.getDescription()); // task name

        //ADD KEY-VALUE PAIR INFORMATION FOR IS_DONE
        //  0- NOT DONE, 1 - IS DONE
        values.put(FIELD_IS_DONE, task.getIsDone());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public void editTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_DESCRIPTION, task.getDescription());
        values.put(FIELD_IS_DONE, task.getIsDone());

        db.update(DATABASE_TABLE, values, KEY_TASK_ID + " = ?",
                new String[]{
                        String.valueOf(task.getId())
                });
        db.close();
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_TASK_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                KEY_TASK_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2));
        db.close();
        return task;
    }

    public void deleteTask(Task task){
        SQLiteDatabase database = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        database.delete(DATABASE_TABLE, KEY_TASK_ID + " = ?",
                new String[]
                        {String.valueOf(task.getId())});
        database.close();
    }

    public int getTaskCount() {
        return taskCount;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        String queryList = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryList, null);



        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()){
            do {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }
}
