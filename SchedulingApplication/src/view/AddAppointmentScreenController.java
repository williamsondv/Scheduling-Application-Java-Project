/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static java.time.ZoneOffset.UTC;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
public class AddAppointmentScreenController implements Initializable {

    @FXML
    private ComboBox<String> appointmentLengthPicker;
    @FXML
    private Button backButton;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button exitButton;

    
     Stage stage;
     Parent scene;
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
    private TableColumn<Appointment, String> appointmentDateCol;
    private TableColumn<Appointment, String> appointmentTimeCol;
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
    private DatePicker appointmentDatePicker;
    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;
    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;
    @FXML
    private ComboBox<String> appointmentTimeComboBox;
    @FXML
    private TextField appointmentTitleField;
    @FXML
    private ChoiceBox<String> appointmentLocationSelector;
    private  String datePickerOutput = new String();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    StringBuilder dateTimeSb = new StringBuilder();
    StringBuilder endAppointmentTimeSb = new StringBuilder();
    private String dateTimeString = dateTimeSb.toString();
    private String endAppointmentTimeString = endAppointmentTimeSb.toString();
    
    public static ObservableList<String> getAppointmentLocationSelectorList() {
        return appointmentLocationSelectorList;
    }

    public void setAppointmentLocationSelectorList(ObservableList<String> appointmentLocationSelectorList) {
        this.appointmentLocationSelectorList = appointmentLocationSelectorList;
    }
    public static ObservableList<String> appointmentLocationSelectorList = FXCollections.observableArrayList("New York" , "Phoenix" , "London" , "Paris" , "Moscow");
     
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
    private void addAppointmentToCalendar(ActionEvent event) throws SQLException {
        if (!customerSelectionTable.getSelectionModel().isEmpty()){
        datePickerOutput = appointmentDatePicker.getValue().toString();
        dateTimeSb.setLength(0);
        endAppointmentTimeSb.setLength(0);
        
       
        dateTimeSb.append(datePickerOutput).append(" ").append(appointmentTimeComboBox.getValue()).append(":00");
        
        endAppointmentTimeSb.append(datePickerOutput).append(" ").append(Appointment.generateAppointmentEndTime(Appointment.generateTimePickerIndex(appointmentTimeComboBox.getValue()), appointmentLengthPicker.getValue())).append(":00");
        
        
        
        
        
        
        Appointment newAppointment = new Appointment(
        Integer.parseInt(appointmentIdField.getText()),
        customerSelectionTable.getSelectionModel().getSelectedItem().getCustomerIdNumber(),
        User.getUserIdForLoggedInUser(LoginController.getLoggedInUser()),
        appointmentTitleField.getText(),
        appointmentDescriptionField.getText(),
        appointmentLocationSelector.getValue(),
        appointmentContactField.getText(),
        appointmentLengthPicker.getValue(),
        appointmentUrlField.getText(),
        dateTimeSb.toString(),
        endAppointmentTimeSb.toString());
        
        LocalDateTime ldtStart = LocalDateTime.parse(newAppointment.getAppointmentStartTime(), formatter);
        ZonedDateTime startZtdLocal = ZonedDateTime.of(ldtStart, TimeZone.getDefault().toZoneId());
        ZonedDateTime startZtd = startZtdLocal.withZoneSameInstant(UTC);
        String startTimeString = startZtd.format(formatter);
        
        LocalDateTime ldtEnd = LocalDateTime.parse(newAppointment.getAppointmentEndTime(), formatter);
        ZonedDateTime endZtdLocal = ZonedDateTime.of(ldtEnd, TimeZone.getDefault().toZoneId());
        ZonedDateTime endZtd = endZtdLocal.withZoneSameInstant(UTC);
        String endTimeString = endZtd.format(formatter);
        
        if (Appointment.checkForAppointmentOverlap(ldtStart, ldtEnd)) {
               if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr")) { 
                Alert alert = new Alert(Alert.AlertType.ERROR, SchedulingApplication.getRb().getString("appointments_overlap"));
                alert.setTitle(SchedulingApplication.getRb().getString("appointments_overlap"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An appointment is already scheduled for this time. Please try again.");
                alert.setTitle("Appointment Overlap");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK); }
        } else {
        Appointment.getAllAppointments().add(newAppointment);
        
        appointmentTableView.setItems(Appointment.getAllAppointments());
        appointmentIdField.setText(Integer.toString(Appointment.generateAppointmentId(Appointment.getAllAppointments())));
        Appointment.getAppointmentIdArray().clear();
        
        
        
       
        
        
        endAppointmentTimeSb.setLength(0);
        dateTimeSb.setLength(0);
        
        conn.createStatement().executeUpdate("INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "VALUES (" + newAppointment.getAppointmentId() + ", " + newAppointment.getCustomerId() + ", " + newAppointment.getUserId()  + ", '" + newAppointment.getAppointmentTitle() + "', '" + newAppointment.getAppointmentDescription() + "', '" +
                           newAppointment.getAppointmentLocation() + "', '" + newAppointment.getAppointmentContact() + "', '" + newAppointment.getAppointmentType() + "', '" + newAppointment.getAppointmentUrl() + "', '" + startTimeString + "', '" + endTimeString + 
                           "', NOW(), '" + LoginController.getLoggedInUser() + "', NOW(), '" + LoginController.getLoggedInUser() + "');");
        
              if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, SchedulingApplication.getRb().getString("add_appointment_success"));
                alert.setTitle(SchedulingApplication.getRb().getString("add_appointment_success"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                } else if (!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr")) {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment Has Been Successfully Added.");
                 alert.setTitle("Appointment Scheduling Successful");
                 Optional<ButtonType> result = alert.showAndWait();
                 if (result.get() == ButtonType.OK){}
                 
                } else {
                
            
                }}
        
        
                if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr") && customerSelectionTable.getSelectionModel().isEmpty()) { 
                Alert alert = new Alert(Alert.AlertType.ERROR, SchedulingApplication.getRb().getString("please_select_customer"));
                alert.setTitle(SchedulingApplication.getRb().getString("please_select_customer"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                } else if (customerSelectionTable.getSelectionModel().isEmpty()){
                 Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select A Customer For This Appointment.");
                 alert.setTitle("Select Customer");
                 Optional<ButtonType> result = alert.showAndWait();
                 if (result.get() == ButtonType.OK); 
            
        }}}
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        appointmentTimeComboBox.setItems(Appointment.getAppointmentTimeComboBoxList1());
        appointmentLengthPicker.setItems(Appointment.getAppointmentLengthList());
        appointmentLocationSelector.setItems(appointmentLocationSelectorList);
        
        
        	
        appointmentIdField.setText(Integer.toString(Appointment.generateAppointmentId(Appointment.getAllAppointments())));
        Appointment.getAppointmentIdArray().clear();
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
    }    

    

   
    
}
