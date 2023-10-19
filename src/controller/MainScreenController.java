package controller;

import Database.appointmentDAO;
import Database.customerDAO;
import com.sun.nio.file.ExtendedWatchEventModifier;
import helper.calculatehelper;
import javafx.collections.FXCollections;
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

import javax.print.attribute.standard.JobStateReason;
import javax.swing.tree.ExpandVetoException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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

    private ObservableList<Appointment> allAppointments;

    /***
     * Initializes all Appointments and Customer data onto the table
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        System.out.println("Main Screen Loaded");

        try {
            allAppointments = appointmentDAO.getAllAppointments();

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

    /***
     * Add button for adding appointments is clicked, takes to the Appointment add form.
     * @param actionEvent
     * @throws Exception
     */
    public void appointment_add_button_clicked(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Add_Appointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage win = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();
    }

    /***
     * Update button for appointments tableview, also saves selected appointment use for the modify appointment form.
     * @param actionEvent
     * @throws Exception
     */
    public void appointment_update_button_clicked(ActionEvent actionEvent) throws Exception {

        // Sets the selected appointment as a Appointment to see if anything valid was selected.
        Appointment select = Appointment_tableview.getSelectionModel().getSelectedItem();

        // If there is no appointments selected, will warn user with pop up message.
        try {
            if (select == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You did not select any Row from Apppointment Tables! \nPlease choose a valid Appointment!");
                Optional<ButtonType> rs = alert.showAndWait();
            } else{
                long selectID = select.getID();

                // If there is a appointment selected, will create a Appointment placeholder to get the appointment data.
                holderData.setSelected_Appointment(selectID);
                holderData.setHolderAppointment(select);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Modify_Appointment.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage win = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                win.setScene(scene);
                win.show();
            }
        }catch (Exception e){
            throw new Exception("Was not able to modify appointment.");
        }

    }

    /***
     * Delete button on the appointment table, will show confirmation message before doing anything.
     * @param actionEvent
     * @throws Exception
     */
    public void appointment_delete_button_clicked(ActionEvent actionEvent) throws Exception {
        // Sets the selected Appointment to a variable placeholder
        Appointment select = Appointment_tableview.getSelectionModel().getSelectedItem();

        // Checks to see if there was any appointments selected, pops message to either show confirmation or warning.
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


    /***
     * Clicks on Weekly appointment radiobutton
     * @param actionEvent
     */
    public void appointment_weekly_radioButton_clicked(ActionEvent actionEvent) {
        // sets the other radio button to false to show the only selected radio button
        Appointment_all_appointment_radiobutton.selectedProperty().set(false);
        Appointment_monthly_radiobutton.selectedProperty().set(false);

        // Use a variable placeholder to collect all appointments that fall under the week.
        ObservableList<Appointment> collectedAppMonths = FXCollections.observableArrayList();

        // Detects the current time for the month and day of the month. Use to calculate current weekday and place.
        String currentMonth = LocalDateTime.now().getMonth().toString();
        String currentWeek = LocalDateTime.now().getDayOfWeek().toString();
        int monthDay = LocalDateTime.now().getDayOfMonth();

        // Returns a List of integers that would represent the 7 day week.
        ObservableList<Integer> weeklist = calculatehelper.weekListing(currentWeek, monthDay);

        // Checks all appointments to see if they fall under the same month and list of integers.
        for(Appointment app: allAppointments){
            String appMonth = app.getStartTime().getMonth().toString();
            String appDayWeek = app.getStartTime().getDayOfWeek().toString();
            int appNumDay = app.getStartTime().getDayOfMonth();

            if (currentMonth.equals(appMonth)){
                // Falls under the current week and collects that appointment.
                if(weeklist.contains(appNumDay)){
                    collectedAppMonths.add(app);
                }
            }
        }

        // Displays all appointments that is in the current week.
        Appointment_tableview.setItems(collectedAppMonths);
    }

    /***
     * Selecting the Monthly appointment radio for the appointments table.
     * @param actionEvent
     */
    public void appointment_monthly_radioButton_clicked(ActionEvent actionEvent) {
        // Sets the other 2 radio button to false and keeps the monthly radio button true.
        Appointment_all_appointment_radiobutton.selectedProperty().set(false);
        Appointment_weekly_radiobutton.selectedProperty().set(false);

        // Sets variable placeholder to collect appointments that fall under the current month
        ObservableList<Appointment> collectedAppMonths = FXCollections.observableArrayList();

        // Gets the current month of now.
        String currentMonth = LocalDate.now().getMonth().toString();

        // Checks any appointments to see if it is within the same month.
        for(Appointment app : allAppointments){
            String appMonth = app.getStartTime().getMonth().toString();
            if(currentMonth.equals(appMonth)){
                collectedAppMonths.add(app);
            }
        }

        // Sets all the appointments that is collected on the appointment table.
        Appointment_tableview.setItems(collectedAppMonths);

    }

    /***
     * All Appointment Button - Sets all other radio button to false
     * @param actionEvent
     */
    public void appointment_allAppointment_radioButton_selected(ActionEvent actionEvent) {
        Appointment_weekly_radiobutton.selectedProperty().set(false);
        Appointment_monthly_radiobutton.selectedProperty().set(false);

        // Sets the all appoinment that was original loaded back on the appointment table
        Appointment_tableview.setItems(allAppointments);

    }

    /***
     * Add Button for the Customer Table, shows the Add Customer Form
     * @param actionEvent
     * @throws Exception
     */
    public void customers_add_button_clicked(ActionEvent actionEvent) throws Exception {
        Parent report = FXMLLoader.load(getClass().getResource("/view/Add_Customer.fxml"));
        Scene scene = new Scene(report);
        Stage win = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();
    }

    /***
     * Update button for the customer Table
     * @param actionEvent
     * @throws Exception
     */
    public void customers_update_button_clicked(ActionEvent actionEvent) throws Exception {
        // Sets the selected customer as a Customer object, if none is select, object will be null.
        Customer selected = Customers_tableview.getSelectionModel().getSelectedItem();

        try {
            // If the object above is null, meaning that they did not select a customer when clicking the button.
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "You did not select any Customers, Please try again!");
                Optional<ButtonType> rs = alert.showAndWait();
            }else {
                // Sets the ID of the selected customer into a variable placeholder.
                long selectedID = selected.getID();

                // Sets the selected Customer into a Customer Object placeholder to move into the update form data fields.
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

    /***
     * Delete button for the Customer table
     * @param actionEvent
     * @throws Exception
     */
    public void customers_delete_button_clicked(ActionEvent actionEvent) throws Exception {
        // Sets the selected Customer into a Customer object placeholder.
        Customer select = Customers_tableview.getSelectionModel().getSelectedItem();

        try{
            // If the user did not select any customer, will pop up warning message.
            if(select == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "You did not select any Customer");
                Optional<ButtonType> rs = alert.showAndWait();
            }else{
                // Sets the ID of the selected Customer and shows confirmation message with the selected Customer
                long select_ID = select.getID();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete this appointment with the ID: " + select_ID );
                Optional<ButtonType> rs = alert.showAndWait();

                // If User selected OK on the confirmation pop-up, will delete the customer.
                if(rs.get() == ButtonType.OK){
                    // Deletes the Customer and gathers all Customers again to set back on the customer table.
                    customerDAO.deleteCustomer(select_ID);

                    ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
                    Customers_tableview.setItems(allCustomers);
                }
            }
        }catch (Exception e){
            throw new Exception("Was not able to delete Customer.");
        }
    }

    /***
     * Reports Button that brings up the Reports Form.
     * @param actionEvent
     * @throws IOException
     */
    public void reports_button_clicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent report = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Scene scene = new Scene(report);
        Stage win = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        win.setScene(scene);
        win.show();
    }

    /***
     * Log out Button that will show confirmation message.
     * @param actionEvent
     */
    public void log_out_button_clicked(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> rs = alert.showAndWait();

        // If the user select ok, appication will exit.
        if(rs.get() ==ButtonType.OK){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
