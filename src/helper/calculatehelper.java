package helper;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class calculatehelper {

    /***
     * Checks to see if the given appointment conflicts with any other appointment
     * @param allAppointments
     * @param desiredAppointment
     * @return
     * @throws NullPointerException
     */
    public static boolean conflictswithAppointments (ObservableList<Appointment> allAppointments, Appointment desiredAppointment) throws NullPointerException{
        // Sets an Appoinment placeholder for the given appointment
        Appointment holder = desiredAppointment;

        // Sets variables for the data from the placeholder appointment.
        LocalDate desiredDate = holder.getStartTime().toLocalDate();
        LocalDateTime desiredStart = holder.getStartTime();
        LocalDateTime desiredEnd = holder.getEndTime();

        // Creates a list and set that list to the given list of all appointments.
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentObservableList = allAppointments;

        // uses a function to check the working days and if the desired appointment falls within working days
        if(!IswithinWorkingDaysTimes(desiredAppointment)){return false;}

        // checks to see if the we are modify the same appointment, this way, we do not need to check other appointment time conflicts, if the original times of the appointment has not changed.
        for (Appointment app : appointmentObservableList){
            if(app.getID() == holder.getID()) {
                if (check_for_same_app_times(holderData.getHolderAppointment(), desiredStart, desiredEnd)) {
                    return true;
                }
            }
        }

        // goes through each appointment and checks to see if the desired appointments is conflicting with their times.
        for (Appointment app : appointmentObservableList) {
            LocalDate appDate = app.getStartTime().toLocalDate();
            LocalDateTime start = app.getStartTime();
            LocalDateTime end = app.getEndTime();

            if(app.getID() != holder.getID()) {

                if (appDate.isEqual(desiredDate)) {

                    if ((start.isEqual(desiredStart)) && (end.isEqual(desiredStart))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                                "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                                "Your Appointment Start Time: " + desiredStart.toString() + "\nYour Appointment End Time: " + desiredEnd.toString() +
                                "\n\nConflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                                + "\nAppointment End Time: " + app.getEndTime().toString());
                        Optional<ButtonType> rs = alert.showAndWait();
                        return false;
                    } else if ((desiredStart.isAfter(start) || desiredStart.equals(start)) && (desiredStart.isBefore(end))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                                "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                                "Your Appointment Start Time: " + desiredStart.toString() + "\nYour Appointment End Time: " + desiredEnd.toString() +
                                "\n\nConflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                                + "\nAppointment End Time: " + app.getEndTime().toString());
                        Optional<ButtonType> rs = alert.showAndWait();
                        return false;
                    } else if ((desiredEnd.isAfter(start)) && ((desiredEnd.isBefore(end)) || desiredEnd.equals(end))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                                "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                                "Your Appointment Start Time: " + desiredStart.toString() + "\nYour Appointment End Time: " + desiredEnd.toString() +
                                "\n\nConflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                                + "\nAppointment End Time: " + app.getEndTime().toString());
                        Optional<ButtonType> rs = alert.showAndWait();
                        return false;
                    } else if ((desiredStart.equals(desiredEnd) )) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment cannot have the same start and end time!");
                        Optional<ButtonType> rs = alert.showAndWait();
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /***
     * Gets the current appointments by checking which appointments fall under the current week
     * @param allAppointments
     * @return
     */
    public ObservableList<Appointment> getCurrentWeekAppointments(ObservableList<Appointment> allAppointments){
        ObservableList<Appointment> currentWeek = FXCollections.observableArrayList();

        LocalDateTime currentTime = LocalDateTime.now();

        String currentMonth = currentTime.getMonth().toString();
        String currentDayWeek = currentTime.getDayOfWeek().toString();
        int currentDayMonth = currentTime.getDayOfMonth();

        // Returns the list of the MonthDays as a list, if any appointment matches, we will know it is within the same week.
        ObservableList<Integer> week = weekListing(currentDayWeek, currentDayMonth);

        // if found, will add appointment to currentweek list
        for(Appointment app : allAppointments){
            String appMonth = app.getStartTime().getMonth().toString();
            int appMonthDay = app.getStartTime().getDayOfMonth();
            if(week.contains(appMonthDay)){
                currentWeek.add(app);
            }

        }

        return currentWeek;

    }

    /***
     * Checks to see if the appointment is same as it's own appointment times.
     * @param oldAppointment
     * @param newStartTime
     * @param newEndTime
     * @return
     */
    public static boolean check_for_same_app_times(Appointment oldAppointment, LocalDateTime newStartTime, LocalDateTime newEndTime){
        // Sets the old and new start times
        LocalDateTime oldAppStart_time = oldAppointment.getStartTime();
        LocalDateTime newAppStart_time = newStartTime;

        // Sets the old and new end times
        LocalDateTime oldAppEnd_time = oldAppointment.getEndTime();
        LocalDateTime newAppEnd_time = newEndTime;

        // checks to see if the new and old times match each other
        if(((oldAppEnd_time.isEqual(newAppEnd_time)) && (oldAppStart_time.isEqual(newAppStart_time)))){
            return true;
        }else{
            return false;
        }
    }

    /***
     * Returns a list of week days to determine the entire week listing.
     * @param DayWeek
     * @param DayMonth
     * @return
     */
    public static ObservableList<Integer> weekListing(String DayWeek, int DayMonth){
        ObservableList<Integer> week = FXCollections.observableArrayList();

        // Sets the 3 places at a initial value
        int backward = 0;
        int forward = 0;
        int place = 0;

        // based off what the dayweek name is, will set the places for each one.
        if(DayWeek.equals("SUNDAY")){
            forward = 6;
        }else if(DayWeek.equals("MONDAY")){
            forward = 5;
            backward = 1;
            place = 1;
        }else if(DayWeek.equals("TUESDAY")){
            forward = 4;
            backward = 2;
            place = 2;
        }else if(DayWeek.equals("WEDNESDAY")){
            forward = 3;
            backward = 3;
            place = 3;
        }else if(DayWeek.equals("THURSDAY")){
            forward = 2;
            backward = 4;
            place = 4;
        }else if(DayWeek.equals("FRIDAY")){
            forward = 1;
            backward = 5;
            place = 5;
        }else if(DayWeek.equals("SATURDAY")){
            backward = 6;
            place = 6;;
        }

        // Sets a placeholder for the Day of the month
        int holder = DayMonth;

        // if it's a sunday or Saturday, will set the numbers accordingly. If not, will proceed to label the rest of the numbers within the listing.
        if(backward == 0){

            for(int i = 0; i < forward; i ++){
                if(i == place){
                    week.add(DayMonth);
                    holder += 1;
                }else{
                    week.add(holder);
                    holder +=1;
                }
            }
        }else if(forward ==0){
            for(int i = 6; i > backward; i --){
                if(i == place){
                    week.add(DayMonth);
                    holder -= 1;
                }else{
                    week.add(holder);
                    holder -=1;
                }
            }
        }else{

            for(int i = 0; i< backward; i ++){
                holder -= 1;
                week.add(holder);
            }
            holder=DayMonth;
            week.add(holder);
            for(int i = 0; i < forward; i ++){
                holder += 1;
                week.add(holder);
            }

            week = week.sorted();
        }

        // Returns the integer week listing.
        return week;

    }

    /***
     * Checks to see if the appointment is within the working days, return a boolean based off it is or not.
     * @param app
     * @return
     */
    public static boolean IswithinWorkingDaysTimes(Appointment app){
        LocalTime start = app.getStartTime().toLocalTime();
        LocalTime end = app.getEndTime().toLocalTime();
        String dayWeek = app.getStartTime().getDayOfWeek().toString();
        int dayMonth = app.getStartTime().getDayOfMonth();

        // Gets the week listing and uses it to determine where the current appointment is at.
        ObservableList<Integer> weekDays = weekListing(dayWeek, dayMonth);
        ObservableList<String> weekNames = getWeekNames();

        // checks to see the if the appointment is either on Sunday or Saturday
        if(dayWeek.equals(weekNames.get(0)) || dayWeek.equals(weekNames.get(6))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment Date are out of the working hours.\n" +
                    "Please change your time so it doesn't conflict.\n\n" +
                    "Allowed Appointment weekdays are from Monday to Friday.");
            Optional<ButtonType> rs = alert.showAndWait();

            return false;
        }

        // Sets the date format for the localtime and sets the allowed times
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime AllowedStartTime = LocalTime.parse("08:00:00", formatter);
        LocalTime AllowedEndTime = LocalTime.parse("22:00:00", formatter);

        // validates if the times are between the working times.
        if((start.isBefore(AllowedStartTime)) || (end.isAfter(AllowedEndTime)) ){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment times are out of the working hours.\n" +
                    "Please change your time so it doesn't conflict.\n\n" +
                    "Allowed Appointment Start Time: " + AllowedStartTime.toString()
                    + "\nAllowed Appointment End Time: " + AllowedEndTime.toString());
            Optional<ButtonType> rs = alert.showAndWait();
            return false;
        } else if((start.isAfter(AllowedStartTime) || start.equals(AllowedStartTime)) && ((end.isBefore(AllowedEndTime) || end.equals(AllowedEndTime)))){
            return true;
        }

        return false;
    }

    public static ObservableList<String> getWeekNames(){
        ObservableList<String> weekNames = FXCollections.observableArrayList();
        weekNames.add("SUNDAY");
        weekNames.add("MONDAY");
        weekNames.add("TUESDAY");
        weekNames.add("WEDNESDAY");
        weekNames.add("THURSDAY");
        weekNames.add("FRIDAY");
        weekNames.add("SATURDAY");

        return weekNames;
    }


}
