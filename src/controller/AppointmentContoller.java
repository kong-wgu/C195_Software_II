package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.lang.reflect.Executable;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class AppointmentContoller implements Initializable {

    @FXML private TextField Add_Appointment_ID_TextField;
    @FXML private TextField Add_Appointment_Type_TextField;
    @FXML private TextField Add_Appointment_Title_TextField;
    @FXML private TextField Add_Appointment_Description_TextField;
    @FXML private TextField Add_Appointment_Location_TextField;

    @FXML private DatePicker Add_Appointment_Start_Date_DatePicker;
    @FXML private ChoiceBox<String> Add_Appointment_Start_Time_ChoiceBox;
    @FXML private DatePicker Add_Appointment_End_Date_DatePicker;
    @FXML private ChoiceBox<String> Add_Appointment_End_Time_ChoiceBox;
    @FXML private ChoiceBox<String> Add_Appointment_Customer_ID_ChoiceBox;
    @FXML private ChoiceBox<String> Add_Appointment_User_ID_ChoiceBox;
    @FXML private ChoiceBox<String> Add_Appointment_Contact_ChoiceBox;

    @FXML private Button Add_Appointment_Save_Button;
    @FXML private Button Add_Appointment_Cancel_Button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointments are being loaded from database.");
    }

    /** This function focuses on when the user clicks on the add button on the add appointment window */
    public void Add_Appointment_Button_Clicked(ActionEvent actionEvent) throws Exception {

    }

    /**  */
    public void Add_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) throws  Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> rs = alert.showAndWait();

        if(rs.get() ==ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**  */
    public void Modify_Appointment_Button_Clicked(ActionEvent actionEvent) {
    }

    /**  */
    public void Modify_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }

    public void check_for_Blanks(){
        String title = Add_Appointment_Title_TextField.getText();
        String type= Add_Appointment_Type_TextField.getText();
        String description= Add_Appointment_Description_TextField.getText();
        String location= Add_Appointment_Location_TextField.getText();
        LocalDate startDate = Add_Appointment_Start_Date_DatePicker.getValue();
        LocalTime startTime = LocalTime.parse(Add_Appointment_Start_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm") );
        LocalDate endDate = Add_Appointment_End_Date_DatePicker.getValue();
        LocalTime endTime = LocalTime.parse(Add_Appointment_End_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        String userID = Add_Appointment_User_ID_ChoiceBox.getValue();
        String customerID = Add_Appointment_Customer_ID_ChoiceBox.getValue();
        String contact = Add_Appointment_Contact_ChoiceBox.getValue();

        if (title.isBlank() || type.isBlank() || description.isBlank() ||
        location.isBlank() || startDate.toString() == "" || startTime.toString() == "" ||
        endDate.toString() == "" || endTime.toString() == "" || userID.isBlank() ||
        customerID.isBlank() || contact.isBlank()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
            Optional<ButtonType> rs = alert.showAndWait();
        }

    }
}
