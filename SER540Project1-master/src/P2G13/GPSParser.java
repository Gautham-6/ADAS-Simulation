package P2G13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GPSParser {
    private List<GPScoordinates> gpsCoordinates;


    public GPSParser(){
        gpsCoordinates = new ArrayList<>();
    }

    public void ReadGPSData(String filePath) throws FileNotFoundException
    {
        File gpdData = new File(filePath);

        Scanner reader = new Scanner(gpdData);

        double timeToAdd = 0;
        while(reader.hasNextLine())
        {
            String line = reader.nextLine();
            String[] coordinates = line.split(",");
            Double latitude = Double.valueOf(coordinates[0]);
            int indexOfSC = coordinates[1].indexOf(';');
            if(indexOfSC == -1)
            {
                indexOfSC = coordinates[1].length();

            }
            String tempLongitude = coordinates[1].substring(0, indexOfSC);
            Double longitude = Double.valueOf(tempLongitude);
            gpsCoordinates.add(new GPScoordinates(timeToAdd,latitude, longitude));
            timeToAdd +=1000.0;
        }

//        for(GPScoordinates l: gpsCoordinates)
//        {
//            System.out.print(l.getLatitude()+ "--");
//            System.out.print(l.getLongitude());
//            System.out.println();
//        }
    }
    List<GPScoordinates> getGpsCoordinates(){
        return gpsCoordinates;
    }
}
