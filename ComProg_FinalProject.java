package com.mycompany.comprog_finalproject;
import java.util.*;
import java.io.*;

public class ComProg_FinalProject {
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static String currentUsername = "";
    public static int taskCounter = 1;

    public static void main(String[] args) throws IOException {
        login();  
        loadTasks();  
        
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

    public static void login() {
        System.out.print("Enter username: ");
        currentUsername = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.println("Login successful!");
    }

    public static void loadTasks() throws IOException {
        File userFolder = new File(currentUsername);
        if (!userFolder.exists()) {
            userFolder.mkdir();  
        }

        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return;

        for (File file : files) {
            Scanner reader = new Scanner(file);
            int id = Integer.parseInt(reader.nextLine());
            String group = reader.nextLine();
            boolean completed = Boolean.parseBoolean(reader.nextLine());
            String dueDate = reader.nextLine();
            String header = reader.nextLine();
            String description = reader.nextLine();
            tasks.add(new Task(id, group, completed, dueDate, header, description));
            if (id >= taskCounter) taskCounter = id + 1;
            reader.close();
        }
    }

    public static void viewEntries() {
        System.out.println("\n---- VIEW ENTRIES ----");
        if (tasks.size() == 0) {
            System.out.println("No tasks available.");
            return;
        }
        System.out.printf("%-5s %-10s %-10s %-15s %-20s\n", "ID", "Group", "Status", "Due Date", "Header");
        System.out.println("----------------------------------------------------------");
        for (Task task : tasks) {
            System.out.printf("%-5d %-10s %-10s %-15s %-20s\n",
                task.id, task.group, task.getStatusIcon(), task.dueDate, task.header);
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
        boolean completed = false;
        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();
        System.out.print("Header: ");
        String header = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();
        Task newTask = new Task(taskCounter, group, completed, dueDate, header, description);
        tasks.add(newTask);

        File userFolder = new File(currentUsername);
        File file = new File(userFolder, "task" + taskCounter + ".txt");
        FileWriter fw = new FileWriter(file);
        fw.write(taskCounter + "\n");
        fw.write(group + "\n");
        fw.write(completed + "\n");
        fw.write(dueDate + "\n");
        fw.write(header + "\n");
        fw.write(description + "\n");
        fw.close();

        System.out.println("Task saved!");
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
