package cs340.programming.project;

import java.util.ArrayList;
import java.util.Arrays;

public class Class implements Comparable<Class>{
    //data fields
    private int classID;
    private int numberSections;
    private String[] sectionTimes; //we use the section number as an index into the array to get the time of that section
    private String[] sectionRooms;
    private ArrayList<Integer> professors;
    private int enrollmentLimit;
    private ArrayList<Student> enrolledStudents;

    public Class(int classID, int numberSections, ArrayList<Integer> professors, int enrollmentLimit)
    {
        this.classID = classID;
        this.numberSections = numberSections;
        this.sectionTimes = new String[numberSections];
        this.sectionRooms = new String[numberSections];
        this.professors = professors;
        this.enrollmentLimit = enrollmentLimit;
        this.enrolledStudents = new ArrayList<>();
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

    public void setProfessors(ArrayList<Integer> professors)
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

    //method to enroll a student in a class
    public void enrollStudent(Student student)
    {
        this.enrolledStudents.add(student);
    }

    @Override
    public int compareTo(Class c)
    {
        return this.getNumberSections()*this.getEnrollmentLimit() - c.getNumberSections()*c.getEnrollmentLimit();
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
