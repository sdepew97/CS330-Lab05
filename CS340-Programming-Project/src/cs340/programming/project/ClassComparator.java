/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs340.programming.project;

import java.util.Comparator;

/**
 *
 * @author Arthur
 */
public class ClassComparator implements Comparator<Class>{
    
    public ClassComparator(){}
    
    @Override
    public int compare(Class c, Class c1) {
        return c1.getNumberSections()*c1.getEnrollmentLimit() - c.getNumberSections()*c.getEnrollmentLimit();
    }
    
}
