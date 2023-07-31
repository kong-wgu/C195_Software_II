package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private ResourceBundle langbundle;

    @FXML
    private Button Login_login_button;

    @FXML
    private Button Login_exit_button;

    @FXML
    private TextField Login_username_textfield;

    @FXML
    private TextField Login_password_textfield;

    @FXML
    private Label Login_zone_label;

    @FXML
    private Label Login_username_Label;

    @FXML
    private Label Login_password_Label;

    @FXML
    private Label Login_timezone_label;

    /***
     *
     * Initializes upon when the screen is set, gets the local area.
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Login Form is initialized");
        Locale locale = Locale.getDefault();
        ZoneId zone = ZoneId.systemDefault();
        langbundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

        Login_login_button.setText(langbundle.getString("Login"));
        Login_exit_button.setText(langbundle.getString("Exit"));
        Login_username_Label.setText(langbundle.getString("UserName"));
        Login_password_Label.setText(langbundle.getString("Password"));
        Login_zone_label.setText("" + zone);
        Login_timezone_label.setText(langbundle.getString("Location"));

    }

    /** This Method is when the user clicks on the login button on the login form */
    public void login_button_clicked(ActionEvent actionEvent) {
        System.out.println("Login Button Clicked!");
    }
}
