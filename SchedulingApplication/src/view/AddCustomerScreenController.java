/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import static util.DBConnection.conn;
import java.lang.System;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import schedulingapplication.SchedulingApplication;
/**
 * FXML Controller class
 *
 * @author willi
 */
public class AddCustomerScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    private boolean activeCustomerTrueFalse;
    private int customerIDvariable;
    
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private ChoiceBox<String> CustomerActiveDropDown;
    @FXML
    private TextField customerPhoneNumberField;
    @FXML
    private TextField customerStreetField;
    @FXML
    private ChoiceBox<String> customerCityField;
    @FXML
    private TextField customerCountryField;
    private int activeBooleanDigital;
    private int cityIdNumber = 1;
    private int countryIdNumber = 1;
    String regexStr = "^[0-9]*$";
    String regexStr1 = "^[0-9\\-]*$";
    
    public boolean isActiveCustomerTrueFalse() {
        return activeCustomerTrueFalse;
    }

    public void setActiveCustomerTrueFalse(boolean activeCustomerTrueFalse) {
        this.activeCustomerTrueFalse = activeCustomerTrueFalse;
    }
    @FXML
    private TextField customerZipCodeField;
    
    
    
    
    @FXML
    private void addCustomerRecord(ActionEvent event) throws SQLException {
        
     
        if        
        (CustomerActiveDropDown.getSelectionModel().getSelectedItem().contains("Yes")){
        setActiveCustomerTrueFalse(true);
        setActiveBooleanDigital(1);
        } else {
        setActiveCustomerTrueFalse(false);
        setActiveBooleanDigital(0);
        }
        
        if (customerNameField.getText().isEmpty()){
              Alert alert = new Alert(Alert.AlertType.ERROR, "Customer name field must not be empty.");
              alert.setTitle("Enter Customer Name");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
        
        } else if (customerPhoneNumberField.getText().isEmpty()){
              Alert alert = new Alert(Alert.AlertType.ERROR, "Customer phone number field must not be empty.");
              alert.setTitle("Enter Customer Phone Number");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
        
        
        } else if (!customerPhoneNumberField.getText().matches(regexStr1) ) {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Customer phone number field must contain only numbers and dashes.");
              alert.setTitle("Enter Valid Customer Phone Number");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
        } else if (customerStreetField.getText().isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter The Customer's Street Address.");
              alert.setTitle("Enter Customer Street Address");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
        
        
        } else {
        
        
        
        Customer newCustomer = new Customer(
        Integer.parseInt(customerIdField.getText()),
        customerNameField.getText(),
        customerPhoneNumberField.getText(),
        customerStreetField.getText(),
        customerCityField.getSelectionModel().getSelectedItem(),
        customerCountryField.getText(),
        customerZipCodeField.getText(),
        activeCustomerTrueFalse);
        
        
           if (!Customer.customerExists(newCustomer))  {
           Customer.getAllCustomers().add(newCustomer);
           Customer.setNumberOfCustomerObjects(Customer.getNumberOfCustomerObjects() + 1);
           
           conn.createStatement().executeUpdate("INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                              "VALUES (" + newCustomer.getCustomerIdNumber() + ", '" + newCustomer.getCustomerStreetAddress() + "','', " + cityIdNumber + ", '" + newCustomer.getCustomerZipCode() + "', '" + newCustomer.getCustomerPhoneNumber() + "', NOW(),'" + 
                               LoginController.getLoggedInUser() + "', NOW(), '" + LoginController.getLoggedInUser() + "');");
           
           conn.createStatement().executeUpdate("INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                              "VALUES (" + newCustomer.getCustomerIdNumber() + ", '" + newCustomer.getCustomerName() + "', " + newCustomer.getCustomerIdNumber() + ", " + getActiveBooleanDigital() + ", NOW(), '" +
                              LoginController.getLoggedInUser() + "' , NOW(), '" + LoginController.getLoggedInUser() + "');");
           
           customerIdField.setText(Integer.toString(Customer.generateCustomerId(Customer.getAllCustomers())));
           Customer.getCustomerIdArray().clear();
           
           customerNameField.setText("");
           customerPhoneNumberField.setText("");
           customerStreetField.setText("");
           customerCityField.getSelectionModel().selectFirst();
           customerCountryField.setText("United States");
           customerZipCodeField.setText("");
           CustomerActiveDropDown.getSelectionModel().selectFirst();
           
               if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, SchedulingApplication.getRb().getString("add_customer_success"));
                alert.setTitle(SchedulingApplication.getRb().getString("add_customer_success"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                } else {
              Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer has been successfully added.");
              alert.setTitle("Select Record");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
           
           }}}}

    public int getActiveBooleanDigital() {
        return activeBooleanDigital;
    }

    public void setActiveBooleanDigital(int activeBooleanDigital) {
        this.activeBooleanDigital = activeBooleanDigital;
    }

    @FXML
    private void mainScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> choiceBoxActive = FXCollections.observableArrayList("Yes","No");
        ObservableList<String> cityBox = FXCollections.observableArrayList("New York" , "Phoenix" , "London" , "Paris" , "Moscow");
        
        customerCityField.setItems(cityBox);
        customerCountryField.setText("United States");
        CustomerActiveDropDown.setItems(choiceBoxActive);
        customerCityField.getSelectionModel().selectFirst();
       
        CustomerActiveDropDown.getSelectionModel().selectFirst();
        
        customerIdField.setText(Integer.toString(Customer.generateCustomerId(Customer.getAllCustomers())));
        
        Customer.getCustomerIdArray().clear();
        customerIdField.setEditable(false);
        
      
        
        customerCityField.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
        try { if (customerCityField.getSelectionModel().getSelectedItem().contains("Phoenix") || customerCityField.getSelectionModel().getSelectedItem().contains("New York")){
            if(customerCityField.getSelectionModel().getSelectedItem().contains("Phoenix")) {
            customerCountryField.setText("United States");
            countryIdNumber = 1;
            cityIdNumber = 4;
                } else {
            customerCountryField.setText("United States");
            countryIdNumber = 1;
            cityIdNumber = 2;
            }
        } else if (customerCityField.getSelectionModel().getSelectedItem().contains("London")) {
        customerCountryField.setText("United Kingdom");
        countryIdNumber = 4;
        cityIdNumber = 3;
        } else if (customerCityField.getSelectionModel().getSelectedItem().contains("Paris")) {
        customerCountryField.setText("France");
        countryIdNumber = 2;
        cityIdNumber = 5;
        } else {
        customerCountryField.setText("Russia");
        countryIdNumber = 3;
        cityIdNumber = 1;
            }} catch (Exception ex) {
                Logger.getLogger(AddCustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
                
            
            
        
        
        
        


   
    
}}
