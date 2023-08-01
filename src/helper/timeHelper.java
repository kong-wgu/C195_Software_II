package helper;

import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class timeHelper {

    public static String convertTime(String datetime){
        String test = "Test";

        return test;

    }

    public static ObservableList<String> getAppointmentTimeList(){

        ObservableList<String> timeList;
        LocalTime time;
        // Going to add 24 hours
        for(int i = 0; i < 24; i++){

            for(int j = 0; j < 46; j+15) {
                if(j==0){String padded = String.format("%02d", j);}
                time = LocalTime.parse(i + "0", DateTimeFormatter.ofPattern("HH:mm"));
            }
        }


    }

}
