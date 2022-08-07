package P2G13;

import java.io.IOException;
/*
Main class: to run the program and perform tests
 */

public class Main {
    public static void main(String [] args) throws IOException {
//        CANParser parser = new CANParser();
//        parser.readDataFromFile("src/19 CANmessages.trc");
//        parser.printTrace();
//        List<CANframe> trace = parser.getTrace();
//        CANTest canTest = new CANTest();
//        canTest.runGetNext(30, parser);
//        canTest.reset(parser);
//        canTest.runGetNext(10, parser);
        //starting the simulator interface
        new SimulatorGUI();
    }
}
