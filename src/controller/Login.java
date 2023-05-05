package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Button login_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Form is initialized");
    }

    /** This Method is when the user clicks on the login button on the login form */
    public void login_button_clicked(ActionEvent actionEvent) {
        System.out.println("Login Button Clicked!");
    }
}
