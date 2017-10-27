package cs340.programming.project;

import java.util.ArrayList;

public class Student {
    //data fields

    private int studentID;
    private Class[] preferencesList;
    private ArrayList<Class> enrolledClassList;

    public Student(int studentID, Class[] preferencesList)
    {
        this.studentID = studentID;
        this.preferencesList = preferencesList;
        this.enrolledClassList = null;
    }

    //getter methods
    public int getStudentID()
    {
        return this.studentID;
    }

    public Class[] getPreferencesList()
    {
        return this.preferencesList;
    }

    public ArrayList<Class> getEnrolledClassList()
    {
        return this.enrolledClassList;
    }

    //setter methods
    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }

    public void setPreferencesList(Class[] preferencesList)
    {
        this.preferencesList = preferencesList;
    }

    public void setEnrolledClassList(ArrayList<Class> enrolledClassList)
    {
        this.enrolledClassList = enrolledClassList;
    }
}
