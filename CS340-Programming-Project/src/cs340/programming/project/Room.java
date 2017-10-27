package cs340.programming.project;

public class Room {
    //data fields
    private String roomName;
    private int roomCapacity;

    //constructor
    public Room(String roomName, int roomCapacity)
    {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
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

    //setter methods
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public void setRoomCapacity(int roomCapacity)
    {
        this.roomCapacity = roomCapacity;
    }
}
