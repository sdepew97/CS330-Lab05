package cs340.programming.project;

import java.util.ArrayList;

public class Student {
    //data fields
    private int studentID;
    private ArrayList<Integer> preferencesList; //holds class id's that student desires
    private ArrayList<Integer> enrolledClassList; //holds class id's that student is enrolled in

    public Student(int studentID, ArrayList<Integer> preferencesList)
    {
        this.studentID = studentID;
        this.preferencesList = preferencesList;
        this.enrolledClassList = new ArrayList<Integer>();
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

    //setter methods
    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }

    public void setPreferencesList(ArrayList<Integer> preferencesList)
    {
        this.preferencesList = preferencesList;
    }

    //method to enroll student in a class
    public void enrollStudent(Integer classToEnroll)
    {
        this.enrolledClassList.add(classToEnroll);
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
