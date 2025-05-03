/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.comprog_finalproject;
import java.util.*;
import java.io.*;

public class ComProg_FinalProject {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static int taskCounter = 1;

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("\nTo Do List App");
            System.out.println("[1] View Entries");
            System.out.println("[2] Edit Entries");
            System.out.println("[3] Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": viewEntries(); break;
                case "2": editEntries(); break;
                case "3": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    public static void viewEntries() {
        System.out.println("\n---- VIEW ENTRIES ----");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("[1] View Task | [4] Return");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            System.out.print("Enter Task Number: ");
            int num = Integer.parseInt(sc.nextLine());
            viewTask(num);
        }
    }

    public static void viewTask(int num) {
        for (Task t : tasks) {
            if (t.id == num) {
                System.out.println("\n--- VIEWING TASK " + num + " ---");
                System.out.println("[" + t.dueDate + "] " + t.header);
                System.out.println(t.description);
                System.out.println("[1] Return");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public static void editEntries() throws IOException {
        System.out.println("\n---- EDIT ENTRIES ----");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("[1] Add Task | [2] Edit Task | [3] Remove Task/s | [7] Return");
        String choice = sc.nextLine();

        switch (choice) {
            case "1": addTask(); break;
        }
    }

    public static void addTask() throws IOException {
        System.out.println("\n---- ADD TASK ----");
        String group = chooseGroup();
        boolean completed = chooseStatus();
        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();
        System.out.print("Header: ");
        String header = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();

        Task newTask = new Task(taskCounter, group, completed, dueDate, header, description);
        tasks.add(newTask);

        File file = new File("task" + taskCounter + ".txt");
        FileWriter fw = new FileWriter(file);
        fw.write("ID: " + taskCounter + "\n");
        fw.write("Group: " + group + "\n");
        fw.write("Status: " + (completed ? "Completed" : "Incomplete") + "\n");
        fw.write("Due Date: " + dueDate + "\n");
        fw.write("Header: " + header + "\n");
        fw.write("Description: " + description + "\n");
        fw.close();

        System.out.println("Task saved to " + file.getName());
        taskCounter++;
    }

    public static String chooseGroup() {
        System.out.println("Group:\n[1] Work\n[2] School\n[3] House\n[4] Other");
        switch (sc.nextLine()) {
            case "1": return "Work";
            case "2": return "School";
            case "3": return "House";
            case "4": return "Other";
            default: return "Other";
        }
    }

    public static boolean chooseStatus() {
        System.out.println("Status:\n[1] Completed\n[2] Incomplete");
        return sc.nextLine().equals("1");
    }
}

