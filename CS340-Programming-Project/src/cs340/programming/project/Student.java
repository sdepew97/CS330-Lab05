package cs340.programming.project;

import java.util.ArrayList;

public class Student {
    //data fields
    private int studentID;
    private ArrayList<Integer> preferencesList; //holds class id's that student desires
    private ArrayList<Integer> enrolledClassList; //holds class id's that student is enrolled in
    private ArrayList<Integer> sectionOfClass;

    public Student(int studentID, ArrayList<Integer> preferencesList)
    {
        this.studentID = studentID;
        this.preferencesList = preferencesList;
        this.enrolledClassList = new ArrayList<Integer>();
        this.sectionOfClass = new ArrayList<Integer>();
    }

    //getter methods
    public int getStudentID()
    {
        return this.studentID;
    }

    public ArrayList<Integer> getPreferencesList()
    {
        return this.preferencesList;
    }

    public ArrayList<Integer> getEnrolledClassList() { return this.enrolledClassList; }

    public ArrayList<Integer> getSectionOfClass() { return this.sectionOfClass; }

    //method to enroll student in a class
    public void enrollStudent(Integer classToEnroll, Integer section)
    {
        this.enrolledClassList.add(classToEnroll);
        this.sectionOfClass.add(section);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID:" + studentID +
                ", preferencesList:" + CS340ProgrammingProject.printArray(preferencesList.toArray()) +
                ", enrolledClassList:" + CS340ProgrammingProject.printArray(enrolledClassList.toArray()) +
                '}';
    }
}
