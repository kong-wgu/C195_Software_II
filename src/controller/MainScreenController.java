package controller;

import Database.appointmentDAO;
import Database.customerDAO;
import com.sun.nio.file.ExtendedWatchEventModifier;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import helper.holderData;

import javax.swing.tree.ExpandVetoException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MainScreenController {


    @FXML private TableView<Appointment> Appointment_tableview;
    @FXML private TableView<Customer> Customers_tableview;

    @FXML private TableColumn<?, ?> Appointment_ID;
    @FXML private TableColumn<?, ?> Appointment_Title;
    @FXML private TableColumn<?, ?> Appointment_Description;
    @FXML private TableColumn<?, ?> Appointment_Location;
    @FXML private TableColumn<?, ?> Appointment_Type;
    @FXML private TableColumn<?, ?> Appointment_Start;
    @FXML private TableColumn<?, ?> Appointment_End;
    @FXML private TableColumn<?, ?> Appointment_CustomerID;
    @FXML private TableColumn<?, ?> Appointment_ContactID;
    @FXML private TableColumn<?, ?> Appointment_UserID;

    @FXML private TableColumn<?, ?> Customer_ID;
    @FXML private TableColumn<?, ?> Customer_Name;
    @FXML private TableColumn<?, ?> Customer_Address;
    @FXML private TableColumn<?, ?> Customer_Phone;
    @FXML private TableColumn<?, ?> Customer_State;
    @FXML private TableColumn<?, ?> Customer_PostalCode;

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

            Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("ID")); ;
            Appointment_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            Appointment_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            Appointment_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
            Appointment_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            Appointment_Start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            Appointment_End.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            Appointment_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            Appointment_ContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            Appointment_UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

            Appointment_tableview.setItems(allAppointments);

            ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
            Customer_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            Customer_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            Customer_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
            Customer_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            Customer_State.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            Customer_PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

            Customers_tableview.setItems(allCustomers);


        } catch (SQLException e){
            throw new SQLException("Could not Load Appointments from DB.");
        }



    }

    public void appointment_add_button_clicked(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Add_Appointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage win = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();
    }

    public void appointment_update_button_clicked(ActionEvent actionEvent) throws Exception {

        Appointment select = Appointment_tableview.getSelectionModel().getSelectedItem();

        try {
            if (select == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select any Row from Apppointment Tables! \nPlease choose a valid Appointment!");
                Optional<ButtonType> rs = alert.showAndWait();
            } else{
                long selectID = select.getID();

                holderData.setSelected_Appointment(selectID);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Modify_Appointment.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage win = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                win.setScene(scene);
                win.show();
            }
        }catch (Exception e){
            throw new Exception("Was not able to delete appointment.");
        }



    }

    public void appointment_delete_button_clicked(ActionEvent actionEvent) throws Exception {
        Appointment select = Appointment_tableview.getSelectionModel().getSelectedItem();

        try {
            if (select == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select any Row from Apppointment Tables! \nPlease choose a valid Appointment!");
                Optional<ButtonType> rs = alert.showAndWait();
            } else{
                long select_ID = select.getID();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the appointment with the ID: " + select_ID);
                Optional<ButtonType> rs = alert.showAndWait();
                if(rs.get() == ButtonType.OK){
                    appointmentDAO.deleteAppointment(select_ID);

                    ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                    Appointment_tableview.setItems(allAppointments);
                }
            }
        }catch (Exception e){
            throw new Exception("Was not able to delete appointment.");
        }
    }




    public void appointment_weekly_radioButton_clicked(ActionEvent actionEvent) {
        Appointment_all_appointment_radiobutton.selectedProperty().set(false);
        Appointment_monthly_radiobutton.selectedProperty().set(false);


    }

    public void appointment_monthly_radioButton_clicked(ActionEvent actionEvent) {
        Appointment_all_appointment_radiobutton.selectedProperty().set(false);
        Appointment_weekly_radiobutton.selectedProperty().set(false);


    }

    public void appointment_allAppointment_radioButton_selected(ActionEvent actionEvent) {
        Appointment_weekly_radiobutton.selectedProperty().set(false);
        Appointment_monthly_radiobutton.selectedProperty().set(false);
    }



    public void customers_add_button_clicked(ActionEvent actionEvent) throws Exception {
        Parent report = FXMLLoader.load(getClass().getResource("/view/Add_Customer.fxml"));
        Scene scene = new Scene(report);
        Stage win = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();

    }

    public void customers_update_button_clicked(ActionEvent actionEvent) throws Exception {
        Customer selected = Customers_tableview.getSelectionModel().getSelectedItem();

        try {
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "You did not select any Customers, Please try again!");
                Optional<ButtonType> rs = alert.showAndWait();
            }else {
                long selectedID = selected.getID();

                holderData.setSelected_Customer(selectedID);
                holderData.setHolderCustomer(selected);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Modify_Customer.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage win = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                win.setScene(scene);
                win.show();

            }
        }catch (Exception e){
            throw new Exception("Was not able to get Customer, please check for Null Customer.");
        }

    }

    public void customers_delete_button_clicked(ActionEvent actionEvent) throws Exception {
        Customer select = Customers_tableview.getSelectionModel().getSelectedItem();

        try{
            if(select == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "You did not select any Customer");
                Optional<ButtonType> rs = alert.showAndWait();
            }else{
                long select_ID = select.getID();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete this appointment with the ID: " + select_ID );
                Optional<ButtonType> rs = alert.showAndWait();
                if(rs.get() == ButtonType.OK){
                    customerDAO.deleteCustomer(select_ID);

                    ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
                    Customers_tableview.setItems(allCustomers);
                }
            }
        }catch (Exception e){
            throw new Exception("Was not able to delete Customer.");
        }
    }

    public void reports_button_clicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent report = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Scene scene = new Scene(report);
        Stage win = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();
    }



    public void log_out_button_clicked(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> rs = alert.showAndWait();

        if(rs.get() ==ButtonType.OK){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
