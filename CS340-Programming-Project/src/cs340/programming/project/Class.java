package cs340.programming.project;

import java.util.ArrayList;
import java.util.Arrays;

public class Class implements Comparable<Class>{
    //data fields
    private int classID;
    private int numberSections;
    private Integer[] sectionTimes; //we use the section number as an index into the array to get the time of that section
    private String[] sectionRooms;
    private ArrayList<Integer> professors; //who can teach the sections
    private int enrollmentLimit;
    private ArrayList<Integer>[] enrolledStudents;
    private int[] whoTeachesSection;

    public Class(int classID, int numberSections, ArrayList<Integer> professors, int enrollmentLimit)
    {
        this.classID = classID;
        this.numberSections = numberSections;
        this.sectionTimes = new Integer[numberSections];
        Arrays.fill(this.sectionTimes, -1); //start all values at -1
        this.sectionRooms = new String[numberSections];
        this.professors = professors;
        this.enrollmentLimit = enrollmentLimit;
        this.enrolledStudents = new ArrayList[numberSections];
        this.whoTeachesSection = new int[numberSections];
        for(int i = 0; i < numberSections; i++){
            enrolledStudents[i] = new ArrayList<Integer>();
        }
    }

    //getter methods
    public int getClassID()
    {
        return classID;
    }

    public int getNumberSections()
    {
        return numberSections;
    }

    public Integer[] getSectionTimes()
    {
        return sectionTimes;
    }

    public String[] getSectionRooms()
    {
        return sectionRooms;
    }

    public ArrayList<Integer> getProfessors()
    {
        return professors;
    }

    public int getEnrollmentLimit()
    {
        return enrollmentLimit;
    }

    public ArrayList<Integer>[] getEnrolledStudents()
    {
        return enrolledStudents;
    }

    public void setSingleSectionTime(int index, Integer sectionTime)
    {
        this.sectionTimes[index] = sectionTime;
    }
    
    public void setSingleSectionRoom(int index, String sectionRoom){
        this.sectionRooms[index] = sectionRoom;
    }
    
    public void setSingleSectionProfessor(int index, int profID){
        this.whoTeachesSection[index] = profID;
    }

    //method to enroll a student in a class
    public void enrollStudent(Integer student, Integer section)
    {
        this.enrolledStudents[section].add(student);
    }

    @Override
    public int compareTo(Class c)
    {
        return c.getNumberSections()*c.getEnrollmentLimit() - this.getNumberSections()*this.getEnrollmentLimit();
    }

    @Override
    public String toString() {
        return "Class{" +
                "classID:" + classID +
                ", numberSections:" + numberSections +
                ", sectionTimes:" + CS340ProgrammingProject.printArray(sectionTimes) +
                ", sectionRooms:" + CS340ProgrammingProject.printArray(sectionRooms) +
                ", professors:" + CS340ProgrammingProject.printArray(professors.toArray()) +
                ", enrollmentLimit:" + enrollmentLimit +
                ", enrolledStudents:" + enrolledStudents +
                '}';
    }
}
