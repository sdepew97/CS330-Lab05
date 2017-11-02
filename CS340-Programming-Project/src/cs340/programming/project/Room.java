package cs340.programming.project;

import java.util.ArrayList;

public class Room implements Comparable<Room>{
    //data fields
    private String roomName;
    private int roomCapacity;
    private ArrayList<Integer> timesOccupied;

    //constructor
    public Room(String roomName, int roomCapacity)
    {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
        this.timesOccupied = new ArrayList<>();
    }

    //getter methods
    public String getRoomName()
    {
        return this.roomName;
    }

    public int getRoomCapacity()
    {
        return this.roomCapacity;
    }

    public ArrayList<Integer> getTimesOccupied()
    {
        return timesOccupied;
    }

    //setter methods
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public void setRoomCapacity(int roomCapacity)
    {
        this.roomCapacity = roomCapacity;
    }

    public void setTimesOccupied(ArrayList<Integer> timesOccupied)
    {
        this.timesOccupied = timesOccupied;
    }

    //checks if the room is occupied at a certain time
    public boolean isOccupied(int time)
    {
        return this.timesOccupied.contains(time);
    }

    //add time occupied
    public void addOccupiedTime(int time)
    {
        this.timesOccupied.add(time);
    }

    @Override
    public int compareTo(Room room)
    {
        //order the values based off the room capacities
        return this.getRoomCapacity() - room.getRoomCapacity();
    }

    @Override
    public String toString()
    {
        return "Room{" +
                "Name:'" + roomName + '\'' +
                ", Capacity:" + roomCapacity +
                ", Times Occupied:" + CS340ProgrammingProject.printArray(timesOccupied.toArray()) +
                '}';
    }
}
