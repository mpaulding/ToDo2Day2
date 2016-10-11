package edu.orangecoastcollege.cs273.mpaulding.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EXPERIMENT 1: CREATE THE DATABASE

        DBHelper database = new DBHelper(this);

        //              ADD FIVE TASK ITEMS TO THE DATABASE
        database.addTask(new Task(
                1, "Read Hamlet", 1));
        database.addTask(new Task(
                2, "Study for exam", 1));
        database.addTask(new Task(
                3, "Call Andy and Sam", 0));
        database.addTask(new Task(
                4, "Create newsletter", 1));
        database.addTask(new Task(
                5, "Buy a dog", 0));

        //            DISPLAY ALL THE TASK ITEMS IN THE TABLE
        String taskItemList = "\n";
        ArrayList<Task> taskList = database.getAllTasks();
        for (int i = 0; i < database.getTaskCount(); i++) {
            Task task = taskList.get(i);
            taskItemList += "\n" + task.toString();
        }
        Log.i("DATABASE RECORDS", taskItemList);

        // EXPERIMENT 2: MODIFY A RECORD
        database.editTask(new Task(
                1, "Read newspaper", 1));

        //EXPERIMENT 3: DISPLAY A SPECIFIC RECORD
        Task anItem = database.getTask(2);
        Log.i("DATABASE RECORDS", anItem.toString());

        //EXPERIMENT 4: DELETE A RECORD
        database.deleteTask(new Task(
                15, "Buy a dog", 0));

        //              DISPLAY ALL THE TASK ITEMS IN THE TABLE
        taskItemList = "\n";
        taskList = database.getAllTasks();
        for (int i = 0; i < database.getTaskCount(); i++) {
            Task task = taskList.get(i);
            taskItemList += "\n" + task.getDescription() + "\t" +
                    task.getIsDone();
        }
        Log.i("DATABASE RECORDS", taskItemList);
    }
}
