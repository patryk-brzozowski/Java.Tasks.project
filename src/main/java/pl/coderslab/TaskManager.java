package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;
    public static void main(String[] args) {
        tasks = load();
        Scanner scr = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            System.out.println(ConsoleColors.BLUE + ("Please select an option:" + ConsoleColors.RESET));
            System.out.println("add\n" + "remove\n" + "list \n" + "exit");

            String check = scr.nextLine();
            switch (check) {
                case "add" -> add();
                case "remove" -> remove();
                case "list" -> list();
                case "exit" -> {
                    exit();
                    exit = false;
                    System.out.println(ConsoleColors.RED + "The program has finished its work.");
                }
                default -> System.out.println("Wrong command. Try again");
            }
        }
    }

    public static String[][] load() {
        Path path = Path.of("tasks.csv");
        String[][] tasksArray = new String[0][0];
        try {
            for (String task: Files.readAllLines(path)) {
                tasksArray = ArrayUtils.add(tasksArray, task.split(","));
            }
        } catch (IOException e) {
            System.out.println("File with list of tasks is probably deleted or program doesn't have access to it");
        }
        return tasksArray;
    }
    public static void list() {

        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + String.join(" ", tasks[i]));
        }
    }
    public static void add() {
        Scanner scr = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scr.nextLine();
        System.out.println("Please add task due date in format 'yyyy-mm-dd'");
        String dateStr;
        while (true) {
            dateStr = scr.nextLine();
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(dateStr);
                if (dateStr.equals(format.format(date))) {
                    break;
                }
            } catch (ParseException | IllegalArgumentException e) {}
            System.out.println("Wrong argument. Please add task due date in format 'yyyy-mm-dd'");
        }
        System.out.println("Is your task important? (true/false)");
        String importance;
        while (true) {
            importance = scr.nextLine();
            if (importance.equalsIgnoreCase("true") || importance.equalsIgnoreCase("false")){
                break;
            } else {
                System.out.println("Please type true or false");
            }
        }
        tasks = Arrays.copyOf(tasks, tasks.length+1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = dateStr;
        tasks[tasks.length-1][2] = importance;
        System.out.println("Record was successfully added");
    }
    public static void remove() {
        Scanner scr = new Scanner(System.in);
        System.out.println("Please select number of task to remove");
        while (!scr.hasNextInt()) {
            System.out.println("It's not a number. Try again.");
            scr.nextLine();
        }
        int number;
        while (true) {
            number = scr.nextInt();
            if (number>=0 && number<tasks.length) {
                break;
            } else {
                System.out.println("Incorrect argument passed. There is no record with such number.");
            }
        }
        tasks = ArrayUtils.remove(tasks, number);
        System.out.println("Record was successfully deleted.");
    }
    public static void exit() {
        try (FileWriter writer = new FileWriter("tasks.csv")) {
            for (String[] task : tasks) {
                writer.append(String.join(",", task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("There is a problem with access to file tasks.csv");
        }
    }

}
