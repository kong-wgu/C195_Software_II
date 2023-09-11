package helper;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.time.LocalDateTime;

public class calculatehelper {

    public static boolean conflictswithAppointments (ObservableList<Appointment> allAppointments, Appointment desiredAppointment) throws NullPointerException{
        Appointment holder = desiredAppointment;
        LocalDateTime desiredStart = holder.getStartTime();
        LocalDateTime desiredEnd = holder.getEndTime();
        Appointment app;
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentObservableList = allAppointments;

        for (int i = 0; i < appointmentObservableList.size(); i ++){
            app = appointmentObservableList.get(i);
            LocalDateTime start = app.getStartTime();
            LocalDateTime end = app.getEndTime();

            if ( (start.isEqual(desiredStart)) || (end.isEqual(desiredStart)) ){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n " +
                        "please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                        "Conflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                + "\nAppointment End Time: " + app.getEndTime().toString());
            }else if((start.isAfter(desiredStart)) && (start.isBefore(desiredEnd))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Your Appointment is conflicted with a existing appointment already.\n " +
                        "please change your time or modify your pre-existing appointment so it doesn't conflict.\n\n" +
                        "Conflicting Appointment ID: " + app.getID() + "\nAppointment Start Time: " + app.getStartTime().toString()
                        + "\nAppointment End Time: " + app.getEndTime().toString());
            }else if()
        }

        return true;
    }

}
