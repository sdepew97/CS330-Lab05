/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs340.programming.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.String;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 *
 * @author Arthur
 */
public class CS340ProgrammingProject {

    //helper to read some number of lines repeatedly
    static void readLines(Object[] array, Scanner scanner, int num_lines_to_read){
        for(int i = 0; i < num_lines_to_read; i++){
            array[i] = scanner.nextLine();
        }
    }
    
    static String printArray(Object[] array){
        String returnString = "";
        for(int i = 0; i < array.length; i++){
            returnString = returnString + array[i] + "\n";
            //System.out.println(array[i]);
        }
        return returnString;
    }
    /*
    static String printArrayList(ArrayList<Object> list){
        String returnString;
        for(int i = 0; i < list.size(); i++){
            returnString = returnString + ""
        }
        return returnString;
    }*/
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the full file path of the constraints file");
        String filePath = input.nextLine();
        File constraints = new File(filePath);
        System.out.println("Please enter the full file path of the student preferences file");
        filePath = input.nextLine();
        File student_prefs = new File(filePath);
        Scanner constraints_scanner = new Scanner(constraints);
        Scanner student_prefs_scanner = new Scanner(student_prefs);
        int num_times = 0;
        int num_rooms = 0;
        int num_classes = 0;
        int num_teachers = 0;
        int num_students = 0;

        //We know the first line is the number of times
        //We know the first section is all times
        /*
        while(constraints_scanner.hasNext()){
            System.out.println(constraints_scanner.nextLine());
        }
        while(student_prefs_scanner.hasNext()){
            System.out.println(student_prefs_scanner.nextLine());
        }*/
        //Got parsing for only integers from stackoverflow
        num_times = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]",""));
        String[] class_times = new String[num_times];
        readLines(class_times, constraints_scanner, num_times);
        //We know the next section in Haverfordconstraints is the rooms
        num_rooms = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        String[] room_strings = new String[num_rooms];
        readLines(room_strings, constraints_scanner, num_rooms);
        Room[] rooms = new Room[num_rooms];
        for (int i = 0; i < num_rooms; i++) {
            Scanner room_scanner = new Scanner(room_strings[i]);
            rooms[i] = new Room(room_scanner.next(), room_scanner.nextInt());
        }
        //We know the third section will be classes and the professors
        //teaching each class
        num_classes = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        num_teachers = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        String[] classprofs = new String[num_classes];
        //For extensions, we assume the input is in the following format:
        //class_id enrollmeent limit prof_id prof_id ...
        //where class_id is the id of the class, enrollment limit is per section
        //and a professor is listed as many times as sections that professor teaches
        readLines(classprofs, constraints_scanner, num_classes);
        PriorityQueue<Class> class_queue = new PriorityQueue<Class>();
        Class[] classes = new Class[num_classes];
        for(int i = 0; i < num_classes; i++){
            Scanner class_scanner = new Scanner(classprofs[i]);
            int class_id = class_scanner.nextInt();
            int enrollment_limit = class_scanner.nextInt();
            ArrayList<Integer> prof_list = new ArrayList<Integer>();
            int num_sections = 0;
            while(class_scanner.hasNext()){
                prof_list.add(class_scanner.nextInt());
                num_sections++;
            }
            class_queue.add(new Class(class_id, num_sections, prof_list, enrollment_limit));
        }
        num_students = Integer.parseInt(student_prefs_scanner.nextLine().replaceAll("[\\D]",""));
        String[] student_pref_classes = new String[num_students];
        readLines(student_pref_classes, student_prefs_scanner, num_students);
        Student[] students = new Student[num_students];
        for(int i = 0; i < num_students; i++){
            Scanner student_scanner = new Scanner(student_pref_classes[i]);
            int student_id = student_scanner.nextInt();
            ArrayList<Integer> pref_classes = new ArrayList<Integer>();
            while(student_scanner.hasNext()){
                pref_classes.add(student_scanner.nextInt());
            }
            students[i] = new Student(student_id, pref_classes);
        }
        /*
        System.out.println(num_times);
        System.out.println(num_rooms);
        System.out.println(num_classes);
        System.out.println(num_teachers);
        */
        //System.out.println(printArray(class_times));
        //System.out.println(printArray(student_pref_classes));
        System.out.println((printArray(rooms)));
        System.out.println((printArray(students)));
    }
}
