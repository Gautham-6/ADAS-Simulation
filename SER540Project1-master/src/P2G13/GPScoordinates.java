package P2G13;

//This class is for parsing and storing the coordinates from the file
public class GPScoordinates {
    private double latitude;
    private double longitude;
    private double timeToSendAt;

    //Parameterized constructor for setting the values
    GPScoordinates(double timeOffset, double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeToSendAt = timeOffset;
    }

    //getters
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public double getTimeToSendAt() {
        return timeToSendAt;
    }
}
