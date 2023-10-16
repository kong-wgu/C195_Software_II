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

    public static boolean conflictswithAppointments (ObservableList<Appointment> allAppointments, Appointment desiredAppointment) throws NullPointerException{
        Appointment holder = desiredAppointment;

        LocalDate desiredDate = holder.getStartTime().toLocalDate();

        LocalDateTime desiredStart = holder.getStartTime();
        LocalDateTime desiredEnd = holder.getEndTime();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentObservableList = allAppointments;

        if(!IswithinWorkingDaysTimes(desiredAppointment)){return false;}

        for (Appointment app : appointmentObservableList){
            if(app.getID() == holder.getID()) {
                if (check_for_same_app_times(holderData.getHolderAppointment(), desiredStart, desiredEnd)) {
                    return true;
                }
            }
        }



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
                    } else if ((desiredStart.isAfter(start)) && (desiredStart.isBefore(end))) {
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
                    }
                }
            }
        }

        return true;
    }

    public ObservableList<Appointment> getCurrentWeekAppointments(ObservableList<Appointment> allAppointments){
        ObservableList<Appointment> currentWeek = FXCollections.observableArrayList();

        LocalDateTime currentTime = LocalDateTime.now();

        String currentMonth = currentTime.getMonth().toString();
        String currentDayWeek = currentTime.getDayOfWeek().toString();
        int currentDayMonth = currentTime.getDayOfMonth();

        ObservableList<Integer> week = weekListing(currentDayWeek, currentDayMonth);

        for(Appointment app : allAppointments){
            String appMonth = app.getStartTime().getMonth().toString();
            int appMonthDay = app.getStartTime().getDayOfMonth();
            if(week.contains(appMonthDay)){
                currentWeek.add(app);
            }

        }

        return currentWeek;

    }

    public static boolean check_for_same_app_times(Appointment oldAppointment, LocalDateTime newStartTime, LocalDateTime newEndTime){
        LocalDateTime oldAppStart_time = oldAppointment.getStartTime();
        LocalDateTime newAppStart_time = newStartTime;


        LocalDateTime oldAppEnd_time = oldAppointment.getEndTime();
        LocalDateTime newAppEnd_time = newEndTime;

        if(((oldAppEnd_time.isEqual(newAppEnd_time)) && (oldAppStart_time.isEqual(newAppStart_time)))){
            return true;
        }else{
            return false;
        }
    }


    public static ObservableList<Integer> weekListing(String DayWeek, int DayMonth){
        ObservableList<Integer> week = FXCollections.observableArrayList();

        int backward = 0;
        int forward = 0;
        int place = 0;

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

        int holder = DayMonth;

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

        return week;

    }

    public static boolean IswithinWorkingDaysTimes(Appointment app){
        LocalTime start = app.getStartTime().toLocalTime();
        LocalTime end = app.getEndTime().toLocalTime();
        String dayWeek = app.getStartTime().getDayOfWeek().toString();
        int dayMonth = app.getStartTime().getDayOfMonth();

        ObservableList<Integer> weekDays = weekListing(dayWeek, dayMonth);
        ObservableList<String> weekNames = getWeekNames();


        if(dayWeek.equals(weekNames.get(0)) || dayWeek.equals(weekNames.get(6))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment Date are out of the working hours.\n" +
                    "Please change your time so it doesn't conflict.\n\n" +
                    "Allowed Appointment weekdays are from Monday to Friday.");
            Optional<ButtonType> rs = alert.showAndWait();

            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime AllowedStartTime = LocalTime.parse("08:00:00", formatter);
        LocalTime AllowedEndTime = LocalTime.parse("22:00:00", formatter);

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
