package com.meowisthetime.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;




public class MainActivity extends AppCompatActivity {
    public static final String TASK_INDEX = "com.meowisthetime.todoapp.TASK_INDEX";
    public static final String TASK_NAME = "com.meowisthetime.todoapp.TASK_NAME";
    public static final String TASK_TEXT = "com.meowisthetime.todoapp.TASK_TEXT";
    public static final String TASK_DUE = "com.meowisthetime.todoapp.TASK_DUE";
    public static final String TASK_CATEGORY = "com.meowisthetime.todoapp.TASK_CATEGORY";
    public static final String TASK_TIME_DUE = "com.meowisthetime.todoapp.TASK_TIME_DUE";
    public static final String TASK_CATEGORYID = "com.meowisthetime.todoapp.TASK_CATEGORYID";




    private ListView taskList;
//    private ArrayList<Task> taskArray;
    String filename = "TodoItems";
    Gson gson = new Gson();
//    List<Task> taskListmeow = new ArrayList<>();

    private List<Category> categories = new ArrayList<>();
    private ArrayList<Object> allItems = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private int CategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskList = (ListView) findViewById(R.id.listView);

        // Create a file to save data to
        final File filesDir = this.getFilesDir();
        final File categoryFile = new File(filesDir + File.separator + filename);

        // If we have save data load it, else setup default notes
        if (categoryFile.exists()) {
            readFile(categoryFile);
        } else {
            setupTasks();
            writeFile();
        }

        // Create our listview and update our items array
        taskList = (ListView) findViewById(R.id.listView);
        updateAllItems();

        // Set our custom adapter to handle our notesList
        categoryAdapter = new CategoryAdapter(this, allItems);
        taskList.setAdapter(categoryAdapter);


        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allItems.get(position);
                Task task = (Task) allItems.get(position);
                Toast.makeText(MainActivity.this, "position" + position, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, AddTasksActivity.class);
                intent.putExtra(TASK_INDEX, position);
                intent.putExtra(TASK_NAME, task.getTaskName());
                intent.putExtra(TASK_DUE, task.getDueDate());
                intent.putExtra(TASK_TIME_DUE, task.getDueTime());
                intent.putExtra(TASK_TEXT, task.getComments());
//                intent.putExtra(TASK_CATEGORY, task.getCategory());
                intent.putExtra(TASK_CATEGORYID, task.getCategoryID());


                startActivityForResult(intent, 1);
            }
        });


        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete");
                alertBuilder.setMessage("You sure?");
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task task = (Task) allItems.get(position);
                        deleteFile(task.getTaskName());
                        categories.get(CategoryID).tasks.remove(task);
                        allItems.remove(position);
                        writeFile();
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });

    }

    public void OnClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, AddTasksActivity.class);
        intent.putExtra(TASK_NAME, "");
        intent.putExtra(TASK_CATEGORY, "");

        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int index = data.getIntExtra(TASK_INDEX, -1);
            // Create a new note from the data passed from our detail activity
            Task task = new Task(data.getStringExtra(TASK_NAME),
                    data.getStringExtra(TASK_DUE),
                    data.getStringExtra(TASK_TIME_DUE),
                    data.getStringExtra(TASK_CATEGORY),
                    data.getStringExtra(TASK_TEXT));
//                    , new Date(), Integer.parseInt(data.getStringExtra(TASK_CATEGORY)));
            switch (data.getStringExtra(TASK_CATEGORY)) {
                case "home":
                    CategoryID = 0;
                    break;
                case "work":
                    CategoryID = 1;
                    break;
                case "misc":
                    CategoryID = 2;
                    break;
            }
            if (index < 0 || index > categories.get(CategoryID).tasks.size() - 1) {
                categories.get(CategoryID).tasks.add(task);
                Toast.makeText(this, "length" +categories.get(CategoryID).tasks.size(), Toast.LENGTH_LONG).show();
            } else {
                Task oldtask = categories.get(CategoryID).tasks.get(index);
                categories.get(CategoryID).tasks.set(index, task);
                if (!oldtask.getTaskName().equals(task.getTaskName())) {
                    File oldFile = new File(this.getFilesDir(), oldtask.getTaskName());
                    File newFile = new File(this.getFilesDir(), task.getTaskName());
                    oldFile.renameTo(newFile);
                }
                // Add the new note to the defined category, write to files, update our adapter
                writeFile();
                updateAllItems();
                categoryAdapter.notifyDataSetChanged();
            }

//        if (resultCode == RESULT_OK) {
//            int index = data.getIntExtra(TASK_INDEX, -1);
//            Task task = new Task(data.getStringExtra(TASK_NAME),
//                    data.getStringExtra(TASK_DUE));
////                    data.getStringExtra("Due Time"),
////                    new Date(),
////                    new Date().getTime(),
////                    data.getStringExtra("Comments"));
//            if (index < 0 || index > taskArray.size() - 1) {
//                taskArray.add(task);
//            } else {
//                Task oldTask = taskArray.get(index);
//                taskArray.set(index, task);
//            }
//            writeFile();
//            taskArrayAdapter.updateAdapter(taskArray);
        }
    }



    private void setupTasks() {
        categories.add(new Category("Home", new ArrayList<Task>()));
        categories.add(new Category("Work", new ArrayList<Task>()));
        categories.add(new Category("Misc", new ArrayList<Task>()));

        for(int i = 0; i < categories.size(); i++) {
            categories.get(i).tasks.add(new Task("Test", "Test", "Test", "", "Test"));
            categories.get(i).tasks.add(new Task("Test", "Test", "Test", "", "Test"));
            categories.get(i).tasks.add(new Task("Test", "Test", "Test", "", "Test"));

//        File filesDir = this.getFilesDir();
//        File todoFile = new File(filesDir + File.separator + filename);
//        if (todoFile.exists()) {
//            readFile(todoFile);
//
//
//            for (Task task : taskListmeow) {
//                Log.d("To do read from file ", task.getTaskName());
//                taskArray.add(task);
//            }
//        } else {
//            taskArray.add(new Task("Walk the cat", "date"));
//            taskArray.add(new Task("Dance", "date"));
//            taskArray.add(new Task("Meow at the moon", "date"));
//            taskArray.add(new Task("Code Monkey is pissed", "date"));
//
//            writeFile();
        }
    }
    private void readFile(File todoFile) {
        FileInputStream inputStream = null;
        String todosText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {}
            todosText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Determine type of our collection
            Type collectionType = new TypeToken<List<Category>>(){}.getType();
            // Pull out our categories in a list
            List<Category> categoryList = gson.fromJson(todosText, collectionType);
            // Create a LinkedList that we can edit from our categories list and save it
            // to our global categories
            categories = new LinkedList(categoryList);


//            Task[] taskList = gson.fromJson(todosText, Task[].class);
//            taskListmeow = Arrays.asList(taskList);

        }
    }


    private void writeFile() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(categories);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {
            }
        }
    }
    private void updateAllItems() {
        allItems.clear();
        for(int i = 0; i < categories.size(); i++) {
            allItems.add(categories.get(i).getName());
            for(int j = 0; j < categories.get(i).tasks.size(); j++) {
                allItems.add(categories.get(i).tasks.get(j));
            }
        }
    }
}
