package controller;

import Database.appointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreen {

    @FXML private TableView Appointment_tableview;
    @FXML private TableView Customers_tableview;

    @FXML private TableColumn Appointment_ID;
    @FXML private TableColumn Appointment_Title;
    @FXML private TableColumn Appointment_Description;
    @FXML private TableColumn Appointment_Location;
    @FXML private TableColumn Appointment_Type;
    @FXML private TableColumn Appointment_Start;
    @FXML private TableColumn Appointment_End;
    @FXML private TableColumn Appointment_CustomerID;
    @FXML private TableColumn Appointment_ContactID;
    @FXML private TableColumn Appointment_UserID;

    @FXML private TableColumn Customer_ID;
    @FXML private TableColumn Customer_Name;
    @FXML private TableColumn Customer_Address;
    @FXML private TableColumn Customer_Phone;
    @FXML private TableColumn Customer_State;
    @FXML private TableColumn Customer_PostalCode;

    @FXML private RadioButton Appointment_all_appointment_radiobutton;
    @FXML private RadioButton Appointment_weekly_radiobutton;
    @FXML private RadioButton Appointment_monthly_radiobutton;
    @FXML private Button Appointment_add_button;
    @FXML private Button Appointment_update_button;
    @FXML private Button Appointment_delete_button;
    @FXML private Button Customers_add_button;
    @FXML private Button Customers_update_button;
    @FXML private Button Customers_delete_button;
    @FXML private Button MainScreen_reports_button;
    @FXML private Button MainScreen_logout_button;



    public void initialize() throws SQLException {

        System.out.println("Main Screen Loaded");

        try {
            ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();

            Appointment_ID.setCellFactory(new PropertyValueFactory<>("ID")); ;
            Appointment_Title.setCellFactory(new PropertyValueFactory<>("title"));
            Appointment_Description.setCellFactory(new PropertyValueFactory<>("description"));
            Appointment_Location.setCellFactory(new PropertyValueFactory<>("location"));
            Appointment_Type.setCellFactory(new PropertyValueFactory<>("type"));
            Appointment_Start.setCellFactory(new PropertyValueFactory<>("startTime"));
            Appointment_End.setCellFactory(new PropertyValueFactory<>("endTime"));
            Appointment_CustomerID.setCellFactory(new PropertyValueFactory<>("customerID"));
            Appointment_ContactID.setCellFactory(new PropertyValueFactory<>("contactID"));
            Appointment_UserID.setCellFactory(new PropertyValueFactory<>("userID"));

            Appointment_tableview.setItems(allAppointments);

        } catch (SQLException e){
            throw new SQLException("Could not Load Appointments from DB.");
        }



    }

    public void appointment_add_button_clicked(ActionEvent actionEvent) {
    }

    public void appointment_update_button_clicked(ActionEvent actionEvent) {
    }

    public void appointment_delete_button_clicked(ActionEvent actionEvent) {
    }




    public void appointment_weekly_radioButton_clicked(ActionEvent actionEvent) {
    }

    public void appointment_monthly_radioButton_clicked(ActionEvent actionEvent) {
    }

    public void appointment_allAppointment_radioButton_selected(ActionEvent actionEvent) {
    }



    public void customers_add_button_clicked(ActionEvent actionEvent) {
    }

    public void customers_update_button_clicked(ActionEvent actionEvent) {
    }

    public void customers_delete_button_clicked(ActionEvent actionEvent) {
    }

    public void reports_button_clicked(ActionEvent actionEvent) {
    }



    public void log_out_button_clicked(ActionEvent actionEvent) {
    }
}
