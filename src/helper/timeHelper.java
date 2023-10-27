package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/***
 * This class is a helper class that will calculate the choicebox for times possible for appointments.
 * Set to help with retrieving possible times.
 */
public class timeHelper {

    /***
     * Gets a total list of all possible appointment times, will be used for the appointment time choicebox on the forms.
     * @return
     */
    public static ObservableList<String> getAppointmentTimeList(){

        // sets the list to collect all appointment times
        ObservableList<String> timeList = FXCollections.observableArrayList();

        // Sets the variables so it be can be referenced in the for loop.
        LocalTime time;
        String hours_padded = "";
        String minute_padded = "";
        String results;

        // Starts from 00 to 24 hours.
        for(int i = 0; i < 24; i++){
            if(i < 10){
                hours_padded = String.format("%02d", i);
            }else{
                hours_padded = Integer.toString(i);
            }

            // Adds on the 15 minute increment, only stops after the 45 minute mark has been reached.
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
