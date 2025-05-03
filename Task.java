/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comprog_finalproject;

/**
 *
 * @author Fritz Ver
 */
public class Task {
    int id;
    String group;
    boolean isCompleted;
    String dueDate;  // Format: YYYY-MM-DD
    String header;
    String description;

    public Task(int id, String group, boolean isCompleted, String dueDate, String header, String description) {
        this.id = id;
        this.group = group;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.header = header;
        this.description = description;
    }

    public String getStatusIcon() {
        return isCompleted ? "✅" : "❌";
    }

    public String toString() {
        return String.format("%d %s %s %s %s", id, group, getStatusIcon(), dueDate, header);
    }
}
