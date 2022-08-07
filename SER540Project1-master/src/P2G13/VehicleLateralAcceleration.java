package P2G13;

/*
VehicleLateralAcceleration class: Child class of CANframe. It contains all the values of CANframe class
and the lateral acceleration to be determined.
 */
public class VehicleLateralAcceleration extends CANframe{
    private double lateralAcceleration;

    //parameterized constructor
    public VehicleLateralAcceleration(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize) {
        super(frameID, dataFieldLocation, dataFieldSize, dataFieldDescription, maxValueOfData, valueRange, unit, stepSize);
    }

    //copy constructor
    public VehicleLateralAcceleration(VehicleLateralAcceleration vehicleLateralAcceleration){
        super(vehicleLateralAcceleration);
    }

    //set the lateral acceleration value after being calculated
    public void setLateralAcceleration(double lateralAcceleration){
        this.lateralAcceleration = lateralAcceleration;
    }

    //print the values from the parent class and the lateral acceleration value
    public void print(){
        super.print();
        System.out.printf("Lateral Acceleration: %10.2f%s", lateralAcceleration, getUnit());
        System.out.println();
    }

    public double getLateralAcceleration() {
        return lateralAcceleration;
    }
}
