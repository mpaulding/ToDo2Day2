package edu.orangecoastcollege.cs273.mpaulding.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;
    private ListView taskListView;
    private EditText taskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EXPERIMENT 1: CREATE THE DATABASE
        //this.deleteDatabase(DBHelper.DATABASE_NAME);
        database = new DBHelper(this);

        //database.addTask(new Task("Study for CS 273 Midterm", 1));
        // database.addTask(new Task("Play more League of Legends", 0));
        //database.addTask(new Task("Master the FragmentManager", 0));

        /*
        taskList = database.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);
        taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(taskListAdapter);
        */

        taskEditText = (EditText) findViewById(R.id.taskEditText);

    }

    @Override
    public void onResume() {
        super.onResume();
        taskList = database.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);
        taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(taskListAdapter);
        taskListView.setAdapter(taskListAdapter);
    }

    public void addTask(View view) {
        String description = taskEditText.getText().toString();
        if (description.isEmpty())
            Toast.makeText(this, "Task description cannot be empty.", Toast.LENGTH_SHORT).show();
        else {
            Task newTask = new Task(description, 0);
            database.addTask(newTask);
            taskEditText.setText("");

            // Notify the adapter of changes:
            taskListAdapter.add(newTask);
            taskListAdapter.notifyDataSetChanged();
        }
    }

    public void clearAllTasks(View view) {
        taskList.clear();
        database.deleteAllTasks();
        taskListAdapter.notifyDataSetChanged();
    }


    public void changeTaskStatus(View view) {
        CheckBox selectedCheckBox = (CheckBox) view;
        Task selectedTask = (Task) selectedCheckBox.getTag();
        selectedTask.setIsDone(selectedCheckBox.isChecked() ? 1 : 0);
        database.editTask(selectedTask);
    }
}
