package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;
    public static void main(String[] args) {
        tasks = load();
        options();
    }

    private static void options() {
        String[] options = {"add", "remove", "list", "exit"};
        Scanner scr = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLUE + ("Please select an option:" + ConsoleColors.RESET));
            for (String o: options) {
                System.out.println(o);
            }
            String check = scr.next();
            if (check.equals("add")) {
                add();
            } else if (check.equals("remove")) {
                remove();
            } else if (check.equals("list")) {
                list();
            } else if (check.equals("exit")) {
                exit();
                System.out.println(ConsoleColors.RED + "The program has finished its work.");
                break;
            } else {
                System.out.println("Wrong commend. Try again");
            }
        }
    }
    public static String[][] load() {
        int recordsNumber = 0;
        Path path = Path.of("tasks.csv");
        String[][] tasksArray = new String[0][0];
        try {
            for (String task: Files.readAllLines(path)) {
                recordsNumber++;
            }
            tasksArray = new String[recordsNumber][3];
            int counter = 0;
            for (String task: Files.readAllLines(path)) {
                    for (int j = 0; j < 3; j++) {
                        tasksArray[counter][j] = task.split(",")[j];
                }
                counter++;
            }
        } catch (IOException e) {
            System.out.println("Plik z listą zadań najwyraźniej został usunięty lub jest problem z dostępem do niego");
        }
        return tasksArray;
    }
    public static void list() {

        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(i + " : " + tasks[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void add() {
        Scanner scr = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scr.nextLine();
        System.out.println("Please add task due date in format 'rrrr-mm-dd'");
        String date = " " + scr.nextLine();
        System.out.println("Is your task important? (true/false)");
        String importance;
        while (true) {
            importance = " " + scr.nextLine();
            if (importance.equals(" true") || importance.equals(" false")){
                break;
            } else {
                System.out.println("Please type true or false");
            }
        }
        tasks = Arrays.copyOf(tasks, tasks.length+1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = date;
        tasks[tasks.length-1][2] = importance;
        scr.close();
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
            for (int i = 0; i < tasks.length; i++) {
                writer.append(String.join(",", tasks[i])+"\n");
            }
        } catch (IOException e) {
            System.out.println("Wystąpił problem z dostępem do pliku tasks.csv");
        }
    }

}
