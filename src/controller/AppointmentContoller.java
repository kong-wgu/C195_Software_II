package controller;

import Database.UserDAO;
import Database.appointmentDAO;
import Database.contactDAO;
import Database.customerDAO;
import helper.calculatehelper;
import javafx.collections.FXCollections;
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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentContoller{

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

    private ObservableList<String> ContactNames;
    private ObservableList<Contact> allContacts;
    private ObservableList<Appointment> allAppointments;

    /***
     * Gets all the prior information so that all choiceboxes can be loaded correctly.
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        try {
            ContactNames = FXCollections.observableArrayList();
            allContacts = contactDAO.getAllContacts();
            allAppointments = appointmentDAO.getAllAppointments();

            // Lambda #1
            allContacts.forEach(contact -> ContactNames.add(contact.getName() + " : " + contact.getID()));
            Add_Appointment_Contact_ChoiceBox.setItems(ContactNames);

            ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
            ObservableList<String> CustomerID = FXCollections.observableArrayList();

            allCustomers.forEach(customer -> CustomerID.add(customer.getName() + " : " + Long.toString(customer.getID())));
            Add_Appointment_Customer_ID_ChoiceBox.setItems(CustomerID);

            ObservableList<User> allUsers = UserDAO.getAllUsers();
            ObservableList<String> UserID = FXCollections.observableArrayList();

            allUsers.forEach(user -> UserID.add(Long.toString(user.getUserID())));
            Add_Appointment_User_ID_ChoiceBox.setItems(UserID);

            Add_Appointment_Start_Time_ChoiceBox.setItems(helper.timeHelper.getAppointmentTimeList());
            Add_Appointment_End_Time_ChoiceBox.setItems(helper.timeHelper.getAppointmentTimeList());

        }catch (SQLException e){
            throw new SQLException("Error Loading Data list, check DBConnection.");
        }

    }

    /***
     * Add Button functionality
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void Add_Appointment_Button_Clicked(ActionEvent actionEvent) throws SQLException, IOException {

        /***
         * Checks for any blanks and times for validation.
         */
        try{
            if (!check_for_Blanks()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
                Optional<ButtonType> rs = alert.showAndWait();
            }else if (check_times()) {

                String type = Add_Appointment_Type_TextField.getText();
                String title = Add_Appointment_Title_TextField.getText();
                String description = Add_Appointment_Description_TextField.getText();
                String location = Add_Appointment_Location_TextField.getText();
                LocalDate start_date = Add_Appointment_Start_Date_DatePicker.getValue();
                LocalDate end_date = Add_Appointment_End_Date_DatePicker.getValue();
                String start_time = Add_Appointment_Start_Time_ChoiceBox.getValue();
                String end_time = Add_Appointment_End_Time_ChoiceBox.getValue();

                String Customer_ID = Add_Appointment_Customer_ID_ChoiceBox.getValue();

                // grabs the last ID from the choicebox so that it can be used as the ID.
                int lastIndent = Customer_ID.lastIndexOf(" ");
                lastIndent = lastIndent + 1;
                Customer_ID = Customer_ID.substring(lastIndent);

                String user_ID = Add_Appointment_User_ID_ChoiceBox.getValue();

                // grabs the last ID from the choicebox so that it can be used as the ID.
                String contact = Add_Appointment_Contact_ChoiceBox.getValue();
                lastIndent = contact.lastIndexOf(" ");
                lastIndent = lastIndent + 1;
                contact = contact.substring(lastIndent);

                // Formats all the dates to the according date format, so it can be easily matching when doing data validation.
                String startDate = start_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                startDate = startDate + " " + start_time + ":00";

                String endDate = end_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDate = endDate + " " + end_time + ":00";

                String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

                String lastUpdate = createDate;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                //Creating variables placeholders
                long dummy_id = 0;
                LocalDateTime test_startDate = LocalDateTime.parse(startDate, formatter);
                LocalDateTime test_endDate = LocalDateTime.parse(endDate, formatter);
                long dummy_customer_ID = Long.parseLong(Customer_ID);
                long dummy_user_ID = Long.parseLong(user_ID);
                long dummy_contact = Long.parseLong(contact);

                // Create a appointment holder to retain all the information
                Appointment test = new Appointment(dummy_id, title, description, location, type, test_startDate, test_endDate, lastUpdate,
                        dummy_customer_ID, dummy_user_ID, dummy_contact);

                // Checks to see if there are any conflicts with other appointments
                if(calculatehelper.conflictswithAppointments(allAppointments, test)) {

                    // Adds the appointment to the database, no need for the id since it auto-increments.
                    appointmentDAO.addAppointment(title, description, location, type, startDate, endDate,
                            createDate, lastUpdate, Customer_ID, user_ID, contact);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/MainScreen.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }

            }
        }catch(SQLException e){
            throw new SQLException("Possible Null or Wrong Data Type");
        }


    }

    /***
     * Cancel Button, returns to the  main menu after a confirmtion screen is shown
     * @param actionEvent
     * @throws Exception
     */
    public void Add_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) throws Exception{
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

    /***
     * Checks to see if any of the fields are blank,
     * If so, will inform user of the error with a message pop up.
     * @return
     * @throws NullPointerException
     */
    public boolean check_for_Blanks() throws NullPointerException{
        try {
            String title = Add_Appointment_Title_TextField.getText();
            String type = Add_Appointment_Type_TextField.getText();
            String description = Add_Appointment_Description_TextField.getText();
            String location = Add_Appointment_Location_TextField.getText();
            LocalDate startDate = Add_Appointment_Start_Date_DatePicker.getValue();
            LocalTime empty = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime startTime = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            if(Add_Appointment_Start_Time_ChoiceBox.getValue() != null){
                startTime = LocalTime.parse(Add_Appointment_Start_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            LocalDate endDate = Add_Appointment_End_Date_DatePicker.getValue();
            if(Add_Appointment_End_Time_ChoiceBox.getValue() != null) {
                endTime = LocalTime.parse(Add_Appointment_End_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            String userID = Add_Appointment_User_ID_ChoiceBox.getValue();
            String customerID = Add_Appointment_Customer_ID_ChoiceBox.getValue();
            String contact = Add_Appointment_Contact_ChoiceBox.getValue();

            if (title.isBlank() || type.isBlank() || description.isBlank() ||
                    location.isBlank() || startDate == null || startTime.equals(empty)  ||
                    endDate == null || endTime.equals(empty) ||
                    customerID == null || contact == null) {
                return false;
            }
            return true;
        }catch(NullPointerException e){
            throw new NullPointerException("There is a null object within the text field.");
        }
    }

    /***
     * Validates the times and dates
     * If any errors, error pop up informs user.
     * @return
     */
    public boolean check_times(){

        LocalDate start_day = Add_Appointment_Start_Date_DatePicker.getValue();
        LocalDate end_day = Add_Appointment_End_Date_DatePicker.getValue();
        String start_time = Add_Appointment_Start_Time_ChoiceBox.getValue();
        String end_time = Add_Appointment_End_Time_ChoiceBox.getValue();

        LocalTime time_start = LocalTime.parse(start_time);
        LocalTime time_end = LocalTime.parse(end_time);

        boolean check = false;

        if(start_day.isAfter(end_day) || end_day.isBefore(start_day) || end_day.isAfter(start_day)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your Appointment Dates must be within the same Day!");
            Optional<ButtonType> rs = alert.showAndWait();
            return check;
        } else if(time_start.isAfter(time_end) || time_end.isBefore(time_start)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Times cannot have End Time before Start Time\n \nOr\n\nTimes Cannot have Start Time after End Time! ");
            Optional<ButtonType> rs = alert.showAndWait();
            return check;
        }else if(start_day.isEqual(end_day) && (time_start.equals(time_end))){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cannot have Start Time same as End Time! ");
            Optional<ButtonType> rs = alert.showAndWait();
            return check;
        }else if(start_day.isEqual(end_day) && (time_start.isBefore(time_end) && time_end.isAfter(time_start))) {

            check = true;
        }
        return check;
    }

}
