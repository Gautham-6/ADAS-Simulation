package P2G13;

/*
SteeringWheelAngle class: Child class of CANframe. It contains all the values of CANframe class
and the steering wheel angle to be determined.
 */
public class SteeringWheelAngle extends CANframe{
    private double steeringWheelAngle;

    //parameterized constructor
    public SteeringWheelAngle(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize) {
        super(frameID, dataFieldLocation, dataFieldSize, dataFieldDescription, maxValueOfData, valueRange, unit, stepSize);
    }

    //copy constructor
    public SteeringWheelAngle(SteeringWheelAngle steeringWheelAngle){
        super(steeringWheelAngle);
    }

    //set the steering wheel angle value after being calculated
    public void setSteeringWheelAngle(double steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }

    //print the values from the parent class and the steering wheel angle value
    public void print(){
        super.print();
        System.out.printf("Steering wheel angle: %10.2f%s", steeringWheelAngle, getUnit());
        System.out.println();
    }

    public double getSteeringWheelAngle() {
        return steeringWheelAngle;
    }
}
