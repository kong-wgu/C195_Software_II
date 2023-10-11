package helper;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class calculatehelper {

    public static boolean conflictswithAppointments (ObservableList<Appointment> allAppointments, Appointment desiredAppointment) throws NullPointerException{
        Appointment holder = desiredAppointment;

        LocalDate desiredDate = holder.getStartTime().toLocalDate();

        LocalDateTime desiredStart = holder.getStartTime();
        LocalDateTime desiredEnd = holder.getEndTime();
        Appointment app;
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentObservableList = allAppointments;

        for (Appointment appointment : appointmentObservableList) {
            app = appointment;
            LocalDate appDate = app.getStartTime().toLocalDate();
            LocalDateTime start = app.getStartTime();
            LocalDateTime end = app.getEndTime();

            if(appDate.isEqual(desiredDate)) {

                if ((start.isEqual(desiredStart)) || (end.isEqual(desiredStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                            "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                            "Conflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                            + "\nAppointment End Time: " + app.getEndTime().toString());
                    Optional<ButtonType> rs = alert.showAndWait();
                    return false;
                } else if ((desiredStart.isAfter(start)) && (desiredStart.isBefore(end))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                            "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                            "Conflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                            + "\nAppointment End Time: " + app.getEndTime().toString());
                    Optional<ButtonType> rs = alert.showAndWait();
                    return false;
                } else if ((desiredEnd.isAfter(start)) && (desiredEnd.isBefore(end))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n" +
                            "Please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                            "Conflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                            + "\nAppointment End Time: " + app.getEndTime().toString());
                    Optional<ButtonType> rs = alert.showAndWait();
                    return false;
                }
            }
        }

        return true;
    }

    public ObservableList<Appointment> getCurrentWeek(ObservableList<Appointment> allAppointments){
        ObservableList<Appointment> currentWeek = FXCollections.observableArrayList();

        LocalDateTime currentTime = LocalDateTime.now();

        String currentMonth = currentTime.getMonth().toString();
        String currentDayWeek = currentTime.getDayOfWeek().toString();
        int currentDayMonth = currentTime.getDayOfMonth();

        ObservableList<Integer> week = weekListing(currentDayWeek, currentDayMonth);


        for(Appointment app : allAppointments){

        }


        return currentWeek;

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
            for(int i = 0; i < forward; i ++){
                holder += 1;
                week.add(holder);
            }

            week.forEach(integer -> System.out.println(integer));

            week.sorted();
            week.forEach(integer -> System.out.println(integer));
        }

        return week;

    }

}
