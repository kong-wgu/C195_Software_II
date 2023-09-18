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
import java.util.Optional;
import java.util.ResourceBundle;

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


    public void initialize() throws SQLException {

        System.out.println("Customer Screen loaded");

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


    /** */
    public void Add_Customer_Save_Button_Clicked(ActionEvent actionEvent) {
    }

    /** */
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

    public boolean check_for_blanks() throws Exception{
        try {
            String name = Add_Customer_Name_TextField.getText();
            String address = Add_Customer_Address_TextField.getText();
            String number = Add_Customer_Phone_Number_TextField.getText();
            String country = Add_Customer_Country_ChoiceBox.getValue();
            String state = Add_Customer_State_ChoiceBox.getValue();
            String postalCode = Add_Customer_Postal_Code_TextField.getText();

            if (name.isBlank() || address.isBlank() || address.isBlank() || number.isBlank() ||
            country.isBlank() || state.isBlank() || postalCode.isBlank()){
                return false;
            }

            return true;

        }catch (Exception e){
            throw new Exception("One of the textfield is empty");
        }

    }

    /** */
    public void Modify_Customer_Save_Button_Clicked(ActionEvent actionEvent) {
    }

    /** */
    public void Modify_Customer_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }

    public void country_selected(TouchEvent touchEvent) {
        System.out.println("Item Selected!");
    }
}