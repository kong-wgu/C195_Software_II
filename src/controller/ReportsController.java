package controller;

import Database.appointmentDAO;
import Database.customerDAO;
import helper.AppointmentReports;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController {


    @FXML private TableView<Appointment> Reports_User_Appointment_TableView;
    @FXML private TableView<AppointmentReports> Reports_Appointments_TableView;
    @FXML private TableView<Division> Reports_Division_TableView;


    @FXML private TableColumn<? ,?>  User_ID;
    @FXML private TableColumn<? ,?>  User_Title;
    @FXML private TableColumn<? ,?>  User_Type;
    @FXML private TableColumn<? ,?>  User_Description;
    @FXML private TableColumn<? ,?>  User_Location;
    @FXML private TableColumn<? ,?>  User_Start;
    @FXML private TableColumn<? ,?>  User_End;
    @FXML private TableColumn<? ,?>  User_CustomerID;

    @FXML private TableColumn<?, ?>  Appointments_Month;
    @FXML private TableColumn<? ,?>  Appointments_Type;
    @FXML private TableColumn<? ,?>  Appointments_Total;

    @FXML private TableColumn<? ,?>  Division_Name;
    @FXML private TableColumn<? ,?>  Division_Total_Customers;

    private ObservableList<Appointment> allAppointments;
    private ObservableList<Customer> allCustomers;

    public void initialize() throws SQLException {

        try{
            ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
            ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();

            User_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            User_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            User_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            User_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            User_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
            User_Start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            User_End.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            User_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            ObservableList<AppointmentReports> appReportsList = AppointmentReports.getAppointmentReports(allAppointments);

            Appointments_Month.setCellValueFactory(new PropertyValueFactory<>("month"));
            Appointments_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            Appointments_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

            Reports_Appointments_TableView.setItems(appReportsList);


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

    public void intialize_all(){

    }

}
