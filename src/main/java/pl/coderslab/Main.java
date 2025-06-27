package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Path path1 = Paths.get("tasks.csv");
        loadTasksFromFile();
        try {
            for (String line : Files.readAllLines(path1)) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[][] options = {
                {"Please select an option:"},
                {"add", "remove", "list", "exit" + "\n"}
        };
        System.out.println(ConsoleColors.BLUE + options[0][0] + ConsoleColors.RESET);
        for (String option : options[1]) {
            System.out.println(option);
        }
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    exitTask();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n" + " Please select an option:");
            }
            System.out.println(ConsoleColors.BLUE + options[0][0] + ConsoleColors.RESET);
            for (String option : options[1]) {
                System.out.println(option);
            }

        }
    }

    public static String[][] tasks = new String[0][];

    public static void addTask() {

        Scanner scan = new Scanner(System.in);

        System.out.print("Please add task description: ");
        String description = scan.nextLine();

        System.out.print("Please add task due date (e.g. 2025-07-01): ");
        String date = scan.nextLine();

        System.out.print("Is your task important? (true/false): ");
        String isImportant = scan.nextLine();

        String[] newTask = {description, date, isImportant};

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;


        try (FileWriter writer = new FileWriter("tasks.csv", true)) {
                writer.write(String.join(",", newTask) + "\n");
            System.out.println("Task added to TaskManager." + "\n");
        } catch (IOException e) {
            System.out.println("Failed.");
        }
    }

    public static void loadTasksFromFile() {
        try {
            String[] linesArray = Files.readAllLines(Paths.get("tasks.csv")).toArray(new String[0]);
            tasks = new String[linesArray.length][];

            for (int i = 0; i < linesArray.length; i++) {
                tasks[i] = linesArray[i].split(",");
            }
        } catch (IOException e) {
            tasks = new String[0][];
        }
    }


    public static void removeTask() {
        Path path1 = Paths.get("tasks.csv");
        Scanner scan = new Scanner(System.in);

        System.out.print("Select number to remove: ");
        int indexToRemove = -1;

        while (true) {
            String input = scan.nextLine();
            try {
                indexToRemove = Integer.parseInt(input);
                if (indexToRemove >= 0 && indexToRemove < tasks.length) {
                    break;
                } else {
                    System.out.print("Index out of range. Try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }

        tasks = ArrayUtils.remove(tasks, indexToRemove);

        try (FileWriter writer = new FileWriter("tasks.csv")) {
            for (String[] task : tasks) {
                writer.write(String.join(",", task) + "\n");
            }
            System.out.println("Task removed successfully."+ "\n");
        } catch (IOException e) {
            System.out.println("Failed to update file.");
        }
    }

    public static void listTasks() {
        File file = new File("tasks.csv");
        StringBuilder reading = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                reading.append(scan.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not file found");
        }
        System.out.println(reading);
    }

    public static void exitTask() {
        Scanner scan = new Scanner(System.in);

        System.out.print(ConsoleColors.RED + "Bye, bye" + "\n" + ConsoleColors.RESET);

    }

}




