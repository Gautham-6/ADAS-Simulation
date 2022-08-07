package P2G13;

import java.util.ArrayList;
import java.util.List;

//enum to differenciate between the sensors and update the gui and the console accordingly
enum SensorName{
    None,
    SteeringWheelAngle,
    Speed,
    YawRate,
    LateralAcceleration,
    LongitudinalAcceleration,
    Latitude,
    Longitude
}

//This class is responsible for starting the simulation and also
//receive the values continuously from the simulation class and display on the gui
//as well as the console

public class SensorDataReceiver {
    SegmentType segmentTypeforHMI = SegmentType.straight;
    CANSimulation simulator;


    
    double distance = 0.0;
    boolean isFirstRun = true;
    double currentTimeOffset;
    double currentTimeInMS;
    String vehicleSpeedValue;
    String steeringWheelAngleValue;
    String yawRateValue;
    String lateralAccelerationValue;
    String longitudinalAccelerationValue;
    String latitudeValue;
    String longitudeValue;
    List<SegmentData> curveSegmentDetails = new ArrayList<SegmentData>();
    int curveSegmentIndex = 1;

    SimulatorGUI gui;

    List<SegmentData> segmentDataList;
    SegmentData currentSegmentData;
    boolean isFirstSegment;
    public SensorDataReceiver(SimulatorGUI gui) {
        this.gui = gui;
        try{
            simulator = new CANSimulation();
            System.out.printf("%-15s|%-20s|%-5s|%-20s|%-20s|%-20s|%-20s|%-20s\n",
                    "CurrentTime", "VehicleSpeed", "SteeringAngle", "YawRate", "LatAcc", "LongAcc", "Lat", "Long");

            vehicleSpeedValue = "-";
            steeringWheelAngleValue = "-";
            yawRateValue = "-";
            lateralAccelerationValue = "-";
            longitudinalAccelerationValue = "-";
            latitudeValue = "-";
            longitudeValue = "-";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        initializeSegmentVariables();
    }

    //method to initialize segment variables
    public void initializeSegmentVariables()
    {
        segmentDataList = new ArrayList<>();
        currentSegmentData = new SegmentData(SegmentType.straight);
        isFirstSegment = true;
    }

    //receive the current time difference between initiation time
    //and current from the simulation class
    void receiveCurrentTime(double currentTimeInMS){
        this.currentTimeInMS = currentTimeInMS;
    }

    //method to determine if the simulation was running
    //for the first time
    public void setFirstrun(boolean value)
    {
        isFirstRun = value;

    }

    //method to set gui components to empty strings
    public void resetValue()
    {
    	gui.resetSegmentData("");

    }

    //receive values from the simulator and display the contents
    //in the console and the gui accordingly
    void receive(String sensorValue, double timeOffset, SensorName identifier){

        //checking for the type of sensor for which the value was received
        //and set that specific value
        switch (identifier){
            case None:
                break;
            case SteeringWheelAngle:
                steeringWheelAngleValue = sensorValue;

                try {
                    gui.setWheelAngle(steeringWheelAngleValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(currentSegmentData.getType() == SegmentType.curve)
                    currentSegmentData.setAngleValues(extractValues(steeringWheelAngleValue));
                detectCurve();
                break;
            case Speed:
                vehicleSpeedValue = sensorValue;
                try {
                    gui.setVehicleSpeed(vehicleSpeedValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentSegmentData.setSpeedValues(extractValues(vehicleSpeedValue));
                break;
            case YawRate:
                yawRateValue = sensorValue;
                try {
                    gui.setYawRate(yawRateValue);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                detectCurve();
                break;
            case LateralAcceleration:
                lateralAccelerationValue = sensorValue;
                try {
                    gui.setLateralAcceleration(lateralAccelerationValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(currentSegmentData.getType()==SegmentType.curve)
                    currentSegmentData.setMinMaxLateralAcceleration(extractValues(lateralAccelerationValue));
                break;
            case LongitudinalAcceleration:
                longitudinalAccelerationValue = sensorValue;
                try {
                    gui.setLongitudinalAcceleration(longitudinalAccelerationValue);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(currentSegmentData.getType()==SegmentType.straight)
                    currentSegmentData.setMinMaxLongitudinalAcceleration(extractValues(longitudinalAccelerationValue));
                break;
            case Latitude:
                latitudeValue = sensorValue;
                try {
                    gui.setLatitude(latitudeValue);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //for the first run when start coordinates is not set
                //execute this
                if(isFirstSegment&&currentSegmentData.getGpsLatitudeStart()==0.0)
                    currentSegmentData.setGpsLatitudeStart(getLatLong(latitudeValue));
                //if segment type is curve then add degrees when the latitude changes
                if(currentSegmentData.getType() == SegmentType.curve)
                    currentSegmentData.setDegreesOfCurve(extractValues(steeringWheelAngleValue));

                break;
            case Longitude:
                longitudeValue = sensorValue;
                try {
                    gui.setLongitude(longitudeValue);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //for the first run when start coordinates is not set
                //execute this
                if(isFirstSegment&&currentSegmentData.getGpsLongitudeStart()==0.0)
                    currentSegmentData.setGpsLongitudeStart(getLatLong(longitudeValue));


                break;
        }

//        print the values in the console
         System.out.printf("\r%-15.1f|%-20s|%-5s|%-20s|%-20s|%-20s|%-20s|%-20s",
                 currentTimeInMS, vehicleSpeedValue, steeringWheelAngleValue,
                 yawRateValue, lateralAccelerationValue, longitudinalAccelerationValue, latitudeValue, longitudeValue);


        //set the time field in the gui
        try {
            gui.setTime(String.valueOf(currentTimeInMS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        //running the hmi for the second and subsequent runs
        if(!isFirstRun)
        {
//            curveSegmentIndex = 1;
            double latitude = Double.parseDouble(latitudeValue);
            double longitude = Double.parseDouble(longitudeValue);

           
            
            if(curveSegmentIndex < curveSegmentDetails.size())
            {
                double lat1 = curveSegmentDetails.get(curveSegmentIndex).getGpsLatitudeStart();
                double long1 = curveSegmentDetails.get(curveSegmentIndex).getGpsLongitudeStart();

                double distance = calculateDistance(lat1, latitude, long1, longitude, 0.0, 0.0);
                distance = Math.round(distance * 100D) / 100D;

                //if the distance is less than 60, print the distances to the next segment
                if(distance <= 60.0)
                {
                    try {
                        if(curveSegmentDetails.get(curveSegmentIndex).getType() == SegmentType.curve)
                        {
                            String s = "Curve " + String.valueOf(curveSegmentDetails.get(curveSegmentIndex).getDirection()) + " in " + distance + " meters ";
                            gui.setCurveDetails(s);
                        }

                        else
                        {
//                        	if(curveSegmentIndex == 0) {
//                        		gui.setCurveDetails("You are going in a straight lane.");
//                        	}
                        	String s = "Straight " + "in " + distance + " meters";
                            gui.setCurveDetails(s);

                        }
                        
                        
                    } catch (Exception e) 
                    {
                        e.printStackTrace();
                    }
                    if(distance < 10.0)
                    {
                        gui.setCurveDetails(" ");
                        curveSegmentIndex++;
                    }
                }
            }
        }
    }

    //
    public double calculateDistance(double lat1, double lat2, double lon1,
            double lon2, double el1, double el2) {

        /*
            Reference:
            Title: Calculating distance between two points, using latitude longitude?
            Author: David George
            Date: May 28, 2013
            Availability: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
         */
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    //method to stop the simulation
    public void stopSimulation(){
        printAllSegments();


        initializeSegmentVariables();
        simulator.setRun(false);

    }

    //start simulation on start button pressed
    void startSimulationButtonPressed(){
        
       simulator.setRun(true);
       simulator.startSimulation(this);
    }

    //for extracting values from the values which were received from the simulation
    //class with unit
    Double extractValues(String str){
        if(str!="-"){
            String[] sensorValues = str.split("[^-?\\d+\\.\\d+]");
            Double doubleValue = 0.0;
            doubleValue = Double.parseDouble(sensorValues[0]);
            return doubleValue;
        }
       return 0.0;
    }

    //returning value of latitude and longitude
    double getLatLong(String s)
    {
        return Double.valueOf(s);
    }

    //method to detect curve and change segment type
    void detectCurve(){
        StringBuilder sb = new StringBuilder();

        Double wheelAngle = extractValues(steeringWheelAngleValue);
        Double yawRate = extractValues(yawRateValue);
        if(Math.abs(wheelAngle)>20.0&&Math.abs(yawRate)>2.9)
        {
            if(currentSegmentData.getType() == SegmentType.curve)
            {
                currentSegmentData.setAngleValues(wheelAngle);
            }
            else
            {
                //add current straight segment to list
                currentSegmentData.setEndCoordinates(getLatLong(latitudeValue), getLatLong(longitudeValue));
                segmentDataList.add(currentSegmentData);
                String temp = "";
                temp += currentSegmentData.print();
                sb.append(temp);
                CurveDirection direction = CurveDirection.right;
                if(wheelAngle<0)
                    direction = CurveDirection.left;
                if(isFirstRun == true)
                {
                    curveSegmentDetails.add(currentSegmentData);
                }

                //create curve object
                currentSegmentData = new SegmentData(currentTimeInMS, SegmentType.curve, getLatLong(latitudeValue),
                getLatLong(longitudeValue), extractValues(vehicleSpeedValue),
                        direction, extractValues(lateralAccelerationValue), wheelAngle);
            }
        }
        else
        {
            if(currentSegmentData.getType()==SegmentType.straight){

            }
            else{
                //add current curve object to list
                currentSegmentData.setEndCoordinates(getLatLong(latitudeValue), getLatLong(longitudeValue));

                String temp1 = "";
                 temp1+=currentSegmentData.print();
                 sb.append(temp1);
                
                segmentDataList.add(currentSegmentData);
                if(isFirstRun == true)
                {
                    curveSegmentDetails.add(currentSegmentData);
                }
                
                //create straight object
                currentSegmentData = new SegmentData(currentTimeInMS, SegmentType.straight,
                getLatLong(latitudeValue), getLatLong(longitudeValue),
                        extractValues(vehicleSpeedValue), extractValues(longitudinalAccelerationValue));
            }
        }
        if(sb.length() > 0)
        {
            gui.setSegmentData(sb.toString());
        }
    }

    //setting the index of segment list to intitial
    void setInit() {
    	curveSegmentIndex = 1;
    }
    void printAllSegments(){
        System.out.println();
        for(SegmentData segment: segmentDataList)
            System.out.println(segment.print());
    }
}
