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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class CustomerDeleteScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customerDeleteTable;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Customer, Integer> IdCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> streetCol;
    @FXML
    private TableColumn<Customer, String> cityCol;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> zipCol;
    @FXML
    private TableColumn<Customer, Boolean> aciveCol;
    
    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goBackToMainScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws SQLException {
        
              if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SchedulingApplication.getRb().getString("delete_customer_confirmation"));
                alert.setTitle(SchedulingApplication.getRb().getString("delete_customer_confirmation"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                 conn.createStatement().executeUpdate("DELETE FROM appointment WHERE customerId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        conn.createStatement().executeUpdate("DELETE FROM customer WHERE customerId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        conn.createStatement().executeUpdate("DELETE FROM address WHERE addressId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        
        Customer.getAllCustomers().remove(customerDeleteTable.getSelectionModel().getSelectedItem());
        Collections.sort(Customer.getAllCustomers());
        customerDeleteTable.setItems(Customer.getAllCustomers());
        Customer.setNumberOfCustomerObjects(Customer.getNumberOfCustomerObjects() - 1);
    }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, SchedulingApplication.getRb().getString("customer_deletion_success"));
                alert1.setTitle(SchedulingApplication.getRb().getString("customer_deletion_success"));
                Optional<ButtonType> result1 = alert.showAndWait();
                if (result1.get() == ButtonType.OK){}
              
                
              } else {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the selected customer?");
              alert.setTitle("Delete Confimation");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{
        
       
        
        
        conn.createStatement().executeUpdate("DELETE FROM appointment WHERE customerId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        conn.createStatement().executeUpdate("DELETE FROM customer WHERE customerId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        conn.createStatement().executeUpdate("DELETE FROM address WHERE addressId = " + customerDeleteTable.getSelectionModel().getSelectedItem().getCustomerIdNumber() + ";");
        
        Customer.getAllCustomers().remove(customerDeleteTable.getSelectionModel().getSelectedItem());
        Collections.sort(Customer.getAllCustomers());
        customerDeleteTable.setItems(Customer.getAllCustomers());
        Customer.setNumberOfCustomerObjects(Customer.getNumberOfCustomerObjects() - 1);
    }
              Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"Customer has been successfully deleted.");
              alert1.setTitle("Customer Deletion Successful");
              Optional<ButtonType> result1 = alert1.showAndWait();
              if (result1.get() == ButtonType.OK);{}
    
    }}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Collections.sort(Customer.getAllCustomers());
        customerDeleteTable.setItems(Customer.getAllCustomers());
        IdCol.setCellValueFactory(new PropertyValueFactory<>("customerIdNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        streetCol.setCellValueFactory(new PropertyValueFactory<>("customerStreetAddress"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));
        aciveCol.setCellValueFactory(new PropertyValueFactory<>("activeCustomer"));
        
    }    


    
    
}
