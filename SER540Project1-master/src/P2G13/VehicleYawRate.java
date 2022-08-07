package P2G13;

/*
VehicleYawRate class: Child class of CANframe. It contains all the values of CANframe class
and the vehicle yaw rate to be determined.
 */
public class VehicleYawRate extends CANframe{
    private double yawRate;

    //parameterized constructor
    public VehicleYawRate(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize) {
        super(frameID, dataFieldLocation, dataFieldSize, dataFieldDescription, maxValueOfData, valueRange, unit, stepSize);
    }

    //copy constructor
    public VehicleYawRate(VehicleYawRate vehicleYawRate){
        super(vehicleYawRate);
    }

    //set the yaw rate value after being calculated
    public void setYawRate(double yawRate){
        this.yawRate = yawRate;
    }

    //print the values from the parent class and the yaw rate value
    public void print(){
        super.print();
        System.out.printf("Yaw Rate: %22.2f%s", yawRate, getUnit());
        System.out.println();
    }
    public double getYawRate(){
        return yawRate;
    }
}
