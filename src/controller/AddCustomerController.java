package controller;

import Database.countryDAO;
import Database.customerDAO;
import Database.divisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.Division;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController {

    @FXML private TextField Add_Customer_ID_TextField;
    @FXML private TextField Add_Customer_Name_TextField;
    @FXML private TextField Add_Customer_Address_TextField;
    @FXML private TextField Add_Customer_Phone_Number_TextField;
    @FXML private TextField Add_Customer_Postal_Code_TextField;
    @FXML private ChoiceBox Add_Customer_Country_ChoiceBox;
    @FXML private ChoiceBox Add_Customer_State_ChoiceBox;



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
    public void Add_Customer_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }

    /** */
    public void Modify_Customer_Save_Button_Clicked(ActionEvent actionEvent) {
    }

    /** */
    public void Modify_Customer_Cancel_Button_Clicked(ActionEvent actionEvent) {
    }
}
