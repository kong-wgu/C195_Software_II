package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Appointment implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointments are being loaded from database.");
    }

    /** This function focuses on when the user clicks on the add button on the add appointment window */
    public void Add_Appointment_Button_Clicked(ActionEvent actionEvent) {
    }

    /**  */
    public void Add_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }

    /**  */
    public void Modify_Appointment_Button_Clicked(ActionEvent actionEvent) {
    }

    /**  */
    public void Modify_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }
}
