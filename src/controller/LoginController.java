package controller;

import Database.DBConnection;
import Database.UserDAO;
import Database.appointmentDAO;
import helper.LoginHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/***
 * Login Controller that will handle form data and also login validation to see if user is able to login.
 */
public class LoginController implements Initializable {

    // Resource bundle that contains both english and French translation.
    private ResourceBundle langbundle;
    private long appointmentID;
    private LocalDateTime endTime;
    private LocalDateTime timeStarted;

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
     * Using Language Bundle to translate language if need so.
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

    /***
     *
     * Login Button functionality that will validate login
     * @param actionEvent
     * @throws Exception
     */

    public void login_button_clicked(ActionEvent actionEvent) throws Exception {

        try{
            // language bundle that contains the correct translation
            langbundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

            String username = Login_username_textfield.getText();
            String password = Login_password_textfield.getText();

            // Checks to see if Username or password fields are blank
            if(username.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(langbundle.getString("Error"));
                alert.setContentText(langbundle.getString("userRequired"));
                alert.show();
            }else if(password.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(langbundle.getString("Error"));
                alert.setContentText(langbundle.getString("passwordRequired"));
                alert.show();
            }else{

                //Validates user and returns user as null if the information cannot be validated.
                ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                User user = UserDAO.validateUser(username, password);

                // If user is validated or not, will record the submission onto the Login text file.
                if (user != null){

                    LocalDateTime minus15 = LocalDateTime.now().minusMinutes(15);
                    LocalDateTime plus15 = LocalDateTime.now().plusMinutes(15);
                    boolean iswithin15 =false;
                    User.userLoggedIn = username;

                    LoginHelper.addLogin(username, true);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/MainScreen.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    // check all appointment times to see if there any appointments that are within the 15 minute interval
                    for (Appointment appointment: allAppointments){
                        timeStarted = appointment.getStartTime();
                        if((timeStarted.isBefore(plus15) || timeStarted.isEqual(plus15)) &&
                                (timeStarted.isAfter(minus15) || timeStarted.isEqual(minus15))){
                            appointmentID = appointment.getID();
                            endTime = appointment.getEndTime();
                            iswithin15 = true;
                        }
                    }

                    // If there is an appointment within the 15 minute interval, will trigger this pop up.
                    if (iswithin15){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                langbundle.getString("within15") + " " + "\n" + langbundle.getString("appointmentid") +
                                        appointmentID +
                                "\n" + langbundle.getString("appointmentStartTime") + " " +
                                timeStarted + "\n" + langbundle.getString("appointmentEndTime") +
                                " " + endTime);
                        Optional<ButtonType> confirm = alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                langbundle.getString("nonewithin15"));
                        Optional<ButtonType> confirm = alert.showAndWait();
                    }
                }else{
                    LoginHelper.addLogin(username, false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(langbundle.getString("Error"));
                    alert.setContentText("incorrectUsernamePassword");
                    alert.show();
                }
            }

        }catch (IOException ioException){
            ioException.printStackTrace();
        }

    }

    /***
     * Exit Button which will exit the application
     * @param actionEvent
     */
    public void exit_button_clicked(ActionEvent actionEvent) {
        langbundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, langbundle.getString("exitButton"));
        Optional<ButtonType> rs = alert.showAndWait();

        if(rs.get() ==ButtonType.OK){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }
}
