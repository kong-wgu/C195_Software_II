package controller;

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
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {


    @FXML TableView<Appointment> Reports_User_Appointment_TableView;
    @FXML TableView<Division> Reports_Appointments_TableView;
    @FXML TableView<Division> Reports_Division_TableView;


    @FXML TableColumn<? ,?>  User_ID;
    @FXML TableColumn<? ,?>  User_Title;
    @FXML TableColumn<? ,?>  User_Type;
    @FXML TableColumn<? ,?>  User_Description;
    @FXML TableColumn<? ,?>  User_Location;
    @FXML TableColumn<? ,?>  User_Start;
    @FXML TableColumn<? ,?>  User_End;
    @FXML TableColumn<? ,?>  User_CustomerID;

    @FXML TableColumn<? ,?>  Appointments_Month;
    @FXML TableColumn<? ,?>  Appointments_Type;
    @FXML TableColumn<? ,?>  Appointments_Total;

    @FXML TableColumn<? ,?>  Divisions_Name;
    @FXML TableColumn<? ,?>  Divisions_Total_Customers;

    private ObservableList<Appointment> allAppointments;


    public void initialize() throws SQLException {

        try{

        }catch(SQLException e){
            throw new SQLException("An item was not loaded correctly from the DB");
        }


    }


    /** This function is to log the user out of the application */
    public void Reports_Log_Out_Button_Clicked(ActionEvent actionEvent) {
    }

    /** This function will bring the user back to the MainScreen Form */
    public void Reports_Back_Button_Clicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to back to the Main Menu?");
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
}
