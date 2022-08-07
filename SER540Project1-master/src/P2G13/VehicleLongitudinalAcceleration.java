package P2G13;/*
VehicleLongitudinalAcceleration class: Child class of CANframe. It contains all the values of CANframe class
and the longitudinal acceleration to be determined.
 */

public class VehicleLongitudinalAcceleration extends CANframe{
    private double longitudinalAcceleration;

    //parameterized constructor
    public VehicleLongitudinalAcceleration(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize) {
        super(frameID, dataFieldLocation, dataFieldSize, dataFieldDescription, maxValueOfData, valueRange, unit, stepSize);
    }

    //copy constructor
    public VehicleLongitudinalAcceleration(VehicleLongitudinalAcceleration vehicleLongitudinalAcceleration){
        super(vehicleLongitudinalAcceleration);
    }

    //set the longitudinal acceleration value after being calculated
    public void setLongitudinalAcceleration(double longitudinalAcceleration){
        this.longitudinalAcceleration = longitudinalAcceleration;
    }

    //print the values from the parent class and the longitudinal acceleration value
    public void print(){
        super.print();
        System.out.printf("Longitudinal acceleration: %5.2f%s", longitudinalAcceleration, getUnit());
        System.out.println();
    }

    public double getLongitudinalAcceleration() {
        return longitudinalAcceleration;
    }
}
