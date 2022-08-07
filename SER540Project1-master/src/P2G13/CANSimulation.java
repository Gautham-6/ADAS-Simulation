package P2G13;

import java.util.ArrayList;
import java.util.List;


//This class is responsible for reading the data from files, starting the simulation
//and sending the values to the receiver class
public class CANSimulation {
    List<CANframe> canFrames;
    List<GPScoordinates> gpsCoordinates;
    private SteeringWheelAngle steeringWheelAngle;
    private VehicleSpeed vehicleSpeed;
    private VehicleYawRate yawRate;
    private VehicleLongitudinalAcceleration longitudinalAcceleration;
    private VehicleLateralAcceleration lateralAcceleration;
    int pointer;
    int gpsPointer;
    public boolean run = true;
    int count=0;

    //Default constructor
    CANSimulation() {
        steeringWheelAngle = new SteeringWheelAngle("0003", new int[]{1, 5, 2, 0}, 14,
                "Steering wheel angle", 0x3FFF, new double[]{-2048.0, 2047.0}, "°", 0.5);

        vehicleSpeed = new VehicleSpeed("019F", new int[]{1, 3, 2, 0}, 12,
                "Displayed vehicle speed", 0xFFF, new double[]{0.0, 409.4}, "km/h", 0.1);

        yawRate = new VehicleYawRate("0245", new int[]{1, 7, 2, 0}, 16,
                "Vehicle yaw rate", 0xFFFF, new double[]{-327.68, 327.66}, "°/s", 0.01);

        longitudinalAcceleration = new VehicleLongitudinalAcceleration("0245", new int[]{5, 7, 5, 0}, 8,
                "Vehicle longitudinal acceleration", 0xFF, new double[]{-10.24, 10.08}, "m/s²", 0.08);

        lateralAcceleration = new VehicleLateralAcceleration("0245", new int[]{6, 7, 6, 0}, 8,
                "Vehicle lateral acceleration", 0xFF, new double[]{-10.24, 10.08}, "m/s²", 0.08);


        //getting all the frames by reading from the file
        CANParser parser = new CANParser();
        try{
            parser.readDataFromFile("src/P2G13/19 CANmessages.trc");
            canFrames = parser.getTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //getting all the gps coordinates by reading from the file
        GPSParser g = new GPSParser();
        try{
            g.ReadGPSData("src/P2G13/GPStrace.txt");
            gpsCoordinates = g.getGpsCoordinates();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    
    public void setRun(boolean value)
    {
        run = value;
    }
    
    public void startSimulation(SensorDataReceiver receiver) {
        //Retrieving the current time of the system when
        //the user initiated the simulation
        if(count>=1) {

            receiver.setFirstrun(false);
            receiver.setInit();
            //receiver.resetValue();
        }
        count++;
        long initiationTime = System.nanoTime();

        long onGoingTime;
        double currentTimeDifference = 0.0;
        
        pointer=0;
        gpsPointer=0;

        while(pointer<canFrames.size() && run){
            receiver.receiveCurrentTime(currentTimeDifference);
            CANframe frame = canFrames.get(pointer);
            ArrayList<SensorName> sensorNames = new ArrayList<>();
            ArrayList<String> sensorValues = new ArrayList<>();
            if(frame.getTimeOffsetInMS()<=currentTimeDifference){
                if(frame.getFrameID()==steeringWheelAngle.getFrameID()){
                    sensorNames.add(SensorName.SteeringWheelAngle);
                    SteeringWheelAngle obj = (SteeringWheelAngle) frame;
                    sensorValues.add(obj.getSteeringWheelAngle() + obj.getUnit());
                    sendValuesToReceiver(sensorValues.get(0), frame.getTimeOffsetInMS(), sensorNames.get(0), receiver);
                    pointer++;
                }
                else if(frame.getFrameID()==vehicleSpeed.getFrameID()){
                    sensorNames.add(SensorName.Speed);
                    VehicleSpeed obj = (VehicleSpeed) frame;
                    sensorValues.add(obj.getVehicleSpeed() + obj.getUnit());
                    sendValuesToReceiver(sensorValues.get(0), frame.getTimeOffsetInMS(), sensorNames.get(0), receiver);
                    pointer++;
                }
                else if(frame.getFrameID()==yawRate.getFrameID()){
                    VehicleYawRate yawRate = (VehicleYawRate) frame;
                    sensorNames.add(SensorName.YawRate);
                    sensorValues.add(yawRate.getYawRate() + yawRate.getUnit());
                    sendValuesToReceiver(sensorValues.get(0), yawRate.getTimeOffsetInMS(), sensorNames.get(0), receiver);
                    pointer++;

                    //if the current frame is yaw rate then we also check for the next two values
                    //because they will be lateral and longitudinal acceleration
                    CANframe  longFrame = canFrames.get(pointer);
                    VehicleLongitudinalAcceleration vehicleLongitudinalAcceleration = (VehicleLongitudinalAcceleration) longFrame;
                    sensorNames.add(SensorName.LongitudinalAcceleration);
                    sensorValues.add(vehicleLongitudinalAcceleration.getLongitudinalAcceleration() + vehicleLongitudinalAcceleration.getUnit());
                    sendValuesToReceiver(sensorValues.get(1), longFrame.getTimeOffsetInMS(), sensorNames.get(1), receiver);
                    pointer++;

                    CANframe latFrame = canFrames.get(pointer);
                    VehicleLateralAcceleration vehicleLateralAcceleration = (VehicleLateralAcceleration) latFrame;
                    sensorNames.add(SensorName.LateralAcceleration);
                    sensorValues.add(vehicleLateralAcceleration.getLateralAcceleration() + vehicleLateralAcceleration.getUnit());
                    sendValuesToReceiver(sensorValues.get(2), latFrame.getTimeOffsetInMS(), sensorNames.get(2), receiver);
                    pointer++;
                }

            }

            //checking for gps change at any time
            if(gpsPointer<gpsCoordinates.size()){
                GPScoordinates coordinates = gpsCoordinates.get(gpsPointer);
                if(coordinates.getTimeToSendAt()<=currentTimeDifference){
                    sendValuesToReceiver(String.valueOf(coordinates.getLatitude()), coordinates.getTimeToSendAt(),SensorName.Latitude, receiver);
                    sendValuesToReceiver(String.valueOf(coordinates.getLongitude()), coordinates.getTimeToSendAt(),SensorName.Longitude, receiver);
                    gpsPointer++;
                }
            }


            //Retrieving the current time of the system at
            //any given point
            onGoingTime = System.nanoTime();

            //calculating the difference(offset) between onGoingTime and initiationTime
            currentTimeDifference = onGoingTime-initiationTime;
            //converting time from nanosecond to millisecond
            currentTimeDifference = Math.round(currentTimeDifference/100000.0);
            currentTimeDifference = currentTimeDifference/10.0;
        }
    }

    //function to send values to the SensorDataReceiver class
    public void sendValuesToReceiver(String sensorValue, double timeOffset, SensorName identifier, SensorDataReceiver receiver){
        receiver.receive(sensorValue, timeOffset, identifier);
    }
}
