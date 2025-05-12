/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalproject;
import java.util.*;
import java.io.*;

/**
 *
 * @author Fritz Ver
 */
public class FinalProject {

    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static String currentUsername = "";
    public static int taskCounter = 1;

    public static void main(String[] args) throws IOException {
        login();
        loadTasks();
        sortTasks();

        while (true) {
            System.out.println("\nTo Do List App");

            if (tasks.size() == 0) {
                System.out.println("No task yet, you are doing great :)");
            } else {
                Task mostUrgent = tasks.get(0);
                System.out.println("MOST URGENT TASK!!: \"" + mostUrgent.getHeader() + "\" (Due: " + mostUrgent.getDueDate() + ")");
            }

            System.out.println("[1] View Entries");
            System.out.println("[2] Edit Entries");
            System.out.println("[3] Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewEntries();
                    break;
                case "2":
                    editEntries();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void login() throws IOException {
        while (true) {
            System.out.println("---- LABORER'S TO-DO-LIST APPLICATION ----");
            System.out.println("[1] Login | [2] Register");
            String option = sc.nextLine();

            if (option.equals("2")) {
                while (true) {
                    System.out.print("Enter new username: ");
                    currentUsername = sc.nextLine();
                    File userFolder = new File(currentUsername);
                    if (userFolder.exists()) {
                        System.out.println("Username already exists. Try another.");
                    } else {
                        userFolder.mkdir();
                        System.out.print("Enter password: ");
                        String password = sc.nextLine();

                        FileWriter writer = new FileWriter(new File(userFolder, "password.txt"));
                        writer.write(password);
                        writer.close();

                        System.out.println("Registration successful! You are now logged in as " + currentUsername);
                        break;
                    }
                }
            } else if (option.equals("1")) {
                while (true) {
                    System.out.print("Enter username: ");
                    currentUsername = sc.nextLine();
                    File userFolder = new File(currentUsername);
                    if (!userFolder.exists()) {
                        System.out.println("Username not found. Try again.");
                        break;
                    } else {
                        System.out.print("Enter password: ");
                        String password = sc.nextLine();

                        File passFile = new File(userFolder, "password.txt");
                        Scanner passReader = new Scanner(passFile);
                        String storedPass = passReader.nextLine();
                        passReader.close();

                        if (password.equals(storedPass)) {
                            System.out.println("Login successful!");
                            return;
                        } else {
                            System.out.println("Incorrect password.");
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void loadTasks() throws IOException {
        File userFolder = new File(currentUsername);
        if (!userFolder.exists()) {
            userFolder.mkdir();
        }

        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("password.txt"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            Scanner reader = new Scanner(file);
            int id = Integer.parseInt(reader.nextLine());
            String group = reader.nextLine();
            boolean completed = Boolean.parseBoolean(reader.nextLine());
            String dueDate = reader.nextLine();
            String header = reader.nextLine();
            String description = reader.nextLine();
            tasks.add(new Task(id, group, completed, dueDate, header, description));
            if (id >= taskCounter) {
                taskCounter = id + 1;
            }
            reader.close();
        }
    }

    public static void sortTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                Task task1 = tasks.get(i);
                Task task2 = tasks.get(j);

                if (task1.isIsCompleted() != task2.isIsCompleted()) {
                    if (task1.isIsCompleted()) {
                        tasks.set(i, task2);
                        tasks.set(j, task1);
                    }
                } else {
                    if (task1.getDueDate().compareTo(task2.getDueDate()) > 0) {
                        tasks.set(i, task2);
                        tasks.set(j, task1);
                    }
                }
            }
        }
    }

    public static void viewEntries() {
        sortTasks();

        System.out.printf("%-9s %-10s %-10s %-15s %-20s\n", "List No.", "Group", "Status", "Due Date", "Header");
        System.out.println("--------------------------------------------------------------------------");
        if (tasks.size() == 0) {
            System.out.println("No tasks available.");
        } else {
            int listNumber = 1;
            for (Task task : tasks) {
                System.out.printf("%-9d %-10s %-10s %-15s %-20s\n",
                        listNumber,
                        task.getGroup(),
                        task.getStatusIcon(),
                        task.getDueDate(),
                        task.getHeader().length() > 17 ? task.getHeader().substring(0, 17) + "..." : task.getHeader()
                );
                listNumber++;
            }
        }

        System.out.println("--------------------------------------------------------------------------");
        String choice = "";
        while (!choice.equals("1") && !choice.equals("4")) {
            System.out.println("[1] View Task | [4] Return");
            System.out.print("Choice: ");
            choice = sc.nextLine();

            if (!choice.equals("1") && !choice.equals("4")) {
                System.out.println("Invalid choice. Please enter 1 or 4.");
            }
        }

        if (choice.equals("1")) {
            System.out.print("Enter Task Number: ");
            String input = sc.nextLine();
            int num = Integer.parseInt(input);
            viewTask(num);
        }
    }

    public static void viewTask(int num) {
        if (num <= 0 || num > tasks.size()) {
            System.out.println("Task not found.");
            return;
        }

        Task t = tasks.get(num - 1);
        System.out.println("\n--- VIEWING TASK " + num + " ---");
        System.out.println("[" + t.getDueDate() + "] " + t.getHeader());
        System.out.println(t.getDescription());
        System.out.println("[1] Return");
        sc.nextLine();
    }

    public static void editEntries() throws IOException {
        sortTasks();

        System.out.printf("%-9s %-10s %-10s %-15s %-20s\n", "List No.", "Group", "Status", "Due Date", "Header");
        System.out.println("--------------------------------------------------------------------------");
        if (tasks.size() == 0) {
            System.out.println("No tasks available.");
        } else {
            int listNumber = 1;
            for (Task task : tasks) {
                System.out.printf("%-9d %-10s %-10s %-15s %-20s\n",
                        listNumber,
                        task.getGroup(),
                        task.getStatusIcon(),
                        task.getDueDate(),
                        task.getHeader().length() > 17 ? task.getHeader().substring(0, 17) + "..." : task.getHeader()
                );
                listNumber++;
            }
        }
        System.out.println("--------------------------------------------------------------------------");
        String choice = "";
        while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("7")) {
            System.out.println("[1] Add Task | [2] Edit Task | [3] Remove Task/s | [7] Return");
            System.out.print("Choice: ");
            choice = sc.nextLine();

            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("7")) {
                System.out.println("Invalid choice.");
            }
        }

        switch (choice) {
            case "1":
                addTask();
                break;
            case "2":
                editTask();
                break;
            case "3":
                removeTask();
                break;
            case "7":
                break;
        }
    }

    public static void editTask() throws IOException {
        System.out.print("Enter Task Number (List no.) to Edit: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        Task task = tasks.get(index);
        System.out.println("\n-- Editing Task: " + task.getHeader());

        System.out.print("Enter new header (leave blank to keep current): ");
        String newHeader = sc.nextLine();

        boolean hasHeader = false;
        for (int i = 0; i < newHeader.length(); i++) {
            if (newHeader.charAt(i) != ' ') {
                hasHeader = true;
                break;
            }
        }
        if (hasHeader) {
            task.setHeader(newHeader);
        }

        System.out.print("Enter new due date (yyyy-mm-dd, leave blank to keep current): ");
        String newDate = sc.nextLine();
        boolean hasDate = false;
        for (int i = 0; i < newDate.length(); i++) {
            if (newDate.charAt(i) != ' ') {
                hasDate = true;
                break;
            }
        }
        if (hasDate) {
            task.setDueDate(newDate);
        }

        System.out.print("Enter new description (leave blank to keep current): ");
        String newDesc = sc.nextLine();
        boolean hasDesc = false;
        for (int i = 0; i < newDesc.length(); i++) {
            if (newDesc.charAt(i) != ' ') {
                hasDesc = true;
                break;
            }
        }
        if (hasDesc) {
            task.setDescription(newDesc);
        }

        System.out.print("Mark as completed? (yes/no): ");
        String status = sc.nextLine();
        if (status.equalsIgnoreCase("yes")) {
            task.setIsCompleted(true);
        } else if (status.equalsIgnoreCase("no")) {
            task.setIsCompleted(false);
        }

        File userFolder = new File(currentUsername);
        File taskFile = new File(userFolder, "task" + task.getId() + ".txt");
        FileWriter fw = new FileWriter(taskFile);
        fw.write(task.getId() + "\n");
        fw.write(task.getGroup() + "\n");
        fw.write(task.isIsCompleted() + "\n");
        fw.write(task.getDueDate() + "\n");
        fw.write(task.getHeader() + "\n");
        fw.write(task.getDescription() + "\n");
        fw.close();

        System.out.println("Task updated successfully!");
        sortTasks();
    }

    public static void removeTask() {
        System.out.println("\n---- Remove Task/s ----");
        System.out.println("How many do you want to remove?");
        System.out.println("[1] Single");
        System.out.println("[2] Multiple");
        System.out.println("[3] All");
        System.out.println("[4] Return");
        System.out.print("Choice: ");
        String choice = sc.nextLine();

        if (choice.equals("1")) {
            System.out.printf("%-9s %-10s %-10s %-15s %-20s\n", "List No.", "Group", "Status", "Due Date", "Header");
            System.out.println("--------------------------------------------------------------------------");
            if (tasks.size() == 0) {
                System.out.println("No tasks available.");
            } else {
                int listNumber = 1;
                for (Task task : tasks) {
                    System.out.printf("%-9d %-10s %-10s %-15s %-20s\n",
                            listNumber,
                            task.getGroup(),
                            task.getStatusIcon(),
                            task.getDueDate(),
                            task.getHeader().length() > 17 ? task.getHeader().substring(0, 17) + "..." : task.getHeader()
                    );
                    listNumber++;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            System.out.print("Enter Task Number (List No.): ");
            int index = Integer.parseInt(sc.nextLine()) - 1;

            if (index < 0 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            Task removed = tasks.get(index);
            System.out.print("Are you sure you want to remove \"" + removed.getHeader() + "\"? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                tasks.remove(index);
                File userFolder = new File(currentUsername);
                File taskFile = new File(userFolder, "task" + removed.getId() + ".txt");
                if (taskFile.exists()) {
                    taskFile.delete();
                }
                System.out.println("Removed: " + removed.getHeader());
            } else {
                System.out.println("Cancelled.");
            }

        } else if (choice.equals("2")) {
            System.out.printf("%-9s %-10s %-10s %-15s %-20s\n", "List No.", "Group", "Status", "Due Date", "Header");
            System.out.println("--------------------------------------------------------------------------");
            if (tasks.size() == 0) {
                System.out.println("No tasks available.");
            } else {
                int listNumber = 1;
                for (Task task : tasks) {
                    System.out.printf("%-9d %-10s %-10s %-15s %-20s\n",
                            listNumber,
                            task.getGroup(),
                            task.getStatusIcon(),
                            task.getDueDate(),
                            task.getHeader().length() > 17 ? task.getHeader().substring(0, 17) + "..." : task.getHeader()
                    );
                    listNumber++;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            System.out.print("Enter Task Numbers (ex. [1, 3, 5]): ");
            String input = sc.nextLine();

            input = input.replace("[", "").replace("]", "").replace(",", " ");
            String[] parts = input.split(" ");

            ArrayList<Integer> indexes = new ArrayList<>();
            for (String part : parts) {
                if (!part.equals("")) {
                    int val = Integer.parseInt(part) - 1;
                    if (val >= 0 && val < tasks.size()) {
                        indexes.add(val);
                    }
                }
            }

            if (indexes.size() == 0) {
                System.out.println("No valid task numbers provided.");
                return;
            }

            System.out.print("Are you sure you want to remove these " + indexes.size() + " tasks? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                for (int i = 0; i < indexes.size() - 1; i++) {
                    for (int j = i + 1; j < indexes.size(); j++) {
                        if (indexes.get(i) < indexes.get(j)) {
                            int temp = indexes.get(i);
                            indexes.set(i, indexes.get(j));
                            indexes.set(j, temp);
                        }
                    }
                }
                
                for (int idx : indexes) {
                    Task removed = tasks.get(idx);
                    File userFolder = new File(currentUsername);
                    File taskFile = new File(userFolder, "task" + removed.getId() + ".txt");

                    if (taskFile.exists()) {
                        taskFile.delete();
                    }

                    System.out.println("Removed: " + removed.getHeader());
                    tasks.remove(idx);
                }
            } else {
                System.out.println("Cancelled.");
            }
        } else if (choice.equals("3")) {
            System.out.printf("%-9s %-10s %-10s %-15s %-20s\n", "List No.", "Group", "Status", "Due Date", "Header");
            System.out.println("--------------------------------------------------------------------------");
            if (tasks.size() == 0) {
                System.out.println("No tasks available.");
            } else {
                int listNumber = 1;
                for (Task task : tasks) {
                    System.out.printf("%-9d %-10s %-10s %-15s %-20s\n",
                            listNumber,
                            task.getGroup(),
                            task.getStatusIcon(),
                            task.getDueDate(),
                            task.getHeader().length() > 17 ? task.getHeader().substring(0, 17) + "..." : task.getHeader()
                    );
                    listNumber++;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            if (tasks.size() == 0) {
                System.out.println("There are no tasks to remove.");
                return;
            }

            System.out.print("Are you sure you want to remove ALL tasks? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                for (Task task : tasks) {
                    File userFolder = new File(currentUsername);
                    File taskFile = new File(userFolder, "task" + task.getId() + ".txt");

                    if (taskFile.exists()) {
                        taskFile.delete();
                    }
                }

                tasks.clear();
                System.out.println("All tasks removed.");
            } else {
                System.out.println("Cancelled.");
            }

        } else if (choice.equals("4")) {
            return;
        } else {
            System.out.println("Invalid choice.");
        }

        sortTasks();
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

        sortTasks();
        System.out.println("Task saved!");
        taskCounter++;
    }

    public static String chooseGroup() {
        String input = "";
        while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
            System.out.println("Group:\n[1] Work\n[2] School\n[3] House\n[4] Other");
            System.out.print("Choose group: ");
            input = sc.nextLine();

            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
                System.out.println("Invalid choice.");
            }
        }

        switch (input) {
            case "1":
                return "Work";
            case "2":
                return "School";
            case "3":
                return "House";
            case "4":
                return "Other";
        }
        return "Other";
    }
}
                
            
        
   
