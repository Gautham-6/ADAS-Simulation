package P2G13;

/*
CANTest class: to run tests given in the assignment 1
 */
public class CANTest {

    //method to run the getNextMessage a number of times and output the values
    public void runGetNext(int numberOfTimes, CANParser parser){
        int i=1;
        while(i<=numberOfTimes){
            CANframe frame = parser.getNextMessage();
            System.out.printf("%3s) ", i);
            parser.print(frame);
            i++;
        }

    }
    //method to call the parser's resetNextMessage method
    public void reset(CANParser parser){
        parser.resetNextMessage();
    }
}
