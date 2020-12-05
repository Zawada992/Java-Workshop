package com.company;

import com.company.ConsoleColors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskMenager {
    static final  String FILE = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;



    public static void main(String[] args) {
        tasks = loadToTab(FILE);
        menu();
    }
    public static void  showOptions(String[] tab){
        //Wyświetla poczatkowy wybór (zrobine)
        System.out.print(ConsoleColors.BLUE);
        System.out.println("Please select an option:" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLACK_BOLD);
        for (String option : tab){
            System.out.println(option);
        }
    }
    public  static void menu() {
        showOptions(OPTIONS);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getNumber());
                    System.out.println("Value was delete.");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    saveTab(FILE, tasks);
                    System.out.println(ConsoleColors.RED+"Good bye!!!");
                    System.exit(0);
                    break;

                default: System.out.println("Please select a correct option.");
            }
            showOptions(OPTIONS);
        }
    }
    public  static int getNumber(){
        System.out.println("Pleas select number to remove:");
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        while (!isNumberGreatrEqualZero(n)){
            System.out.println("Incorrect argument passed. Pleas give number or equal 0 ");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }
    public static boolean isNumberGreatrEqualZero(String input){
        if (NumberUtils.isParsable(input)){
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }
    public static String[][] loadToTab(String FILE){
        Path dir = Paths.get(FILE);
        if(!Files.exists(dir)){
            System.out.println("File not exist!!!");
            System.exit(0);
        }
        String [][] tab = null;
        try {
            List <String> strings =Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for(int i=0; i<strings.size(); i++){
                String[] split =strings.get(i).split(",");
                for(int j=0; j < split.length; j++){
                    tab[i][j] = split[j];
            }
        }
        }catch (IOException e){
            e.printStackTrace();
        }
        return tab;
    }

    private static void addTask() {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task dueDate");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important choose: true,\n but not impotrant choose: folse");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = dueDate;
        tasks[tasks.length-1][2] = isImportant;
    }

    private static void removeTask(String[][] tab, int index) {
        try {
            if (index<tab.length){
                tasks = ArrayUtils.remove(tab, index);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element not exist in tab");
        }

    }

    private static void printTab(String[][] tab) {
        for (int i= 0; i< tab.length; i++){
            System.out.print(i +" : ");
            for( int j=0; j<tab[i].length; j++){
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void saveTab(String FILE, String[][] tab) {
        Path dir = Paths.get(FILE);

        String[] lines = new String[tasks.length];
        for (int i=0; i < tab.length; i++){
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
