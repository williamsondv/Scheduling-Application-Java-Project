/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class ReportsScreenController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentConsultantSchedule;
    @FXML
    private TableColumn<Appointment, Integer> IdCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> urlCol;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private ChoiceBox<Integer> monthsChoiceBox;
    @FXML
    private TextField numberOfAppointmentsByMonthTextField;
    @FXML
    private ChoiceBox<String> locationChoiceBox;
    @FXML
    private TextField appointmentsByLocationTextField;
    @FXML
    private TableView<User> consultantTableView;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    
     Stage stage;
     Parent scene;
    @FXML
    private TableColumn<User, String> consultantNameCol;
    @FXML
    private TableColumn<User, Integer> consultantIdCol;
    
    

      @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void backToMainScreen(ActionEvent event) {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
         stage.setScene(new Scene(scene));
         stage.show();
    }
     
     
     
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        monthsChoiceBox.setItems(Appointment.getAppointmentsByMonthComboBoxList());
        locationChoiceBox.setItems(AddAppointmentScreenController.getAppointmentLocationSelectorList());
        
        appointmentConsultantSchedule.setItems(Appointment.getAllAppointments());
        IdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        urlCol.setCellValueFactory(new PropertyValueFactory<>("appointmentUrl"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        
        consultantTableView.setItems(model.User.getAllUsers());
        consultantIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        consultantNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        
         //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
        consultantTableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            appointmentConsultantSchedule.setItems(User.findAppointmentsByUser(consultantTableView.getSelectionModel().getSelectedItem()));
            
        });
        //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
        monthsChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
             numberOfAppointmentsByMonthTextField.setText(Integer.toString(Appointment.generateNumberOfAppointmentsByMonth(monthsChoiceBox.getValue())));
            
        });
        //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
             locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
             appointmentsByLocationTextField.setText(Integer.toString(Appointment.generateNumberOfAppointmentsByLocation(locationChoiceBox.getSelectionModel().getSelectedItem())));
         }); 

   
    
}
}