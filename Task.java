/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

public class Task extends Entry {
    private String group;
    private boolean isCompleted;
    private String dueDate;

    public Task(int id, String group, boolean isCompleted, String dueDate, String header, String description) {
        super(id, header, description);
        this.group = group;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
    }

    public String getStatusIcon() {
        return isCompleted ? "/" : "X";
    }

    public String getDetails() {
        return String.format("Group: %s\nStatus: %s\nDue Date: %s\nHeader: %s\nDescription: %s",
                group, getStatusIcon(), dueDate, header, description);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s", id, group, getStatusIcon(), dueDate, header);
    }
}
