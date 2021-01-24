/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import model.Appointment;
import schedulingapplication.SchedulingApplication;
import static util.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class DeleteAppointmentScreenController implements Initializable {

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
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;

    Stage stage;
    Parent scene;
    @FXML
    private TableView<Appointment> appointmentDeleteTable;
    /**
     * Initializes the controller class.
     */
    
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
    private void deleteAppointment(ActionEvent event) throws SQLException {
        if (!appointmentDeleteTable.getSelectionModel().isEmpty()){
        conn.createStatement().executeUpdate("DELETE FROM appointment WHERE appointmentId = " + appointmentDeleteTable.getSelectionModel().getSelectedItem().getAppointmentId() + ";");    
            
            
        Appointment.getAllAppointments().remove(Appointment.generateIndexFromAppointmentId(appointmentDeleteTable.getSelectionModel().getSelectedItem().getAppointmentId()));
        appointmentDeleteTable.setItems(Appointment.getAllAppointments());
        
        if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SchedulingApplication.getRb().getString("appointment_deletion_success"));
                alert.setTitle(SchedulingApplication.getRb().getString("appointment_deletion_success"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){}
                } else if (!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr")){
              Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment Has Been Successfully Deleted.");
              alert.setTitle("Appointment Deletion Successful");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK); 
                }
        } else if (Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr")) {
                 Alert alert = new Alert(Alert.AlertType.ERROR, SchedulingApplication.getRb().getString("select_appointment"));
                alert.setTitle(SchedulingApplication.getRb().getString("select_appointment"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){}
            
            
        } else {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select An Appointment To Delete");
              alert.setTitle("Select Appointment");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK); 
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        appointmentDeleteTable.setItems(Appointment.getAllAppointments());
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
    }    

}
