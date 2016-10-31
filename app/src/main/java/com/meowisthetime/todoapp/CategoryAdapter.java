package com.meowisthetime.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by lrterry on 10/27/16.
 */

public class CategoryAdapter extends BaseAdapter {
    private ArrayList<Object> items;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat formatter;
    boolean[] checkBoxState;


    // Define ints to determine the type of view we want to create
    private static final int TYPE_TASK = 0;
    private static final int TYPE_CATEGORY = 1;

    // Construct our custom adapter
    public CategoryAdapter(Context context, ArrayList<Object> object) {
        this.items = object;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new SimpleDateFormat("MM/dd/yyyy \n h:mm a", Locale.getDefault());
        checkBoxState = new boolean[items.size()];
    }

    // Get the number of views we will need to inflate. Should be the size of our items array
    @Override
    public int getCount() {
        return items.size();
    }

    // Get the position in our array we are at
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Get the position of our item in the array
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    // Determine the amount of separate views our adapter will need to handle
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // Determine the type of view we will need to use for the position in our item array
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Task) {
            return TYPE_TASK;
        }

        return TYPE_CATEGORY;
    }

    // Enable or disabled the ability to interact with the view
    @Override
    public boolean isEnabled(int position) {

        return true;
    }

    // Determine the type of view we are creating, then insert the necessary info from our
    // items array
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int type = getItemViewType(position);
        if (convertView == null) {
            if (type == TYPE_TASK) {
                convertView = layoutInflater.inflate(R.layout.task_list_item, parent, false);
            } else if (type == TYPE_CATEGORY) {
                convertView = layoutInflater.inflate(R.layout.category_list_item, parent, false);

            }
        }

        if (type == TYPE_TASK) {
            Task task = (Task) getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.task_name);
            TextView date = (TextView) convertView.findViewById(R.id.date_due);
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            TextView modDate = (TextView) convertView.findViewById(R.id.date_mod);
            name.setText(task.getTaskName());
            modDate.setText(formatter.format(task.getModDate()));
            date.setText(task.getDueDate());
            checkbox.setChecked(checkBoxState[position]);


            //for managing the state of the boolean
            //array according to the state of the
            //CheckBox

            checkbox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        checkBoxState[position] = true;
                    } else {
                        checkBoxState[position] = false;
                    }
                }
            });
                } else if (type == TYPE_CATEGORY) {
            String categoryName = (String) getItem(position);
            TextView category = (TextView) convertView.findViewById(R.id.category);
            category.setText(categoryName);
        }
        return convertView;
    }
}
