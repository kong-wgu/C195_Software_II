package controller;

import Database.countryDAO;
import Database.customerDAO;
import Database.divisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/***
 * Controller for adding customer, this would allow set and get methods, also manipulates the add customer form.
 */
public class AddCustomerController {

    @FXML private TextField Add_Customer_ID_TextField;
    @FXML private TextField Add_Customer_Name_TextField;
    @FXML private TextField Add_Customer_Address_TextField;
    @FXML private TextField Add_Customer_Phone_Number_TextField;
    @FXML private TextField Add_Customer_Postal_Code_TextField;
    @FXML private ChoiceBox<String> Add_Customer_Country_ChoiceBox;
    @FXML private ChoiceBox<String> Add_Customer_State_ChoiceBox;



    private ObservableList<Division> allDivisions;
    private ObservableList<Country> allCountries;
    private long currentCountryID;
    private long currentDivisionID;

    /***
     * Grabs all information prior so it can be loaded into the choicebox for the form.
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        try{
            ObservableList<String> CountryList = FXCollections.observableArrayList();

            allDivisions = divisionDAO.getAllDivisions();
            allCountries = countryDAO.getAllCountries();

            allCountries.forEach(country -> CountryList.add(country.getName()));
            Add_Customer_Country_ChoiceBox.setItems(CountryList);

        }catch(SQLException e){
            throw new SQLException("Failed to load one or more List, check DataBase Package");
        }

    }


    /***
     * Save Button
     * @param actionEvent
     * @throws Exception
     */
    public void Add_Customer_Save_Button_Clicked(ActionEvent actionEvent) throws Exception {

        if(!check_for_blanks()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
            Optional<ButtonType> rs = alert.showAndWait();
        }else{

            String name = Add_Customer_Name_TextField.getText();
            String address = Add_Customer_Address_TextField.getText();
            String phone = Add_Customer_Phone_Number_TextField.getText();
            String country = Add_Customer_Country_ChoiceBox.getValue();
            String state = Add_Customer_State_ChoiceBox.getValue();
            String postalCode = Add_Customer_Postal_Code_TextField.getText();
            long id = 0;

            Customer newCustomer = new Customer(id, name, address,postalCode, phone, currentDivisionID, state);

            String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            customerDAO.addCustomer(newCustomer, createDate);

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
     * Cancel button that will prompt confirmation. If confirmed, will go back to the main screen.
     * @param actionEvent
     * @throws Exception
     */
    public void Add_Customer_Cancel_Button_Clicked(ActionEvent actionEvent) throws Exception {
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
     * Checks for blanks of all fields
     * @return
     * @throws Exception
     */
    public boolean check_for_blanks() throws Exception{
        try {
            String name = Add_Customer_Name_TextField.getText();
            String address = Add_Customer_Address_TextField.getText();
            String number = Add_Customer_Phone_Number_TextField.getText();
            String country = Add_Customer_Country_ChoiceBox.getValue();
            String state = Add_Customer_State_ChoiceBox.getValue();
            String postalCode = Add_Customer_Postal_Code_TextField.getText();

            // If any blanks, returns false, if not, will assign current division ID.
            if (name.isBlank() || address.isBlank() || address.isBlank() || number.isBlank() ||
            country.isBlank() || state.isBlank() || postalCode.isBlank()){
                return false;
            }

            currentDivisionID = Long.parseLong(getDivisionID());
            return true;

        }catch (Exception e){
            throw new Exception("One of the textfield is empty");
        }

    }

    /***
     * Once the user selects a Country, updates the division choicebox accordingly to the country.
     * @param actionEvent
     */
    public void country_selected(ActionEvent actionEvent) {
        String selectedcountry = Add_Customer_Country_ChoiceBox.getValue();
        long countryid = 0; boolean found = false;

        // gets country ID so correct divisions can be loaded.
        for(Country country : allCountries ){
            String name = country.getName();
            long id = country.getID();
            if(name == selectedcountry){
                countryid = id;
                currentCountryID = id;
                found = true;
            }
        }

        // gets Division thats is under the country ID and loads onto the division choicebox.
        if(found){
            ObservableList<String> divisionList = FXCollections.observableArrayList();

            for(Division div : allDivisions){
                long divisionID = div.getCountryID();
                if(divisionID == countryid){
                    currentDivisionID = div.getID();
                    String state = div.getName();
                    divisionList.add(state);
                }
            }
            Add_Customer_State_ChoiceBox.setItems(divisionList);
        }

    }

    /***
     * Gets the division ID of the division that is placed withi the choicebox for division.
     * More of a helper than of that as a module.
     * @return
     * @throws NullPointerException
     */
    public String getDivisionID() throws NullPointerException{
        String divisionName = Add_Customer_State_ChoiceBox.getValue();
        ObservableList<Division> divisions = allDivisions;
        String divisionID = null;

        for (Division div : divisions) {
            String name = div.getName();
            if (name.equals(divisionName)) {
                divisionID = Long.toString(div.getID());
                return divisionID;
            }
        }

        return divisionID;
    }
}
