package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Reports Button Clicked");
    }


    /** This function is to log the user out of the application */
    public void Reports_Log_Out_Button_Clicked(ActionEvent actionEvent) {
    }

    /** This function will bring the user back to the MainScreen Form */
    public void Reports_Back_Button_Clicked(ActionEvent actionEvent) {
    }
}
