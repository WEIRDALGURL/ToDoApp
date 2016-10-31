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
    private Date modDate;

    @SerializedName("comments")
    private String comments;

    @SerializedName("categoryID")
    private int categoryID;



    public Task(String taskName, String dueDate, String dueTime, Date modDate, String comments, String category, int categoryID) {
        this.taskName = taskName;
        this.category = category;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.modDate = modDate;
        this.comments = comments;
        this.categoryID = categoryID;

    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
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

    public int compareTo(Task another) {
    return another.getDueDate().compareTo(getDueDate());


    }
}

