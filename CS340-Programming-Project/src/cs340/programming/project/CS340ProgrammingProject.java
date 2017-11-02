/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs340.programming.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.String;
import static java.lang.System.in;

/**
 *
 * @author Arthur, Amanda, and Sarah
 */
public class CS340ProgrammingProject {

    //fields of the class
    //variables for file input
    private static Scanner input;
    private static Scanner constraints_scanner;
    private static Scanner student_prefs_scanner;
    private static File constraints;
    private static File student_prefs;

    private static int num_times = 0;
    private static int num_rooms = 0;
    private static int num_classes = 0;
    private static int num_teachers = 0;
    private static int num_students = 0;

    //data structures for information
    private static String[] class_times;
    private static PriorityQueue<Class> class_queue = new PriorityQueue<>();
    private static Class[] classes; //array that holds class objects
    private static String[] room_strings;
    private static String[] classprofs;
    private static Room[] rooms; //array that holds room objects
    private static Student[] students; //array that holds student objects
    private static String[] times; 
    //dictionary: key is professor id, value is arrayList of string times
    private static HashMap<Integer, ArrayList<Integer>> preferredTimes = new HashMap<>();
   //dictionary: key is professor id, value is arrayList of times professor is teaching
    private static HashMap<Integer, ArrayList<Integer>> teachingTimes = new HashMap();

    //Binary Search Tree to store room objects ordered by capacity
    private static BST<Room> roomBST;

    //main function
    public static void main(String[] args) throws FileNotFoundException {
        input = new Scanner(System.in);

        //Allow the user to input file paths
        //inputFiles(constraints, student_prefs);

        //Paths of the files to read
        constraints = new File("C:/Users/Arthur/Documents/NetBeansProjects/haverfordConstraints.txt");
        student_prefs = new File("C:/Users/Arthur/Documents/NetBeansProjects/haverfordStudentPrefs.txt");
        constraints_scanner = new Scanner(constraints);
        student_prefs_scanner = new Scanner(student_prefs);

        //We know the first line is the number of times
        //We know the first section is all times

        //Got parsing for only integers from Stack Overflow web-page
        num_times = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]",""));
        class_times = new String[num_times];
        readLines(class_times, constraints_scanner, num_times);

        //We know the next section in Haverfordconstraints is the rooms
        num_rooms = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        room_strings = new String[num_rooms];
        roomBST = new BST<>();
        readLines(room_strings, constraints_scanner, num_rooms);
        rooms = new Room[num_rooms];
        Room current;
        for (int i = 0; i < num_rooms; i++) {
            Scanner room_scanner = new Scanner(room_strings[i]);
            current = new Room(room_scanner.next(), room_scanner.nextInt());
            rooms[i] = current;
            roomBST.add(current);
        }

        //We know the third section will be classes and the professors
        //teaching each class
        num_classes = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        num_teachers = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        classprofs = new String[num_classes];

        //For extensions, we assume the input is in the following format:
        //class_id enrollmeent limit prof_id prof_id ...
        //where class_id is the id of the class, enrollment limit is per section
        //and a professor is listed as many times as sections that professor teaches
        readLines(classprofs, constraints_scanner, num_classes);
        classes = new Class[num_classes];

        for(int i = 0; i < num_classes; i++){
            Scanner class_scanner = new Scanner(classprofs[i]);
            int class_id = class_scanner.nextInt();
            int enrollment_limit = 50;
            /*
            if(class_scanner.hasNext()){
                enrollment_limit = class_scanner.nextInt();
            }*/
            ArrayList<Integer> prof_list = new ArrayList<Integer>();
            int num_sections = 0;
            while(class_scanner.hasNext()){
                prof_list.add(class_scanner.nextInt());
                num_sections++;
            }
           class_queue.add(new Class(class_id, num_sections, prof_list, enrollment_limit));
        }
        while(constraints_scanner.hasNext()){
            String profPreferredTimes = constraints_scanner.nextLine();
            Scanner prof_scanner = new Scanner(profPreferredTimes);
            int profId = prof_scanner.nextInt();
            ArrayList<Integer> prefTimes = new ArrayList<Integer>();
            while(prof_scanner.hasNext()){
                prefTimes.add(prof_scanner.nextInt());
            }
            preferredTimes.put(profId,prefTimes);
        }
        num_students = Integer.parseInt(student_prefs_scanner.nextLine().replaceAll("[\\D]",""));

        //process data from student prefs file
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

        //Fill classes with necessary information
        Class[] classesWithInformation = new Class[class_queue.size()];
        Class currentClass;
        int numSections;
        int currentProfessor;
        while(!class_queue.isEmpty()) {
            currentClass = class_queue.poll(); //never going to be null
            numSections = currentClass.getNumberSections();

            for(int i=0; i<numSections; i++) {
                //if(/*professor available to teach class*/)
                //{
                //dequeue.assignProfessor();

                //}

                while (currentClass.getSectionRooms().size() < i+1) {

                    for (int j = 0; j < (preferredTimes.get(currentClass.getProfessors().get(i))).size(); j++)
                    {
                        //if teaching time is available, so not yet teaching at this time
                        if(!teachingTimes.get(currentClass.getProfessors().get(i)).contains(preferredTimes.get(currentClass.getProfessors()).get(j)));
                        {
                            currentClass.setSingleSectionTime(i, preferredTimes.get(currentClass.getProfessors()).get(j));
                        }

                        //mark time as tried
                    }

                    //check if time was actually assigned (filled with -1 at the start which is then replaced with a time
                    if(currentClass.getSectionTimes()[i]!=-1)
                    {
                        //pick an entry into the times array at random
                        Random random = new Random();
                        random.nextInt();
                    }
                }
            }

        }

        /*
        System.out.println(num_times);
        System.out.println(num_rooms);
        System.out.println(num_classes);
        System.out.println(num_teachers);
        */
        //System.out.println(printArray(class_times));
        //System.out.println(printArray(student_pref_classes));
        System.out.println(roomBST.toString());
        System.out.println((printArray(rooms)));
        System.out.println((printArray(students)));
    }

    //helper functions
    //helper to read some number of lines repeatedly
    static void readLines(Object[] array, Scanner scanner, int num_lines_to_read){
        for(int i = 0; i < num_lines_to_read; i++){
            array[i] = scanner.nextLine();
        }
    }

    static String printArray(Object[] array)
    {
        String returnString = "";
        for(int i = 0; i < array.length; i++)
        {
            returnString = returnString + array[i] + "\n";
            //System.out.println(array[i]);
        }
        return returnString;
    }

    //Allow the user to input the paths of the files to read
    static void inputFiles(File constraints, File student_prefs)
    {
        String filePath;

        System.out.println("Please enter the full file path of the constraints file");
        filePath = input.nextLine();
        constraints = new File(filePath);

        System.out.println("Please enter the full file path of the student preferences file");
        filePath = input.nextLine();
        student_prefs = new File(filePath);
    }

}
