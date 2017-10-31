package cs340.programming.project;

import java.util.ArrayList;
import java.util.Arrays;

public class Class /*implements Comparable<Class>*/{
    //data fields
    private int classID;
    private int numberSections;
    private String[] sectionTimes; //we use the section number as an index into the array to get the time of that section
    private ArrayList<String> sectionRooms;
    private ArrayList<Integer> professors; //who can teach the sections
    private int enrollmentLimit;
    private ArrayList<Student> enrolledStudents;
    private int[] whoTeachesSection;

    public Class(int classID, int numberSections, Integer[] professors, int enrollmentLimit)
    {
        this.classID = classID;
        this.numberSections = numberSections;
        this.sectionTimes = new String[numberSections];
        this.sectionRooms = new String[numberSections];
        this.professors = professors;
        this.enrollmentLimit = enrollmentLimit;
        this.enrolledStudents = new ArrayList<>();
        this.whoTeachesSection = new int[numberSections];
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

    public String[] getSectionTimes()
    {
        return sectionTimes;
    }

    public ArrayList<String> getSectionRooms()
    {
        return sectionRooms;
    }

    public Integer[] getProfessors()
    {
        return professors;
    }

    public int getEnrollmentLimit()
    {
        return enrollmentLimit;
    }

    public ArrayList<Student> getEnrolledStudents()
    {
        return enrolledStudents;
    }

    //setter methods
    public void setClassID(int classID)
    {
        this.classID = classID;
    }

    public void setNumberSections(int numberSections)
    {
        this.numberSections = numberSections;
    }

    public void setSectionTimes(String[] sectionTimes)
    {
        this.sectionTimes = sectionTimes;
    }

    public void setSectionRooms(String[] sectionRooms)
    {
        this.sectionRooms = sectionRooms;
    }

    public void setProfessors(Integer[] professors)
    {
        this.professors = professors;
    }

    public void setEnrollmentLimit(int enrollmentLimit)
    {
        this.enrollmentLimit = enrollmentLimit;
    }

    public void setEnrolledStudents(ArrayList<Student> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
    }

    public void assignProfessor(int section, int professor)
    {
        this.whoTeachesSection[section] = professor;
    }

    //method to enroll a student in a class
    public void enrollStudent(Student student)
    {
        this.enrolledStudents.add(student);
    }

    /*
    @Override
    public int compareTo(Class c)
    {
        return this.getNumberSections()*this.getEnrollmentLimit() - c.getNumberSections()*c.getEnrollmentLimit();
    }
    */

    @Override
    public String toString() {
        return "Class{" +
                "classID:" + classID +
                ", numberSections:" + numberSections +
                ", sectionTimes:" + CS340ProgrammingProject.printArray(sectionTimes) +
                ", sectionRooms:" + CS340ProgrammingProject.printArray(sectionRooms) +
                ", professors:" + CS340ProgrammingProject.printArray(professors) +
                ", enrollmentLimit:" + enrollmentLimit +
                ", enrolledStudents:" + enrolledStudents +
                '}';
    }
}
