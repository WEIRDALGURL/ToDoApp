package com.meowisthetime.todoapp;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by sheamaynard on 10/24/16.
 */

public class Task implements Comparable<Task> {
    @SerializedName("name")
    private String taskName;

    @SerializedName("category")
    private String category;

    @SerializedName("due date")
    private String dueDate;

    @SerializedName("due time")
    private String dueTime;

    @SerializedName("mod date")
    private Date Date;

    @SerializedName("comments")
    private String comments;

    @SerializedName("categoryID")
    private String categoryID;


//    public Task(String taskName, String category, String dueDate, String dueTime, Date modDate, String comments) {
//        this.taskName = taskName;
//        this.category = category;
//        this.dueDate = dueDate;
//        this.dueTime = dueTime;
//        this.Date = Date;
//        this.comments = comments;
//    }

    public Task(String taskName, String dueDate, String dueTime, String categoryID, String comments) {
        this.taskName = taskName;
        this.category = category;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.Date = new Date();
        this.comments = comments;
        this.categoryID = categoryID;

    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date modDate) {
        this.Date = modDate;
    }


    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

@Override
    public int compareTo(Task another) {
    return another.getCategory().compareToIgnoreCase(getCategory());


//        return another.getDate().compareTo(getDate()); //puts new notes first
//        return another.getDate().compareTo(another.getDate()); // my attempt
//        return getDueDate().compareTo(another.getDueDate());    // Actual answer
    }
}

