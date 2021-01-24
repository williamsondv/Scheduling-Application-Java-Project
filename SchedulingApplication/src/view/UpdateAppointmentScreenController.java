/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.ZoneOffset.UTC;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class UpdateAppointmentScreenController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private ComboBox<String> appointmentLengthPicker;
    @FXML
    private Button backButton;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button exitButton;

    
    @FXML
    private TextField appointmentUrlField;
    @FXML
     private TextField appointmentContactField;
    @FXML
    private TextArea appointmentDescriptionField;
    @FXML
    private TableView<Customer> customerSelectionTable;
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
    private TextField appointmentIdField;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;
    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;
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
    private ComboBox<String> appointmentTimeComboBox;
    @FXML
    private TextField appointmentTitleField;
    @FXML
    private ChoiceBox<String> appointmentLocationSelector;
     private  String datePickerOutput = new String();
    
    StringBuilder dateTimeSb = new StringBuilder();
    StringBuilder endAppointmentTimeSb = new StringBuilder();
    private String dateTimeString = dateTimeSb.toString();
    private String endAppointmentTimeString = endAppointmentTimeSb.toString();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");

    @FXML
    private void returnToMainScreen(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }


    @FXML
    private void endProgram(ActionEvent event) {
        System.exit(0);
    }
    
     @FXML
    private void addAppointmentToCalendar(ActionEvent event) throws InterruptedException, SQLException {
    	 dateTimeSb.setLength(0);
         endAppointmentTimeSb.setLength(0);
         
        datePickerOutput = appointmentDatePicker.getValue().toString();
   
        
       
        dateTimeSb.append(datePickerOutput.trim()).append(" ").append(appointmentTimeComboBox.getValue()).append(":00");
        
        endAppointmentTimeSb.append(datePickerOutput).append(" ").append(Appointment.generateAppointmentEndTime(Appointment.generateTimePickerIndex(appointmentTimeComboBox.getValue()), appointmentLengthPicker.getValue())).append(":00");
        
        int appointmentIndex = Appointment.getAllAppointments().indexOf(appointmentTableView.getSelectionModel().getSelectedItem());
        
        
        
        
        Appointment newAppointment = new Appointment(
        Integer.parseInt(appointmentIdField.getText()),
        Customer.getCustomerIdFromName(appointmentContactField.getText()),
        User.getUserIdForLoggedInUser(LoginController.getLoggedInUser()),
        appointmentTitleField.getText(),
        appointmentDescriptionField.getText(),
        appointmentLocationSelector.getValue(),
        appointmentContactField.getText(),
        appointmentLengthPicker.getValue(),
        appointmentUrlField.getText(),
        dateTimeSb.toString(),
        endAppointmentTimeSb.toString());
        LocalDateTime ldtStart;
       
        try {
        Appointment.getAllAppointments().set(appointmentIndex, newAppointment);
        } catch(Exception e) {
        	 Alert alert = new Alert(Alert.AlertType.WARNING, "Please Select An Appointment to Update.");
             alert.setTitle("Select Appointment");
        }
        
        ldtStart = LocalDateTime.parse(newAppointment.getAppointmentStartTime(), formatter);
        
        ldtStart = LocalDateTime.parse(newAppointment.getAppointmentStartTime(), formatter1);
        
        ZonedDateTime startZtdLocal = ZonedDateTime.of(ldtStart, TimeZone.getDefault().toZoneId());
        ZonedDateTime startZtd = startZtdLocal.withZoneSameInstant(UTC);
        String startTimeString = startZtd.format(formatter);
        
        LocalDateTime ldtEnd = LocalDateTime.parse(newAppointment.getAppointmentEndTime(), formatter);
        ZonedDateTime endZtdLocal = ZonedDateTime.of(ldtEnd, TimeZone.getDefault().toZoneId());
        ZonedDateTime endZtd = endZtdLocal.withZoneSameInstant(UTC);
        String endTimeString = endZtd.format(formatter);
        
        conn.createStatement().executeUpdate("UPDATE appointment SET appointmentId = " + newAppointment.getAppointmentId() + ", customerId = " + newAppointment.getCustomerId() + ", userId = " + User.getUserIdForLoggedInUser(LoginController.getLoggedInUser()) + ", "
                + "title = '" + newAppointment.getAppointmentTitle() + "', description = '" + newAppointment.getAppointmentDescription() + "', location = '" + newAppointment.getAppointmentLocation() + "', contact = '" + newAppointment.getAppointmentContact() + "', "
                + "type = '" + newAppointment.getAppointmentType() + "', url = '" + newAppointment.getAppointmentUrl() + "', start = '" + startTimeString + "', end = '" + endTimeString + "', lastUpdate = NOW(), " + 
                  "lastUpdateBy = '" + LoginController.getLoggedInUser() + "' WHERE appointmentId = " + newAppointment.getAppointmentId() + ";");
        
        dateTimeSb.setLength(0);
        endAppointmentTimeSb.setLength(0);
        startTimeString = null;
        endTimeString = null;
        
       if ((Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))) {
             Alert alert = new Alert(Alert.AlertType.WARNING, SchedulingApplication.getRb().getString("appointment_update_success"));
               alert.setTitle(SchedulingApplication.getRb().getString("appointment_update_success"));
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
       } else {
         Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment Has Been Successfully Updated");
              alert.setTitle("Update Successful");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK); 
        }}
    
       
    
    public void populateAppointmentTextFields() throws InterruptedException{
      
        appointmentIdField.setText(Integer.toString(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId()));
        String localDateString = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentStartTime().substring(0, Math.min(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentStartTime().length(), 10));
        appointmentDatePicker.setValue(LocalDate.parse(localDateString));
        appointmentLengthPicker.setValue(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentType());
        appointmentTitleField.setText(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentTitle());
        String startTimeString = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentStartTime().substring(10,16);
        appointmentTimeComboBox.setValue(startTimeString);
        appointmentUrlField.setText(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentUrl());
        appointmentLocationSelector.setValue(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentLocation());
        appointmentDescriptionField.setText(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentDescription());
        appointmentContactField.setText(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentContact());
    
    
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        appointmentTimeComboBox.setItems(Appointment.getAppointmentTimeComboBoxList1());
        appointmentLengthPicker.setItems(Appointment.getAppointmentLengthList());
        appointmentLocationSelector.setItems(AddAppointmentScreenController.getAppointmentLocationSelectorList());
        
        
        
        Collections.sort(Customer.getAllCustomers());
        customerSelectionTable.setItems(Customer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerIdNumber"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerStreetCol.setCellValueFactory(new PropertyValueFactory<>("customerStreetAddress"));
        customerCityCol.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerZipCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));
        customerActiveCol.setCellValueFactory(new PropertyValueFactory<>("activeCustomer"));
        
        appointmentTableView.setItems(Appointment.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTime"));
        appointmentLengthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentUrlCol.setCellValueFactory(new PropertyValueFactory<>("appointmentUrl"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        
        appointmentContactField.setEditable(false);
        
        customerSelectionTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
        appointmentContactField.setText(customerSelectionTable.getSelectionModel().getSelectedItem().getCustomerName());
    });
        //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
        appointmentTableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(!appointmentTableView.getSelectionModel().isEmpty()){
            try {   
                populateAppointmentTextFields();
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateAppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(UpdateAppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }}
    });

                

    
     
    }

   
}
