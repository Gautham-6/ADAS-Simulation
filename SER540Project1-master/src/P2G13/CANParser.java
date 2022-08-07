package P2G13;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
/*
CANParser class: Performs the parsing of the contents of the
file "CANmessages.trc", then create a list of frames to print and/or get next CAN frame to be executed
 */

public class CANParser {
    private List<CANMessage> messageList;
    private List<CANframe> frameList;
    private SteeringWheelAngle steeringWheelAngle;
    private VehicleSpeed vehicleSpeed;
    private VehicleYawRate yawRate;
    private VehicleLongitudinalAcceleration longitudinalAcceleration;
    private VehicleLateralAcceleration lateralAcceleration;
    private Iterator it;

    //default constructor
    CANParser(){
        messageList = new ArrayList<>();
        frameList = new LinkedList<>();

        //initialize different objects based on the values given in "CAN Frames Info.txt"

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


    }

    //method to read data from the file and put all the frames in a list
    void readDataFromFile(String fileName) throws IOException{

        try{
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String st = bufferedReader.readLine();
            while(st.charAt(0) == ';')
                st = bufferedReader.readLine();

            while((st = bufferedReader.readLine()) != null){
                st = st.trim();
                String[] messageStr = st.split("\\s+");
                String[] data = new String[messageStr.length-5];
                for(int i=5;i<messageStr.length;i++)
                    data[i-5] = messageStr[i];
                CANMessage message = new CANMessage(Double.parseDouble(messageStr[1]), messageStr[2], messageStr[3], Integer.parseInt(messageStr[4]), data);
                String messageFrameId = message.getFrameID();
                if(messageFrameId.equals(steeringWheelAngle.getFrameID())){
                    message.calculateValue(steeringWheelAngle);
                    messageList.add(message);
                    SteeringWheelAngle obj = new SteeringWheelAngle(steeringWheelAngle);
                    obj.setSteeringWheelAngle(message.getCalculatedValue());
                    obj.setTimeOffsetInMS(message.getTimeOffsetInMS());
                    frameList.add(obj);
                }
                else if(messageFrameId.equals(vehicleSpeed.getFrameID())){
                    message.calculateValue(vehicleSpeed);
                    messageList.add(message);
                    VehicleSpeed obj = new VehicleSpeed(vehicleSpeed);
                    obj.setVehicleSpeed(message.getCalculatedValue());
                    obj.setTimeOffsetInMS(message.getTimeOffsetInMS());
                    frameList.add(obj);
                }
                else if(messageFrameId.equals(yawRate.getFrameID())){
                    message.calculateValue(yawRate);
                    messageList.add(message);
                    VehicleYawRate obj = new VehicleYawRate(yawRate);
                    obj.setYawRate(message.getCalculatedValue());
                    obj.setTimeOffsetInMS(message.getTimeOffsetInMS());
                    frameList.add(obj);

                    message.calculateValue(longitudinalAcceleration);
                    messageList.add(message);
                    VehicleLongitudinalAcceleration obj1 = new VehicleLongitudinalAcceleration(longitudinalAcceleration);
                    obj1.setLongitudinalAcceleration(message.getCalculatedValue());
                    obj1.setTimeOffsetInMS(message.getTimeOffsetInMS());
                    frameList.add(obj1);


                    message.calculateValue(lateralAcceleration);
                    messageList.add(message);
                    VehicleLateralAcceleration obj2 = new VehicleLateralAcceleration(lateralAcceleration);
                    obj2.setLateralAcceleration(message.getCalculatedValue());
                    obj2.setTimeOffsetInMS(message.getTimeOffsetInMS());
                    frameList.add(obj2);
                }




            }
            //initialize the iterator to the first frame. This will return the first frame
            //when getNextMessage() is called for the first time
            it = frameList.iterator();

        }
        catch(Error e){
            System.out.println("Unable to locate the file due the error: " + e.getLocalizedMessage());
        }
    }

    //method to print all the relevant values parsed
    public void printTrace(){
        for(CANframe frame: frameList){
            print(frame);
        }

    }

    //method to print a frame's data
    public void print(CANframe frame){
        String frameId = frame.getFrameID();
        String frameDesc = frame.getDataFieldDescription();
        if(frameId.equals(steeringWheelAngle.getFrameID())) {
            SteeringWheelAngle angle = (SteeringWheelAngle) frame;
            angle.print();
        }
        else if(frameId.equals(vehicleSpeed.getFrameID())){
            VehicleSpeed speed = (VehicleSpeed) frame;
            speed.print();
        }
        else if(frameId.equals(yawRate.getFrameID())&&frameDesc.equals(yawRate.getDataFieldDescription())){
            VehicleYawRate rate = (VehicleYawRate) frame;
            rate.print();
        }
        else if(frameId.equals(longitudinalAcceleration.getFrameID())&&frameDesc.equals(longitudinalAcceleration.getDataFieldDescription())){
            VehicleLongitudinalAcceleration longitunal = (VehicleLongitudinalAcceleration) frame;
            longitunal.print();
        }
        else if(frameId.equals(lateralAcceleration.getFrameID())&&frameDesc.equals(lateralAcceleration.getDataFieldDescription())){
            VehicleLateralAcceleration lateral = (VehicleLateralAcceleration) frame;
            lateral.print();
        }
    }

    //method to return the list of frames that was created by reading the data from the file.
    public List<CANframe> getTrace(){
        return frameList;
    }

    //method to get the next CAN frame to be sent after the current frame was transmitted
    public CANframe getNextMessage(){
        if(it.hasNext())
            return (CANframe) it.next();
        return null;
    }

    //method to reset the counter of the frames sent and start again from the first one
    public void resetNextMessage(){
        System.out.println("iterator reset");
        it = frameList.iterator();
    }

}

