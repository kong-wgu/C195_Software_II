package controller;

import Database.countryDAO;
import Database.customerDAO;
import Database.divisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ModifyCustomerController {

    @FXML private TextField Modify_Customer_ID_TextField;
    @FXML private TextField Modify_Customer_Name_TextField;
    @FXML private TextField Modify_Customer_Address_TextField;
    @FXML private TextField Modify_Customer_Phone_Number_TextField;
    @FXML private TextField Modify_Customer_Postal_Code_TextField;
    @FXML private ChoiceBox<String> Modify_Customer_Country_ChoiceBox;
    @FXML private ChoiceBox<String> Modify_Customer_State_ChoiceBox;



    private ObservableList<Division> allDivisions;
    private ObservableList<Country> allCountries;
    private long currentCountryID;
    private long currentDivisionID;


    /***
     * Sets all the fields from the selected customer from the main screen
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        System.out.println("Customer Screen loaded");

        try{
            // Collects all countries within a list
            ObservableList<String> CountryList = FXCollections.observableArrayList();

            allDivisions = divisionDAO.getAllDivisions();
            allCountries = countryDAO.getAllCountries();

            allCountries.forEach(country -> CountryList.add(country.getName()));
            Modify_Customer_Country_ChoiceBox.setItems(CountryList);

            // Variable placeholder for the selected customer.
            Customer selectedCustomer = helper.holderData.getHolderCustomer();

            Modify_Customer_ID_TextField.setText(Long.toString(selectedCustomer.getID()));
            Modify_Customer_Name_TextField.setText(selectedCustomer.getName());
            Modify_Customer_Address_TextField.setText(selectedCustomer.getAddress());
            Modify_Customer_Phone_Number_TextField.setText(selectedCustomer.getPhone());
            Modify_Customer_Postal_Code_TextField.setText(selectedCustomer.getPostalCode());


            String country = getCountryname(selectedCustomer.getDivisionID());
            String division = getDivisionName(selectedCustomer.getDivisionID());
            Modify_Customer_Country_ChoiceBox.setValue(country);
            Modify_Customer_State_ChoiceBox.setValue(division);


        }catch(SQLException e){
            throw new SQLException("Failed to load one or more List, check DataBase Package");
        }

    }

    /***
     * Save Button for the Modify Customer form
     * @param actionEvent
     * @throws Exception
     * @throws NullPointerException
     */
    public void Modify_Customer_Save_Button_Clicked(ActionEvent actionEvent) throws Exception, NullPointerException {

        // Checks for any blank within the form.
        if(!check_for_blanks()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
            Optional<ButtonType> rs = alert.showAndWait();
        }else{

            // Sets the information from the fields from the form
            long id = Long.parseLong(Modify_Customer_ID_TextField.getText());
            String name = Modify_Customer_Name_TextField.getText();
            String address = Modify_Customer_Address_TextField.getText();
            String phone = Modify_Customer_Phone_Number_TextField.getText();
            String country = Modify_Customer_Country_ChoiceBox.getValue();
            String state = Modify_Customer_State_ChoiceBox.getValue();
            String postalCode = Modify_Customer_Postal_Code_TextField.getText();
            String division = Long.toString(currentDivisionID);

            // checks to see if the data return is null
            try{
                division = getDivisionID();
            }catch(NullPointerException e){
                throw new NullPointerException("Division ID was not Found, please check the database for accurate ID.");
            }


            Customer newCustomer = new Customer(id, name, address,postalCode, phone, currentDivisionID, state);

            String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            customerDAO.updateCustomer(newCustomer, createDate, division);

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
     * Cancel Button, confirms with user first.
     * @param actionEvent
     * @throws Exception
     */
    public void Modify_Customer_Cancel_Button_Clicked(ActionEvent actionEvent) throws Exception {
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
     * Checks to see if there are any blank fields on the form
     * @return
     * @throws Exception
     */
    public boolean check_for_blanks() throws Exception{
        try {
            String name = Modify_Customer_Name_TextField.getText();
            String address = Modify_Customer_Address_TextField.getText();
            String number = Modify_Customer_Phone_Number_TextField.getText();
            String country = Modify_Customer_Country_ChoiceBox.getValue();
            String state = Modify_Customer_State_ChoiceBox.getValue();
            String postalCode = Modify_Customer_Postal_Code_TextField.getText();

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
     * Once user selects a country, the division are loaded on the choicebox
     * @param actionEvent
     */
    public void country_selected(ActionEvent actionEvent) {
        String selectedcountry = Modify_Customer_Country_ChoiceBox.getValue();
        long countryid = 0; boolean found = false;

        // Checks for the selected country, sets the current country to the selected country so it can be used to get eh divisions
        for(Country country : allCountries ){
            String name = country.getName();
            long id = country.getID();
            if(name == selectedcountry){
                countryid = id;
                currentCountryID = id;
                found = true;
            }
        }

        if(found){
            // List placeholder to collect all divisions related the country
            ObservableList<String> divisionList = FXCollections.observableArrayList();

            // checks to see any divisions that is under country, saves into the list placeholder
            for(Division div : allDivisions){
                long divisionID = div.getCountryID();
                if(divisionID == countryid){
                    currentDivisionID = div.getID();
                    String state = div.getName();
                    divisionList.add(state);
                }
            }
            // Once all divisions are found, loads all division to hte choicebox.
            Modify_Customer_State_ChoiceBox.setItems(divisionList);
        }

    }

    /***
     * Returns the country name with the given division ID
     * @param divisionID
     * @return
     */
    public String getCountryname(long divisionID){
        ObservableList<Division> divisions = allDivisions;
        ObservableList<Country> countries = allCountries;
        String countryname = "";

        // Uses the Division ID to get the country it belongs with.
        for(Division div : divisions){
           if(div.getID() == divisionID){
               long countryid = div.getCountryID();
               for(Country country : countries){
                   if(countryid == country.getID()){
                       countryname = country.getName();
                   }
               }
           }
        }

        return countryname;
    }

    /***
     * Returns the division name based off the ID given.
     * @param divisionID
     * @return
     */
    public String getDivisionName(long divisionID){
        ObservableList<Division> divisions = allDivisions;
        String name = "";

        for(Division div : divisions){
            if(div.getID() == divisionID){
                name = div.getName();
            }
        }
        return name;
    }

    /***
     * Returns the division ID by checking the choicebox.
     * @return
     * @throws NullPointerException
     */
    public String getDivisionID() throws NullPointerException{
        String divisionName = Modify_Customer_State_ChoiceBox.getValue();
        ObservableList<Division> divisions = allDivisions;
        String divisionID = null;

        // Using the choicebox value to get the division ID it is associated with.
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
