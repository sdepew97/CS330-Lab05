/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs340.programming.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Arthur
 */
public class CS340ProgrammingProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File constraints = new File("haverfordConstraints.txt");
        File student_prefs = new File("haverfordStudentPrefs.txt");
        Scanner constraints_scanner = new Scanner(constraints);
        Scanner student_prefs_scanner = new Scanner(student_prefs);
    }
    
}
