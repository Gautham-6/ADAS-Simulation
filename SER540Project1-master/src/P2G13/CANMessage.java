package P2G13;

import java.util.Arrays;
/*
CANMessage class: to parse the values in the "CANmessages.trc file in a
formatted way and also to calculate the values based on the data length, location of data and other parameters
 */
public class CANMessage {
    private double timeOffSetInMS;
    private String frameID;
    private String type;
    private int dataLength;
    private String[] data;
    private double calculatedValue;

    //parameterized constructor
    CANMessage(double timeOffSetInMS, String type, String frameID, int dataLength, String[] data){
        this.timeOffSetInMS = timeOffSetInMS;
        this.frameID = frameID;
        this.type = type;
        this.dataLength = dataLength;
        this.data = data;


    }


    //calculate and retrieve value of the desired frame
    // with the help of data field location and data in the message
    public void calculateValue(CANframe frame){
        int[] dataFieldLocation = frame.getDataFieldLocation();
        int dataStartPoint = dataFieldLocation[0]-1;
        int dataEndPoint = dataFieldLocation[2]-1;
        String[] dataForCalculation = Arrays.copyOfRange(this.data, dataStartPoint, dataEndPoint+1);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=dataStartPoint;i<=dataEndPoint;i++)
            stringBuilder.append(data[i]);
        int dataInInt = Integer.parseInt(stringBuilder.toString(), 16);
        int fieldSize = frame.getDataFieldSize();

        int bitmask = ~(-1<<fieldSize);

        dataInInt = dataInInt & bitmask;
        dataInInt = dataInInt>>(dataFieldLocation[3]);

        calculatedValue = dataInInt * frame.getStepSize() + frame.getValueRange()[0];
    }

    //getters
    public String getFrameID(){
        return frameID;
    }
    public double getCalculatedValue(){
        return calculatedValue;
    }

    public double getTimeOffsetInMS(){
        return timeOffSetInMS;
    }
}
