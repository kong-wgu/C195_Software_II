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



    public void initialize() throws SQLException {

        System.out.println("Customer Screen loaded");

        try{
            ObservableList<String> CountryList = FXCollections.observableArrayList();

            allDivisions = divisionDAO.getAllDivisions();
            allCountries = countryDAO.getAllCountries();

            allCountries.forEach(country -> CountryList.add(country.getName()));
            Modify_Customer_Country_ChoiceBox.setItems(CountryList);

            Customer selectedCustomer = helper.holderData.getHolderCustomer();

            Modify_Customer_ID_TextField.setText(Long.toString(selectedCustomer.getID()));
            Modify_Customer_Name_TextField.setText(selectedCustomer.getName());
            Modify_Customer_Address_TextField.setText(selectedCustomer.getAddress());
            Modify_Customer_Phone_Number_TextField.setText(selectedCustomer.getPhone());
            Modify_Customer_Postal_Code_TextField.setText(selectedCustomer.getPostalCode());


        }catch(SQLException e){
            throw new SQLException("Failed to load one or more List, check DataBase Package");
        }

    }


    /** */
    public void Modify_Customer_Save_Button_Clicked(ActionEvent actionEvent) throws Exception {

        if(!check_for_blanks()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Ensure that all fields are filled in!");
            Optional<ButtonType> rs = alert.showAndWait();
        }else{

            long id = Long.parseLong(Modify_Customer_ID_TextField.getText());
            String name = Modify_Customer_Name_TextField.getText();
            String address = Modify_Customer_Address_TextField.getText();
            String phone = Modify_Customer_Phone_Number_TextField.getText();
            String country = Modify_Customer_Country_ChoiceBox.getValue();
            String state = Modify_Customer_State_ChoiceBox.getValue();
            String postalCode = Modify_Customer_Postal_Code_TextField.getText();

            Customer newCustomer = new Customer(id, name, address,postalCode, phone, currentDivisionID, state);

            String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            customerDAO.updateCustomer(newCustomer, createDate);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }

    /** */
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

            return true;

        }catch (Exception e){
            throw new Exception("One of the textfield is empty");
        }

    }


    public void country_selected(ActionEvent actionEvent) {
        String selectedcountry = Modify_Customer_Country_ChoiceBox.getValue();
        long countryid = 0; boolean found = false;
        for(Country country : allCountries ){
            String name = country.getName();
            long id = country.getID();
            if(name == selectedcountry){
                countryid = id;
                currentCountryID = id;
                found = true;
            }
        }

        if(found == true){
            ObservableList<String> divisionList = FXCollections.observableArrayList();

            for(Division div : allDivisions){
                long divisionID = div.getCountryID();
                if(divisionID == countryid){
                    currentDivisionID = div.getID();
                    String state = div.getName();
                    divisionList.add(state);
                }
            }
            Modify_Customer_State_ChoiceBox.setItems(divisionList);
        }

    }
}
