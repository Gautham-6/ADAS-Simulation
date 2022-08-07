package P2G13;

enum SegmentType{
    straight,
    curve
}

//enum for
enum CurveDirection{
    left,
    right
}
public class SegmentData {
    private SegmentType type;
    private double gpsLatitudeStart;
    private double gpsLongitudeStart;
    private double gpsLatitudeEnd;
    private double gpsLongitudeEnd;
    public void setType(SegmentType type) {
        this.type = type;
    }
    public double getGpsLatitudeEnd() {
        return gpsLatitudeEnd;
    }
    public void setGpsLatitudeEnd(double gpsLatitudeEnd) {
        this.gpsLatitudeEnd = gpsLatitudeEnd;
    }
    public double getGpsLongitudeEnd() {
        return gpsLongitudeEnd;
    }
    public void setGpsLongitudeEnd(double gpsLongitudeEnd) {
        this.gpsLongitudeEnd = gpsLongitudeEnd;
    }
    public double getAvgVehicleSpeed() {
        return avgVehicleSpeed;
    }
    public void setAvgVehicleSpeed(double avgVehicleSpeed) {
        this.avgVehicleSpeed = avgVehicleSpeed;
    }
    public double getCurrentTimeDifference() {
        return currentTimeDifference;
    }
    public void setCurrentTimeDifference(double currentTimeDifference) {
        this.currentTimeDifference = currentTimeDifference;
    }
    public double getTotalSpeedValues() {
        return totalSpeedValues;
    }
    public void setTotalSpeedValues(double totalSpeedValues) {
        this.totalSpeedValues = totalSpeedValues;
    }
    public double getSumSpeedValues() {
        return sumSpeedValues;
    }
    public void setSumSpeedValues(double sumSpeedValues) {
        this.sumSpeedValues = sumSpeedValues;
    }
    public double getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public double getMinSpeed() {
        return minSpeed;
    }
    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }
    public double getMaxLongAcceleration() {
        return maxLongAcceleration;
    }
    public void setMaxLongAcceleration(double maxLongAcceleration) {
        this.maxLongAcceleration = maxLongAcceleration;
    }
    public double getMinLongAcceleration() {
        return minLongAcceleration;
    }
    public void setMinLongAcceleration(double minLongAcceleration) {
        this.minLongAcceleration = minLongAcceleration;
    }
    public double getLengthOfStraight() {
        return lengthOfStraight;
    }
    public void setLengthOfStraight(double lengthOfStraight) {
        this.lengthOfStraight = lengthOfStraight;
    }
    public CurveDirection getDirection() {
        return direction;
    }
    public void setDirection(CurveDirection direction) {
        this.direction = direction;
    }
    public double getMaxLatAccerelation() {
        return maxLatAccerelation;
    }
    public void setMaxLatAccerelation(double maxLatAccerelation) {
        this.maxLatAccerelation = maxLatAccerelation;
    }
    public double getMinLatAcceleration() {
        return minLatAcceleration;
    }
    public void setMinLatAcceleration(double minLatAcceleration) {
        this.minLatAcceleration = minLatAcceleration;
    }
    public double getMaxSteeringAngle() {
        return maxSteeringAngle;
    }
    public void setMaxSteeringAngle(double maxSteeringAngle) {
        this.maxSteeringAngle = maxSteeringAngle;
    }
    public double getDegreeOfCurve() {
        return degreeOfCurve%360;
    }

    private double avgVehicleSpeed;
    private double currentTimeDifference;

    //straight segment variables
    private double totalSpeedValues;
    private double sumSpeedValues;
    private double maxSpeed;
    private double minSpeed;
    private double maxLongAcceleration;
    private double minLongAcceleration;
    private double lengthOfStraight;

    //curve segment variables
    private CurveDirection direction;
    private double maxLatAccerelation;
    private double minLatAcceleration;
    private double maxSteeringAngle;
    private double degreeOfCurve;


    public SegmentData(SegmentType type){
        this.type = type;
        this.avgVehicleSpeed = Double.MIN_VALUE;
        this.maxSpeed = Double.MIN_VALUE;
        this.minSpeed = Double.MAX_VALUE;
        this.minLatAcceleration = Double.MAX_VALUE;
        this.maxLatAccerelation = Double.MIN_VALUE;
        this.gpsLatitudeStart = 0.0;
        this.gpsLongitudeStart = 0.0;
        this.currentTimeDifference = 0.0;
    }
    //constructor for straight
    public SegmentData(double currentTimeDifference, SegmentType type, double gpsLatitudeStart, double gpsLongitudeStart, double speed, double longAcceleration){
        this.currentTimeDifference = currentTimeDifference;
        this.type = type;
        this.gpsLatitudeStart = gpsLatitudeStart;
        this.gpsLongitudeStart = gpsLongitudeStart;
        this.totalSpeedValues = 1;
        this.sumSpeedValues = speed;
        this.avgVehicleSpeed = speed;
        this.maxSpeed = speed;
        this.minSpeed = speed;
        this.maxLongAcceleration = longAcceleration;
        this.minLongAcceleration = longAcceleration;
    }
    //setters For Straight segment
    public void setSpeedValues(double speed){
        if(type == SegmentType.straight){
            if(maxSpeed<speed)
                maxSpeed = speed;
            if(minSpeed>speed)
                minSpeed = speed;
        }
        calculateAvgSpeed(speed);
    }
    public void setGpsLatitudeStart(double latitudeStart){
        this.gpsLatitudeStart = latitudeStart;
    }

    public void setGpsLongitudeStart(double gpsLongitudeStart) {
        this.gpsLongitudeStart = gpsLongitudeStart;
    }

    //method to calculate average speed
    public void calculateAvgSpeed(double speed){
        if(avgVehicleSpeed==Double.MIN_VALUE){
            avgVehicleSpeed = speed;
            totalSpeedValues = 1;
            sumSpeedValues = speed;
        }
        else{
            totalSpeedValues++;
            sumSpeedValues += speed;
            avgVehicleSpeed = sumSpeedValues/totalSpeedValues;
        }


    }

    //setting minimum and maximum longitudinal acceleration
    public void setMinMaxLongitudinalAcceleration(double longitudinalAcceleration){
        if(maxLongAcceleration<longitudinalAcceleration)
            maxLongAcceleration = longitudinalAcceleration;
        if(minLongAcceleration>longitudinalAcceleration)
            minLongAcceleration = longitudinalAcceleration;
    }

    public void setEndCoordinates(double latitude, double longitude){
        this.gpsLatitudeEnd = latitude;
        this.gpsLongitudeEnd = longitude;
        if(type == SegmentType.straight)
            calculateStraightSegmentLength();
    }

    //when the end coordinates are set, calculate the straight segment length in km
    public void calculateStraightSegmentLength(){
        /*
            Reference: Wikipedia
            Title: Program for distance between two points on earth
            Author: Twinkl Bajaj
            Date: Apr 09, 2021
            Availability: https://www.geeksforgeeks.org/program-distance-two-points-earth/#:~:text=For%20this%20divide%20the%20values,is%20the%20radius%20of%20Earth
         */
        //do calculation
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double tempgpsLongitudeStart = Math.toRadians(gpsLongitudeStart);
        double tempgpsLongitudeEnd = Math.toRadians(gpsLongitudeEnd);
        double tempgpsLatitudeStart = Math.toRadians(gpsLatitudeStart);
        double tempgpsLatitudeEnd = Math.toRadians(gpsLatitudeEnd);

        // Haversine formula
        double dlon = tempgpsLongitudeEnd - tempgpsLongitudeStart;
        double dlat = tempgpsLatitudeEnd - tempgpsLatitudeStart;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(tempgpsLatitudeStart) * Math.cos(tempgpsLatitudeEnd)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
//        return(c * r);
        lengthOfStraight = c * r;
//        System.out.println("Length of segment: " + lengthOfStraight);

    }

    //constructor for curve
    public SegmentData(double currentTimeDifference, SegmentType type, double gpsLatitudeStart, double gpsLongitudeStart, double speed, CurveDirection direction, double latAcceleration, double angle){
        this.currentTimeDifference = currentTimeDifference;
        this.type = type;
        this.gpsLatitudeStart = gpsLatitudeStart;
        this.gpsLongitudeStart = gpsLongitudeStart;
        this.totalSpeedValues = 1;
        this.sumSpeedValues = speed;
        this.avgVehicleSpeed = speed;
        this.direction = direction;
        this.maxLatAccerelation = latAcceleration;
        this.minLatAcceleration = latAcceleration;
        this.maxSteeringAngle = angle;
        this.degreeOfCurve = angle;
    }

    //setters For Curve segment
    public void setMinMaxLateralAcceleration(double lateralAcceleration){
        if(maxLatAccerelation<lateralAcceleration)
            maxLatAccerelation = lateralAcceleration;
        if(minLatAcceleration>lateralAcceleration)
            minLatAcceleration = lateralAcceleration;
    }
    //set maximum steering angle
    public void setAngleValues(double angle){
        if(Math.abs(maxSteeringAngle)<Math.abs(angle))
            maxSteeringAngle = angle;
    }

    //calculate total degrees of curve taken
    public void setDegreesOfCurve(double angle){

        degreeOfCurve += angle;

    }

    public SegmentType getType(){
        return type;
    }

    public double getGpsLatitudeStart() {
        return gpsLatitudeStart;
    }

    public double getGpsLongitudeStart() {
        return gpsLongitudeStart;
    }
    public String print(){
        String toPrint="";
        toPrint = "Segment Type: " + getType() + "\n" + "Starting Latitude(°): "
                + String.valueOf(getGpsLatitudeStart() ) + "\nStarting longitude(°): " +String.valueOf(getGpsLongitudeStart())
                + "\nEnd Latitude(°): " + getGpsLatitudeEnd() + "\nEnd Longitude(°): " + getGpsLongitudeEnd() + "\n" +"Average speed(km/h): " + getAvgVehicleSpeed();

        if(getType()==SegmentType.straight){
            toPrint += "\n"+ "Maximum vehicle speed(km/h): " + getMaxSpeed()+
                    "\n"+ "Minimum vehicle speed(km/h): " + getMinSpeed()+"\n"+ "Maximum longitudinal acceleration(m/s²): " + getMaxLongAcceleration()+
                    "\n"+ "Minimum longitudinal acceleration(m/s²): " + getMinLongAcceleration()+ "\n" + "Length of the straight(km): " + getLengthOfStraight() + "\n\n";
        }
        else {
            toPrint+="\n"+ "Curve direction: " + getDirection()+
                    "\n"+ "Maximum lateral acceleration(m/s²): " + getMaxLatAccerelation()+
                    "\nMinimum lateral acceleration(m/s²): " + getMinLatAcceleration()+ "\n" + "Degrees of the curve(°): " + getDegreeOfCurve() +"\n" +
                    "Maximum steering wheel angle(°): " + getMaxSteeringAngle() + "\n\n";
        }
        return toPrint;
    }
}
