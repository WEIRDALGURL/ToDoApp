package com.meowisthetime.todoapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheamaynard on 10/26/16.
 */

public class Category {
    @SerializedName("name")
    private String name;

    @SerializedName("tasks")
    public ArrayList<Task> tasks;

    public Category(String name, ArrayList<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}