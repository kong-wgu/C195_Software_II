package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class timeHelper {

    public static String convertTime(String datetime){
        String test = "Test";

        return test;

    }

    public static ObservableList<String> getAppointmentTimeList(){

        ObservableList<String> timeList = FXCollections.observableArrayList();
        LocalTime time;
        String hours_padded = "";
        String minute_padded = "";
        String results;
        // Going to add 24 hours
        for(int i = 0; i < 24; i++){
            if(i < 10){
                hours_padded = String.format("%02d", i);
            }else{
                hours_padded = Integer.toString(i);
            }
            for(int j = 0; j < 46; j = j+15) {
                if(j==0){
                    minute_padded = String.format("%02d", j);
                }else{
                    minute_padded = Integer.toString(j);
                }

                results = hours_padded + ":" + minute_padded;

                time = LocalTime.parse( results , DateTimeFormatter.ofPattern("HH:mm"));
                results = time.toString();
                timeList.add(results);
            }
        }

        return timeList;
    }

}
