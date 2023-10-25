package controller;

import Database.appointmentDAO;
import Database.contactDAO;
import Database.customerDAO;
import helper.AppointmentReports;
import javafx.collections.FXCollections;
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
import model.Contact;
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
    @FXML private TableView<AppointmentReports> Reports_Division_TableView;

    @FXML private ChoiceBox<String> Reports_User_ChoiceBox;

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
    private ObservableList<Contact> allContacts;

    /***
     * Sets all the information into their tables, sets columns for the properties of the it's object.
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        try{
            // Sets all the Lists Arrays to information
            allAppointments = appointmentDAO.getAllAppointments();
            allCustomers = customerDAO.getAllCustomers();
            allContacts = contactDAO.getAllContacts();
            ObservableList<String> allContactList = FXCollections.observableArrayList();

            // Saves the contact information and ID into a list array placeholder
            allContacts.forEach(contact -> allContactList.add(contact.getName() + " - " + Long.toString(contact.getID())));

            User_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            User_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            User_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            User_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            User_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
            User_Start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            User_End.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            User_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            // Sets a Array List containing a helper class to support the appointment report section.
            ObservableList<AppointmentReports> appReportsList = AppointmentReports.getAppointmentReports(allAppointments);

            Appointments_Month.setCellValueFactory(new PropertyValueFactory<>("month"));
            Appointments_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            Appointments_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

            // Sets an Array List holder that gathers all division information.
            ObservableList<AppointmentReports> divisionReportList = AppointmentReports.getDivisionReports(allCustomers);

            Division_Name.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            Division_Total_Customers.setCellValueFactory(new PropertyValueFactory<>("totaldivisionCustomers"));

            // Places all sorted Array List information into the tableview.
            Reports_Appointments_TableView.setItems(appReportsList);
            Reports_Division_TableView.setItems(divisionReportList);
            Reports_User_ChoiceBox.setItems(allContactList);

        }catch(SQLException e){
            throw new SQLException("An item was not loaded correctly from the DB");
        }

    }


    /***
     * Log out button that Logs the user out, confirms with the user first.
     * @param actionEvent
     */
    public void Reports_Log_Out_Button_Clicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> rs = alert.showAndWait();

        // If the user select ok, appication will exit.
        if(rs.get() ==ButtonType.OK){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    /***
     * Back Button that will take the user back to the main screen after confirmation message.
     * @param actionEvent
     * @throws IOException
     */
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

    /***
     * Calculates all appointments related the user that is selected, will update the user table with the information.
     * @param actionEvent
     */
    public void user_selected(ActionEvent actionEvent) {
        String selectedUserID = Reports_User_ChoiceBox.getValue();

        int lastIndent = selectedUserID.lastIndexOf(" ");
        lastIndent = lastIndent + 1;
        selectedUserID = selectedUserID.substring(lastIndent);

        long selectedID = Long.parseLong(selectedUserID);
        boolean found = false;

        // Checks to the see if selected ID is within all contacts.
        // If so, will gather all related appointments.
        for(Contact contact: allContacts){
            long id = contact.getID();

            if(id == selectedID){
                // Sets the List Array to collect all related appointments.
                ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

                for(Appointment app: allAppointments){
                    long userappID = app.getContactID();
                    if(userappID == id){
                        contactAppointments.add(app);
                    }
                }

                // Sets all the related appointment of the user into the appoinment table.
                Reports_User_Appointment_TableView.setItems(contactAppointments);

                break;
            }
        }


    }
}
