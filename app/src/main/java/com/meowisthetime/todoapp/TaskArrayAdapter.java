package com.meowisthetime.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sheamaynard on 10/25/16.
 */

public class TaskArrayAdapter extends ArrayAdapter<Task> {
    private int resource;
    private ArrayList<Task> tasks;
    private LayoutInflater layoutInflater;

    // constructor for custom adapter, takes context adapter is used in, the resource xml id that is used
    // to contain the data, and the list of objects it is pulling from
    public TaskArrayAdapter(Context context, int resource, ArrayList<Task> object) {
        super(context, resource, object);
        this.resource = resource;
        this.tasks = object;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // sets and creates the view for each list view row
    public View getView(int position, View currentView, ViewGroup parent) {
        // define a layout inflater to create the interface for each table row
        View taskRow = layoutInflater.inflate(resource, parent, false);

        // define variables for our custom text views
        TextView taskName = (TextView)taskRow.findViewById(R.id.task_name);

        // grab the object from our array and set each text views value from its properties
        Task task = tasks.get(position);
        taskName.setText(task.getTaskName());

        // return the newly created row
        return taskRow;
    }

    public void updateAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
        super.notifyDataSetChanged();
    }
}
