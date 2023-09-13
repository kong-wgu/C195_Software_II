package helper;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

}
