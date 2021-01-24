/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import schedulingapplication.SchedulingApplication;
import static util.DBConnection.conn;
/**
 * FXML Controller class
 *
 * @author willi
 */
public class MainScreenController implements Initializable {


    @FXML
    private Button appointmentAdd;
    @FXML
    private Button appointmentDelete;
    @FXML
    private Button exitCloseProgram;
    @FXML
    private Button appointmentUpdate;
    @FXML
    private Button weeklyView;
    @FXML
    private Button monthView;
    @FXML
    private Button addCustomer;
    @FXML
    private Button updateCustomer;
    @FXML
    private Button deleteCustomer;
    @FXML
    private Button addUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button updateUser;
    
    
    
    
    
    Stage stage;
    Parent scene;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    
    @FXML
    private TableColumn<Appointment, String> appointmentLengthCol;
    @FXML
    private TableColumn<Appointment, String> appointmentUrlCol;
    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;
    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;
    private ObservableList<LocalDateTime> weeklyAppointmentList = FXCollections.observableArrayList();
    @FXML
    private Button viewAll;
    @FXML
    private Button reports;
    
    
    
     @FXML
    private void addAppointment(ActionEvent event) throws IOException {
        
         
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("AddAppointmentScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.centerOnScreen();
         stage.show();
         
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }
    
     @FXML
    private void deleteAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("DeleteAppointmentScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.centerOnScreen();
         stage.show();
    }

    @FXML
    private void updateAppointmentScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("UpdateAppointmentScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.centerOnScreen();
         stage.show();
    }

    @FXML
    private void addCustomerScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("AddCustomerScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }

    @FXML
    private void updateCustomerScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("UpdateCustomerScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("CustomerDeleteScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }
    
    @FXML
    private void addUser(ActionEvent event) throws IOException {
        
         if (LoginController.getLoggedInUser().contains("Admin") || LoginController.getLoggedInUser().contains("Test")){
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
         } else if((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))) {
             Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("no_authorization"));
               alert.setTitle(SchedulingApplication.getRb().getString("no_authorization"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         } else {
         Alert alert = new Alert(Alert.AlertType.WARNING,"Sorry, you are not authorized to modify Users.");
               alert.setTitle("No Authorization");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         }
         }
    
     @FXML
    private void updateUser(ActionEvent event) throws IOException {
        
         if (LoginController.getLoggedInUser().contains("Admin") || LoginController.getLoggedInUser().contains("Test")) {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("UserUpdateScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
         } else if((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))) {
             Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("no_authorization"));
               alert.setTitle(SchedulingApplication.getRb().getString("no_authorization"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         } else {
         Alert alert = new Alert(Alert.AlertType.WARNING,"Sorry, you are not authorized to modify Users.");
               alert.setTitle("No Authorization");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         }
         }
        
    
     @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        
         if (LoginController.getLoggedInUser().contains("Admin") || LoginController.getLoggedInUser().contains("Test")) {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("UserDeleteScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
         } else if((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))) {
             Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("no_authorization"));
               alert.setTitle(SchedulingApplication.getRb().getString("no_authorization"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         } else {
              Alert alert = new Alert(Alert.AlertType.WARNING,"Sorry, you are not authorized to modify Users.");
              alert.setTitle("No Authorization");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
         }
         }
    
    

    @FXML
    private void toggleWeeklyView(ActionEvent event) {
        appointmentTableView.setItems(Appointment.displayWeeklyAppointments(Appointment.getAllAppointments(), LocalDateTime.now()));
       
    }

    @FXML
    private void toggleMonthlyView(ActionEvent event) {
        appointmentTableView.setItems(Appointment.displayMonthlyAppointments(Appointment.getAllAppointments(), LocalDateTime.now()));
    }
    
    @FXML
    private void toggleViewAll(ActionEvent event) {
        appointmentTableView.setItems(Appointment.getAllAppointments());
    }
    
    @FXML
    private void openReportsScreen(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
         stage.setScene(new Scene(scene));
         stage.centerOnScreen();
         stage.show();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
                       Customer.setCustomerResultSet();
                       
                       Customer.initializeAllCustomersFromDatabase();
                       
                       User.setUserResultSet();
                       
                       User.initializeAllUsersFromDatabase();
                       
                       Appointment.setAppointmentResultSet();
                       
                       Appointment.initializeAllAppointmentsFromDatabase();
                       
        appointmentTableView.setItems(Appointment.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));
        appointmentLengthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentUrlCol.setCellValueFactory(new PropertyValueFactory<>("appointmentUrl"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        
        if (Appointment.appointmentWithinFifteenMinutesOfLogIn(LocalDateTime.now())){
              Alert alert = new Alert(Alert.AlertType.WARNING,"Attentention! There is an appointment scheduled within 15 minutes!");
              alert.setTitle("Appointment Within 15 Minutes.");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    

    

   
    
   

}
