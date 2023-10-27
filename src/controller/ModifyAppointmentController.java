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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import helper.holderData;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/***
 * Controller for the modify appointment form, will set, get, and validation information for and on the form.
 */
public class ModifyAppointmentController {

    @FXML private TextField Modify_Appointment_ID_TextField;
    @FXML private TextField Modify_Appointment_Type_TextField;
    @FXML private TextField Modify_Appointment_Title_TextField;
    @FXML private TextField Modify_Appointment_Description_TextField;
    @FXML private TextField Modify_Appointment_Location_TextField;

    @FXML private DatePicker Modify_Appointment_Start_Date_DatePicker;
    @FXML private ChoiceBox<String> Modify_Appointment_Start_Time_ChoiceBox;
    @FXML private DatePicker Modify_Appointment_End_Date_DatePicker;
    @FXML private ChoiceBox<String> Modify_Appointment_End_Time_ChoiceBox;
    @FXML private ChoiceBox<String> Modify_Appointment_Customer_ID_ChoiceBox;
    @FXML private ChoiceBox<String> Modify_Appointment_User_ID_ChoiceBox;
    @FXML private ChoiceBox<String> Modify_Appointment_Contact_ChoiceBox;

    @FXML private Button Modify_Appointment_Save_Button;
    @FXML private Button Modify_Appointment_Cancel_Button;

    private ObservableList<String> ContactNames;
    private ObservableList<Contact> allContacts;
    private ObservableList<Appointment> allAppointments;

    /***
     * Intitializes the form with the Appointment placeholder that was set when the user click the button the main screen form.
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        try {
            // Gathers all contact and appointments and sets them into a variable placeholder.
            ContactNames = FXCollections.observableArrayList();
            allContacts = contactDAO.getAllContacts();
            allAppointments = appointmentDAO.getAllAppointments();

            // Lambda #1
            allContacts.forEach(contact -> ContactNames.add(contact.getName() + " : " + contact.getID()));
            Modify_Appointment_Contact_ChoiceBox.setItems(ContactNames);

            ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
            ObservableList<String> CustomerID = FXCollections.observableArrayList();

            allCustomers.forEach(customer -> CustomerID.add(customer.getName() + " : " + Long.toString(customer.getID())));
            Modify_Appointment_Customer_ID_ChoiceBox.setItems(CustomerID);

            ObservableList<User> allUsers = UserDAO.getAllUsers();
            ObservableList<String> UserID = FXCollections.observableArrayList();

            allUsers.forEach(user -> UserID.add(Long.toString(user.getUserID())));
            Modify_Appointment_User_ID_ChoiceBox.setItems(UserID);

            // Returns all the available times of teh day to choose. Sets the list into hte choicebox.
            Modify_Appointment_Start_Time_ChoiceBox.setItems(helper.timeHelper.getAppointmentTimeList());
            Modify_Appointment_End_Time_ChoiceBox.setItems(helper.timeHelper.getAppointmentTimeList());

            // Goes through all appoints to the get the placeholder Appointment information. we could transfer it, but much safer to populate data from database.
            for(Appointment app: allAppointments){
                long id = app.getID();
                // Sets all information fields with the found appointment.
                if (id == holderData.getSelected_Appointment()){
                    Modify_Appointment_ID_TextField.setText(Long.toString(app.getID()));
                    Modify_Appointment_Title_TextField.setText(app.getTitle());
                    Modify_Appointment_Description_TextField.setText(app.getDescription());
                    Modify_Appointment_User_ID_ChoiceBox.setValue( Long.toString(app.getUserID()));
                    Appointment selectedapp = holderData.getHolderAppointment();

                    // Gathers all customer information and sets it in the choicebox to choose from.
                    for(Customer customer : allCustomers){
                        long customerid = customer.getID();
                        if (selectedapp.getCustomerID() == customerid){
                            String customerName = customer.getName();
                            String name = customerName + " : " + Long.toString(customerid);
                            Modify_Appointment_Customer_ID_ChoiceBox.setValue(name);
                            break;
                        }
                    }

                    // Gathers all Contact information and sets it in the choicebox to choose from.
                    for(Contact contact : allContacts){
                        long contactID = contact.getID();
                        if(selectedapp.getContactID() == contactID){
                            String contactName = contact.getName();
                            String name = contactName + " : " + Long.toString(contactID);
                            Modify_Appointment_Contact_ChoiceBox.setValue(name);
                            break;
                        }
                    }

                    // Sets the information of the appointment into the fields
                    Modify_Appointment_Contact_ChoiceBox.setValue(Long.toString(app.getContactID()));
                    Modify_Appointment_Start_Date_DatePicker.setValue(app.getStartTime().toLocalDate());
                    Modify_Appointment_End_Date_DatePicker.setValue(app.getEndTime().toLocalDate());

                    Modify_Appointment_Start_Time_ChoiceBox.setValue(app.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
                    Modify_Appointment_End_Time_ChoiceBox.setValue(app.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
                    Modify_Appointment_Location_TextField.setText(app.getLocation());
                    Modify_Appointment_Type_TextField.setText(app.getType());

                    // Leave loop as the needed instructions are all done.
                    break;
                }
            }

        }catch (SQLException e){
            throw new SQLException("Error Loading Data list, check DBConnection.");
        }

    }

    /***
     * Modify button clicked on the Modify Appointment form.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void Modify_Appointment_Button_Clicked(ActionEvent actionEvent) throws SQLException, IOException {

        try{
            // Checks to see if there are any blank fields on the form. Will also check the times and dates to make sure that they are validated as well.
            if (!check_for_Blanks()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
                Optional<ButtonType> rs = alert.showAndWait();
            }else if (check_times()) {

                // Sets all variable placeholders to the data fields entered on the form.
                String id = Modify_Appointment_ID_TextField.getText();
                String type = Modify_Appointment_Type_TextField.getText();
                String title = Modify_Appointment_Title_TextField.getText();
                String description = Modify_Appointment_Description_TextField.getText();
                String location = Modify_Appointment_Location_TextField.getText();
                LocalDate start_date = Modify_Appointment_Start_Date_DatePicker.getValue();
                LocalDate end_date = Modify_Appointment_End_Date_DatePicker.getValue();
                String start_time = Modify_Appointment_Start_Time_ChoiceBox.getValue();
                String end_time = Modify_Appointment_End_Time_ChoiceBox.getValue();

                String Customer_ID = Modify_Appointment_Customer_ID_ChoiceBox.getValue();

                // Grabs the ID on the ends of the choicebox so it can be assigned to the variable.
                int lastIndent = Customer_ID.lastIndexOf(" ");
                lastIndent = lastIndent + 1;
                Customer_ID = Customer_ID.substring(lastIndent);

                String user_ID = Modify_Appointment_User_ID_ChoiceBox.getValue();

                String contact = Modify_Appointment_Contact_ChoiceBox.getValue();
                lastIndent = contact.lastIndexOf(" ");
                lastIndent = lastIndent + 1;
                contact = contact.substring(lastIndent);

                // Formats date and time together so it can formatted correctly to be uploaded to the database.
                String startDate = start_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                startDate = startDate + " " + start_time + ":00";

                String endDate = end_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDate = endDate + " " + end_time + ":00";

                String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

                String lastUpdate = createDate;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


                long dummy_id = Long.parseLong(id);
                LocalDateTime test_startDate = LocalDateTime.parse(startDate, formatter);
                LocalDateTime test_endDate = LocalDateTime.parse(endDate, formatter);
                long dummy_customer_ID = Long.parseLong(Customer_ID);
                long dummy_user_ID = Long.parseLong(user_ID);
                long dummy_contact = Long.parseLong(contact);


                Appointment test = new Appointment(dummy_id, title, description, location, type, test_startDate, test_endDate, lastUpdate,
                        dummy_customer_ID, dummy_user_ID, dummy_contact);

                if(calculatehelper.conflictswithAppointments(allAppointments, test)) {
                    appointmentDAO.modifyAppointment(id, title, description, location, type, startDate,
                            endDate, lastUpdate, Customer_ID, user_ID, contact);

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
     * Cancel button functionality
     * @param actionEvent
     * @throws Exception
     */
    public void Modify_Appointment_Cancel_Button_Clicked(ActionEvent actionEvent) throws Exception{
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
     * Checks to see if there are any blank fields on the form.
     * @return
     * @throws NullPointerException
     */
    public boolean check_for_Blanks() throws NullPointerException{
        try {
            String title = Modify_Appointment_Title_TextField.getText();
            String type = Modify_Appointment_Type_TextField.getText();
            String description = Modify_Appointment_Description_TextField.getText();
            String location = Modify_Appointment_Location_TextField.getText();
            LocalDate startDate = Modify_Appointment_Start_Date_DatePicker.getValue();
            LocalTime empty = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime startTime = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse("12:00" , DateTimeFormatter.ofPattern("HH:mm"));
            if(Modify_Appointment_Start_Time_ChoiceBox.getValue() != null){
                startTime = LocalTime.parse(Modify_Appointment_Start_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            LocalDate endDate = Modify_Appointment_End_Date_DatePicker.getValue();
            if(Modify_Appointment_End_Time_ChoiceBox.getValue() != null) {
                endTime = LocalTime.parse(Modify_Appointment_End_Time_ChoiceBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            String userID = Modify_Appointment_User_ID_ChoiceBox.getValue();
            String customerID = Modify_Appointment_Customer_ID_ChoiceBox.getValue();
            String contact = Modify_Appointment_Contact_ChoiceBox.getValue();

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
     * Checks the time to validate all possible errors.
     * @return
     */
    public boolean check_times(){

        LocalDate start_day = Modify_Appointment_Start_Date_DatePicker.getValue();
        LocalDate end_day = Modify_Appointment_End_Date_DatePicker.getValue();
        String start_time = Modify_Appointment_Start_Time_ChoiceBox.getValue();
        String end_time = Modify_Appointment_End_Time_ChoiceBox.getValue();

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
