/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package cs340.programming.project;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
    private static boolean extensions = true;
    private static boolean basic = true;
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
    //private static String[] times;
    //dictionary: key is professor id, value is arrayList of string times
    private static HashMap<Integer, ArrayList<Integer>> preferredTimes = new HashMap<>();
    //dictionary: key is professor id, value is arrayList of times professor is teaching
    private static HashMap<Integer, ArrayList<Integer>> teachingTimes = new HashMap<>();

    //main function
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis(); //got information from stack overflow

        input = new Scanner(System.in);

        //Allow the user to input file paths
        //inputFiles(constraints, student_prefs);

        //Paths of the files to read
        //constraints = new File("C:/Users/Arthur/Documents/NetBeansProjects/const_haverford_test.txt");
        //student_prefs = new File("C:/Users/Arthur/Documents/NetBeansProjects/pref_haverford_test.txt");
        constraints = new File("/Users/Sarah/Desktop/cs340/project/haverford/const_haverford_test.txt");
        student_prefs = new File("/Users/Sarah/Desktop/cs340/project/haverford/pref_haverford_test.txt");
        //constraints = new File(args[0]);
        //student_prefs = new File(args[1]);
        constraints_scanner = new Scanner(constraints);
        student_prefs_scanner = new Scanner(student_prefs);

        //We know the first line is the number of times
        //We know the first section is all times

        //Got parsing for only integers from Stack Overflow web-page
        num_times = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]",""));
        class_times = new String[num_times];
        if(!basic){
            readLinesTimes(class_times, constraints_scanner, num_times);
        }

        //We know the next section in Haverfordconstraints is the rooms
        num_rooms = Integer.parseInt(constraints_scanner.nextLine().replaceAll("[\\D]", ""));
        room_strings = new String[num_rooms];
        readLines(room_strings, constraints_scanner, num_rooms);
        rooms = new Room[num_rooms];
        Room current;
        for (int i = 0; i < num_rooms; i++) {
            Scanner room_scanner = new Scanner(room_strings[i]);
            current = new Room(room_scanner.next(), room_scanner.nextInt());
            rooms[i] = current;
        }
        Arrays.sort(rooms);

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
            if(extensions){
                Scanner class_scanner = new Scanner(classprofs[i]);
                int class_id = class_scanner.nextInt();
                int enrollment_limit = 0;
                if(class_scanner.hasNext()){
                    enrollment_limit = class_scanner.nextInt();
                }
                ArrayList<Integer> prof_list = new ArrayList<>();
                int num_sections = 0;
                while(class_scanner.hasNext()){
                    int prof_id = class_scanner.nextInt();
                    prof_list.add(prof_id);
                    num_sections++;
                    teachingTimes.put(prof_id, new ArrayList<Integer>());
                }
                class_queue.add(new Class(class_id, num_sections, prof_list, enrollment_limit));
            }
            else{
                Scanner class_scanner = new Scanner(classprofs[i]);
                int class_id = class_scanner.nextInt();
                int enrollment_limit = 50;
                ArrayList<Integer> prof_list = new ArrayList<>();
                int num_sections = 0;
                while(class_scanner.hasNext()){
                    int prof_id = class_scanner.nextInt();
                    prof_list.add(prof_id);
                    num_sections++;
                    teachingTimes.put(prof_id, new ArrayList<Integer>());
                }
                class_queue.add(new Class(class_id, num_sections, prof_list, enrollment_limit));
            }
        }
        if(extensions){
            while(constraints_scanner.hasNext()){
                String profPreferredTimes = constraints_scanner.nextLine();
                Scanner prof_scanner = new Scanner(profPreferredTimes);
                int profId = prof_scanner.nextInt();
                ArrayList<Integer> prefTimes = new ArrayList<>();
                while(prof_scanner.hasNext()){
                    prefTimes.add(prof_scanner.nextInt());
                }
                preferredTimes.put(profId,prefTimes);
            }
        }
        num_students = Integer.parseInt(student_prefs_scanner.nextLine().replaceAll("[\\D]",""));

        //process data from student prefs file
        String[] student_pref_classes = new String[num_students];
        readLines(student_pref_classes, student_prefs_scanner, num_students);
        Student[] students = new Student[num_students];
        for(int i = 0; i < num_students; i++){
            Scanner student_scanner = new Scanner(student_pref_classes[i]);
            int student_id = student_scanner.nextInt();
            ArrayList<Integer> pref_classes = new ArrayList<>();
            while(student_scanner.hasNext()){
                pref_classes.add(student_scanner.nextInt());
            }
            students[i] = new Student(student_id, pref_classes);
        }
        //Fill classes with necessary information
        Class currentClass;
        int numSections;
        int currentProfessor;
        int classProcessed = 0;
        while(!class_queue.isEmpty()) {
            currentClass = class_queue.poll(); //never going to be null
            numSections = currentClass.getNumberSections();

            for(int i=0; i<numSections; i++) {
                boolean scheduledSection = false;
                while (!scheduledSection) {
                    int currentProf = currentClass.getProfessors().get(i);
                    if(extensions){
                        boolean hasRoom = false;
                        for (int j = 0; j < preferredTimes.get(currentProf).size(); j++)
                        {
                            int currentTime = preferredTimes.get(currentProf).get(j);
                            int currentTry = findRoom(currentClass.getEnrollmentLimit(),rooms);
                            Room room = rooms[currentTry];
                            while(!hasRoom){
                                if(!room.isOccupied(currentTime)){
                                    currentClass.setSingleSectionTime(i, currentTime);
                                    currentClass.setSingleSectionRoom(i, room.getRoomName());
                                    currentClass.setSingleSectionProfessor(i, currentProf);
                                    teachingTimes.get(currentProf).add(currentTime);
                                    room.addOccupiedTime(currentTime);
                                    hasRoom = true;
                                    scheduledSection = true;
                                }
                                currentTry++;
                                room = rooms[currentTry];
                            }

                            //mark time as tried
                        }
                    }

                    Random random = new Random();
                    int randomTime = random.nextInt(class_times.length);
                    int currentTime = randomTime;
                    //check if time was actually assigned (filled with -1 at the start which is then replaced with a time
                    while (currentClass.getSectionTimes()[i] == -1) {
                        //pick an entry into the times array at random
                        if(!teachingTimes.get(currentProf).contains(currentTime)){
                            boolean hasRoom = false;
                            int currentTry = findRoom(currentClass.getEnrollmentLimit(),rooms);
                            Room room = rooms[currentTry];
                            while(!hasRoom){
                                if(!room.isOccupied(currentTime)){
                                    currentClass.setSingleSectionTime(i, currentTime);
                                    currentClass.setSingleSectionRoom(i, room.getRoomName());
                                    currentClass.setSingleSectionProfessor(i, currentProf);
                                    teachingTimes.get(currentProf).add(currentTime);
                                    room.addOccupiedTime(currentTime);
                                    hasRoom = true;
                                    scheduledSection = true;
                                }
                                if(currentTry == rooms.length - 1){
                                    break;
                                }
                                currentTry++;
                                room = rooms[currentTry];
                            }
                        }
                        currentTime++;
                        if(currentTime == class_times.length){
                            currentTime = 0;
                        }
                        if(currentTime == randomTime){
                            break;
                        }
                    }
                }
            }
            classes[classProcessed]=currentClass;
            classProcessed++;
        }

        //sort class array
        Arrays.sort(classes, new ClassComparator());

        //enrolling students
        for(int i=0; i<students.length; i++)
        {
            ArrayList<Integer> preferences = students[i].getPreferencesList();
            for(int j=0; j<preferences.size(); j++) //getting preferences per student
            {
                //enroll class in preferences list
                Integer classToEnroll = preferences.get(j); //getting first preferred class
                Class foundClass = findClass(classToEnroll, classes);
                if(foundClass!= null && foundClass.getNumberSections() > 0){
                    ArrayList<Integer> currentlyEnrolled = students[i].getEnrolledClassList();

                    //make sure classToEnroll has no conflicts with classes currently enrolled
                    /*
                    if(currentlyEnrolled.size()==0) //if enrolled classes list is empty
                    {
                        //enroll student in class and note section enrolled in
                        students[i].enrollStudent(classToEnroll, 0);

                        //list student as enrolled in class
                        if(foundClass!= null){
                            if(foundClass.getNumberSections()!=0) {
                                foundClass.enrollStudent(students[i].getStudentID(), 0);
                            }
                        }

                    }*/
                    //student is already enrolled in one or more classes
                    //else {
                        //go through list of classes student current enrolled in
                        int numCurrentlyEnrolled = currentlyEnrolled.size();
                        if(extensions) {
                            //boolean if the student can enroll in the class
                            boolean canEnroll = true;
                            Class wantToEnroll = findClass(classToEnroll,classes);
                            int numWantToEnrollSections = wantToEnroll.getNumberSections();

                            if(wantToEnroll!=null) {
                                for(int p=0; p<numWantToEnrollSections; p++) {
                                    for (int k = 0; k < numCurrentlyEnrolled; k++) {
                                        Class thisClass = findClass(currentlyEnrolled.get(k),classes);
                                        if(thisClass != null && thisClass.getSectionTimes().length != 0){
                                            if(wantToEnroll.getSectionTimes().length != 0&&wantToEnroll.getEnrollmentLimit()>wantToEnroll.getEnrolledStudents()[p].size()) {
                                                for(int l = 0; l < thisClass.getNumberSections();l++){    
                                                    Integer timeToCheck = thisClass.getSectionTimes()[l];
                                                    if (wantToEnroll.getSectionTimes()[p].equals(timeToCheck)) {
                                                        canEnroll = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (canEnroll) {
                                    students[i].enrollStudent(classToEnroll, 0);
                                    if(wantToEnroll.getSectionTimes().length != 0) {
                                        wantToEnroll.enrollStudent(students[i].getStudentID(), 0);
                                    }
                                }
                            }
                        }
                        else {
                            if(foundClass.getEnrollmentLimit()>foundClass.getEnrolledStudents()[0].size()){
                                boolean canEnroll = true;
                                Class wantToEnroll = findClass(classToEnroll,classes);
                                if(wantToEnroll!=null) {
                                    for (int k = 0; k < numCurrentlyEnrolled; k++) {
                                        Class thisClass = findClass(currentlyEnrolled.get(k),classes);
                                        if(thisClass != null && thisClass.getSectionTimes().length != 0){
                                            if(wantToEnroll.getSectionTimes().length != 0) {
                                                Integer timeToCheck = thisClass.getSectionTimes()[0];
                                                if (wantToEnroll.getSectionTimes()[0].equals(timeToCheck)) {
                                                    canEnroll = false;
                                                    break;
                                                }
                                            }
                                            else{
                                                canEnroll = false;
                                            }
                                        }
                                    }
                                    if (canEnroll) {
                                        students[i].enrollStudent(classToEnroll, 0);
                                        if(wantToEnroll.getSectionTimes().length != 0) {
                                            wantToEnroll.enrollStudent(students[i].getStudentID(), 0);
                                        }
                                    }
                                }

                            }
                        }
                    //}
                }

            }
        }

        if(extensions){
            PrintStream out = new PrintStream(new FileOutputStream("/Users/Sarah/Desktop/cs340/project/haverford/output_test.txt"));
            PrintStream originalOut = System.out;
            System.setOut(out);
            System.out.println("Course\tSection\tRoom\tTeacher\tTime\tStudents");
            for(int i = 0; i < classes.length; i++){
                currentClass = classes[i];
                for(int j = 0; j < currentClass.getNumberSections(); j++){
                    String enrolled_students = "";
                    ArrayList<Integer> section_students = currentClass.getEnrolledStudents()[j];
                    for(int k = 0; k < section_students.size(); k++){
                        enrolled_students = enrolled_students + section_students.get(k) + " ";
                    }
                    System.out.println(currentClass.getClassID() + "\t" + (j+1) + "\t" + currentClass.getSectionRooms()[j] + "\t" + currentClass.getProfessors().get(j) + "\t"
                            + currentClass.getSectionTimes()[j] + "\t" + enrolled_students);
                }
            }
            //reset output
            System.setOut(originalOut);
        }
        else{
            PrintStream out = new PrintStream(new FileOutputStream("/Users/Sarah/Desktop/cs340/project/haverford/OUTPUT.txt")); //"/Users/Sarah/Desktop/cs340/project/haverford/schedule.txt"
            PrintStream originalOut = System.out;
            //PrintStream out = new PrintStream(new FileOutputStream(args[2]));
            System.setOut(out);
            System.out.println("Course\tRoom\tTeacher\tTime\tStudents");
            for(int i = 0; i < classes.length; i++){
                currentClass = classes[i];
                String enrolled_students = "";
                if(currentClass.getNumberSections()!=0) {
                    ArrayList<Integer> section_students = currentClass.getEnrolledStudents()[0];
                    for (int k = 0; k < section_students.size(); k++) {
                        enrolled_students = enrolled_students + section_students.get(k) + " ";
                    }
                    System.out.println(currentClass.getClassID() + "\t" + currentClass.getSectionRooms()[0] + "\t" + currentClass.getProfessors().get(0) + "\t"
                            + currentClass.getSectionTimes()[0] + "\t" + enrolled_students);
                }
            }
            //reset output
            System.setOut(originalOut);
        }

        if(extensions)
        {
            //best case value



            //actual value
        }

        else
        {
            int totalStudentsEnrolled = 0;

            for(int i=0; i<classes.length; i++)
            {
                if(classes[i].getNumberSections()>0 && classes[i].getEnrolledStudents()[0].size()>0) {
                    totalStudentsEnrolled += classes[i].getEnrolledStudents()[0].size();
                }
            }

            System.out.println("Student Preference Value: " + (totalStudentsEnrolled));
            System.out.println("Best Case Student Value: " + (4*num_students));
        }

        long finishTime = System.currentTimeMillis();

        System.out.println("That took " + (finishTime - startTime) + " milliseconds");
    }


    //helper functions
    //helper to read some number of lines repeatedly
    static void readLines(Object[] array, Scanner scanner, int num_lines_to_read){
        for(int i = 0; i < num_lines_to_read; i++){
            array[i] = scanner.nextLine();
        }
    }

    static void readLinesTimes(Object[] array, Scanner scanner, int num_lines_to_read){
        for(int i = 0; i < num_lines_to_read; i++){
            array[i] = scanner.nextLine().substring(0,3).trim();
        }
    }

    static String printArray(Object[] array)
    {
        String returnString = "";
        for(int i = 0; i < array.length; i++)
        {
            returnString = returnString + array[i] + "\n";
            System.out.println(array[i]);
        }
        return returnString;
    }

    static Class findClass(int id, Class[] classes){
        int start = 0;
        int end = classes.length - 1;
        int index = (start + end)/2;
        int currentID = classes[index].getClassID();
        while(currentID != id){
            if(end - start == 1){
                return null;
            }
            if(currentID < id){
                start = index;
            }
            else{
                end = index;
            }
            index = (start + end)/2;
            currentID = classes[index].getClassID();
        }
        return classes[index];
    }

    static int findRoom(int capacity, Room[] rooms){
        int start = 0;
        int end = rooms.length - 1;
        int index = (start + end)/2;
        int currentCapacity = rooms[index].getRoomCapacity();
        while(currentCapacity != capacity){
            if(end - start == 1){
                if(currentCapacity >= capacity){
                    return index;
                }
                while(currentCapacity < capacity && index < rooms.length){
                    index++;
                    currentCapacity = rooms[index].getRoomCapacity();
                }
                return index;
            }
            if(currentCapacity < capacity){
                start = index;
            }
            else{
                end = index;
            }
            index = (start + end)/2;
            currentCapacity = rooms[index].getRoomCapacity();
        }
        return index;
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
