package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
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
                System.out.println("add");
            } else if (check.equals("remove")) {
                System.out.println("remove");
            } else if (check.equals("list")) {
                list();
            } else if (check.equals("exit")) {
                System.out.println(ConsoleColors.RED + "Program zakończył pracę.");
                break;
            } else {
                System.out.println("Niepoprawna komenda. Spróbuj jeszcze raz");
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
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void add() {
    }
    public static void delete() {
    }
    public static void exit() {
        Path path = Path.of("tasks.csv");

    }

}
