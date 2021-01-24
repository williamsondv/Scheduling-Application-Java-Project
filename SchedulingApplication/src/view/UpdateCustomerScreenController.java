/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import schedulingapplication.SchedulingApplication;
import static util.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class UpdateCustomerScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private Button mainScreen;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneNumberField;
    @FXML
    private TableView<Customer> customerUpdateTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerPhoneCol;
    @FXML
    private TableColumn<Customer, String> customerStreetCol;
    @FXML
    private TableColumn<Customer, String> customerCityCol;
    @FXML
    private TableColumn<Customer, String> customerCountryCol;
    @FXML
    private TableColumn<Customer, String> customerZipCodeCol;
    @FXML
    private TableColumn<Customer, Boolean> customerActiveCol;
    @FXML
    private TextField customerStreetField;
    @FXML
    private TextField customerCountryField;
    @FXML
    private TextField customerZipCodeField;
    @FXML
    private ChoiceBox<String> customerCityChoiceBox;
    @FXML
    private ChoiceBox<String> activeCustomerChoiceBox;
    private int cityIdNumber = 1;
    private int countryIdNumber = 1;
    private int activeDigital = 1;
    private boolean activeCustomer = true;
    private int customerIndex;
    
     @FXML
    private void updateCustomerRecord(ActionEvent event) throws SQLException {
        if (!customerUpdateTable.getSelectionModel().isEmpty()){
        
        customerIndex = Customer.getAllCustomers().indexOf(customerUpdateTable.getSelectionModel().getSelectedItem());
        int customerId = Integer.parseInt(customerIdField.getText());
        String customerName = customerNameField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();
        String customerStreetAddress = customerStreetField.getText();
        String customerCity = customerCityChoiceBox.getValue();
        String customerCountry = customerCountryField.getText();
        String customerZipCode = customerZipCodeField.getText();
        
        
        if(activeCustomerChoiceBox.getSelectionModel().getSelectedItem().contains("Yes")){
        activeCustomer = true; 
        activeDigital = 1;
        } else {
        activeCustomer = false;
        activeDigital = 0;
        }
        
        Customer newCustomer = new Customer(customerId, customerName, customerPhoneNumber, customerStreetAddress, customerCity, customerCountry, customerZipCode, activeCustomer);
        
        Customer.getAllCustomers().set(customerIndex, newCustomer);
        
        conn.createStatement().executeUpdate("UPDATE customer SET customerName = '" + newCustomer.getCustomerName() + "',active = " + activeDigital + ", lastUpdate = NOW(), lastUpdateBy = '" + LoginController.getLoggedInUser() + "' WHERE customerId = " + newCustomer.getCustomerIdNumber() + ";");
        conn.createStatement().executeUpdate("UPDATE address SET address = '" + newCustomer.getCustomerStreetAddress() + "', cityId = " + cityIdNumber + ", postalCode  = '" + newCustomer.getCustomerZipCode() + "', phone = '" + newCustomer.getCustomerPhoneNumber() + "', lastUpdate = NOW(), lastUpdateBy = '" + LoginController.getLoggedInUser() + "' WHERE addressId = " + newCustomer.getCustomerIdNumber() + ";");
        
        customerUpdateTable.setItems(Customer.getAllCustomers());
        
           customerIdField.setText("");
           customerNameField.setText("");
           customerPhoneNumberField.setText("");
           customerStreetField.setText("");
           customerCityChoiceBox.getSelectionModel().selectFirst();
           customerCountryField.setText("United States");
           customerZipCodeField.setText("");
           activeCustomerChoiceBox.getSelectionModel().selectFirst();
           
           if ((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))) {
             Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("customer_update_success"));
               alert.setTitle(SchedulingApplication.getRb().getString("customer_update_success"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
              
           
       } else if ((!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr"))) {
        
              Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"Customer record has been successfully updated.");
              alert1.setTitle("Successful Update");
              Optional<ButtonType> result = alert1.showAndWait();
              if (result.get() == ButtonType.OK);{}
              
        } } else if((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))){
               Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("select_customer_record"));
               alert.setTitle(SchedulingApplication.getRb().getString("select_customer_record"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
        } else {
              Alert alert = new Alert(Alert.AlertType.WARNING,"Please select a customer record to update.");
              alert.setTitle("Select Record");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
    }}
    

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
private void  populateTextFields() throws InterruptedException  {
        try {
        Thread.sleep(20);
        customerNameField.setText(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerName());
        customerPhoneNumberField.setText(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerPhoneNumber());
        customerIdField.setText(Integer.toString(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerIdNumber()));
        customerStreetField.setText(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerStreetAddress());
        customerZipCodeField.setText(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerZipCode());
        
        customerCityChoiceBox.setValue(customerUpdateTable.getSelectionModel().getSelectedItem().getCustomerCity());
        
        
        if (customerUpdateTable.getSelectionModel().getSelectedItem().isActiveCustomer()) {
        activeCustomerChoiceBox.getSelectionModel().select("Yes");
        } else {
        activeCustomerChoiceBox.getSelectionModel().select("No");
        }
        } catch (NullPointerException e) {
        
        
        }
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> choiceBoxActive = FXCollections.observableArrayList("Yes","No");
        ObservableList<String> cityBox = FXCollections.observableArrayList("New York" , "Phoenix" , "London" , "Paris" , "Moscow");
        
        customerCityChoiceBox.setItems(cityBox);
        customerCountryField.setText("United States");
        activeCustomerChoiceBox.setItems(choiceBoxActive);
        customerCityChoiceBox.getSelectionModel().selectFirst();
       
        activeCustomerChoiceBox.getSelectionModel().selectFirst();
        customerIdField.setText("");
        Customer.getCustomerIdArray().clear();
        customerIdField.setEditable(false);
        
      
         //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
        customerCityChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
        try { if (customerCityChoiceBox.getSelectionModel().getSelectedItem().contains("Phoenix") || customerCityChoiceBox.getSelectionModel().getSelectedItem().contains("New York")){
            if(customerCityChoiceBox.getSelectionModel().getSelectedItem().contains("Phoenix")) {
            customerCountryField.setText("United States");
            countryIdNumber = 1;
            cityIdNumber = 4;
                } else {
            customerCountryField.setText("United States");
            countryIdNumber = 1;
            cityIdNumber = 2;
            }
        } else if (customerCityChoiceBox.getSelectionModel().getSelectedItem().contains("London")) {
        customerCountryField.setText("United Kingdom");
        countryIdNumber = 4;
        cityIdNumber = 3;
        } else if (customerCityChoiceBox.getSelectionModel().getSelectedItem().contains("Paris")) {
        customerCountryField.setText("France");
        countryIdNumber = 3;
        cityIdNumber = 5;
        } else {
        customerCountryField.setText("Russia");
        countryIdNumber = 3;
        cityIdNumber = 1;
            }} catch (Exception ex) {
                Logger.getLogger(AddCustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
        
        
        Collections.sort(Customer.getAllCustomers());
        customerUpdateTable.setItems(Customer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerIdNumber"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerStreetCol.setCellValueFactory(new PropertyValueFactory<>("customerStreetAddress"));
        customerCityCol.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerZipCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));
        customerActiveCol.setCellValueFactory(new PropertyValueFactory<>("activeCustomer"));
        
        customerUpdateTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                populateTextFields();
            } catch (InterruptedException ex) {
                Logger.getLogger(UserUpdateScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
    }    

   
    
}
