package P2G13;

/*
VehicleSpeed class: Child class of CANframe. It contains all the values of CANframe class
and the vehicle speed to be determined.
 */
public class VehicleSpeed extends CANframe{
    private double vehicleSpeed;

    //parameterized constructor
    public VehicleSpeed(String frameID, int[] dataFieldLocation, int dataFieldSize, String dataFieldDescription, int maxValueOfData, double[] valueRange, String unit, double stepSize) {
        super(frameID, dataFieldLocation, dataFieldSize, dataFieldDescription, maxValueOfData, valueRange, unit, stepSize);
    }

    //copy constructor
    VehicleSpeed(VehicleSpeed vehicleSpeed){
        super(vehicleSpeed);
    }

    //set the vehicle speed value after being calculated
    public void setVehicleSpeed(double vehicleSpeed){

        this.vehicleSpeed = vehicleSpeed;
    }

    //print the values from the parent class and the vehicle speed value
    public void print(){
        super.print();
        System.out.printf("Vehicle speed: %17.2f%s", vehicleSpeed, getUnit());
        System.out.println();
    }

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }
}
