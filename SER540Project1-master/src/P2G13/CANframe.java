package P2G13;

/*
 CANframe class: to store the five different types of CAN frames that needs to be parsed and evaluated for the scope of this assignment
 */
public class CANframe {
    private String frameID;
    private int[] dataFieldLocation;
    private int dataFieldSize;
    private String dataFieldDescription;
    private int maxValueOfData;
    private double[] valueRange;
    private String unit;
    private double stepSize;
    private double timeOffsetInMS;

    //parameterized constructor
    public CANframe(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize){
        this.frameID = frameID;
        this.dataFieldLocation = dataFieldLocation;
        this.dataFieldSize = dataFieldSize;
        this.dataFieldDescription = dataFieldDescription;
        this.maxValueOfData = maxValueOfData;
        this.valueRange = valueRange;
        this.unit = unit;
        this.stepSize = stepSize;
    }

    //copy constructor
    public CANframe(CANframe frame){
        this.frameID = frame.frameID;
        this.dataFieldLocation = frame.dataFieldLocation;
        this.dataFieldSize = frame.dataFieldSize;
        this.dataFieldDescription = frame.dataFieldDescription;
        this.maxValueOfData = frame.maxValueOfData;
        this.valueRange = frame.valueRange;
        this.unit = frame.unit;
        this.stepSize = frame.stepSize;
    }

    //to print the id, offset and values of the frame
    public void print(){
        System.out.printf("frameId: %5s |\t", getFrameID());
        System.out.printf("timeOffset: %7.1f |\t", getTimeOffsetInMS());

    }

    //getters/setters for different fields
    public String getFrameID(){
        return frameID;
    }
    public int[] getDataFieldLocation(){
        return dataFieldLocation;
    }
    public int getDataFieldSize(){
        return dataFieldSize;
    }
    public double getStepSize(){
        return stepSize;
    }
    public double[] getValueRange(){
        return valueRange;
    }
    public String getDataFieldDescription(){
        return dataFieldDescription;
    }
    public void setTimeOffsetInMS(double timeOffsetInMS){
        this.timeOffsetInMS = timeOffsetInMS;
    }
    public String getUnit(){ return this.unit;}

    public double getTimeOffsetInMS(){
        return timeOffsetInMS;
    }


}
