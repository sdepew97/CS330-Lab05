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
/**
 *
 * @author Arthur
 */
public class CS340ProgrammingProject {

    /**
     * @param args the command line arguments
     */
    //helper to read some number of lines repeatedly
    static void readLines(String[] array, Scanner scanner, int num_lines_to_read){
        for(int i = 0; i < num_lines_to_read; i++){
            array[i] = scanner.nextLine();
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        File constraints = new File("haverfordConstraints.txt");
        File student_prefs = new File("haverfordStudentPrefs.txt");
        Scanner constraints_scanner = new Scanner(constraints);
        Scanner student_prefs_scanner = new Scanner(student_prefs);
        //We know the first line is the number of times
        //We know the first section is all times
        int num_times = constraints_scanner.nextInt();
        String[] class_times = new String[num_times];
        readLines(class_times, constraints_scanner, num_times);
        //We know the next section in Haverfordconstraints is the rooms
        int num_rooms = constraints_scanner.nextInt();
        String[] rooms = new String[num_rooms];
        readLines(rooms, constraints_scanner, num_rooms);
        //We know the third section will be classes and the professors
        //teaching each class
        int num_classes = constraints_scanner.nextInt();
        String[] classprofs = new String[num_classes];
        constraints_scanner.nextLine();
        readLines(classprofs, constraints_scanner, num_classes);
    }
}
