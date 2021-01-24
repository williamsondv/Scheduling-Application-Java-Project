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
import model.Customer;
import model.User;
import schedulingapplication.SchedulingApplication;
import static util.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class UserDeleteScreenController implements Initializable {

    @FXML
    private TableView<User> userDeleteTable;
    @FXML
    private TableColumn<User, Integer> userIdCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, Boolean> activeCol;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    
    Stage stage;
    Parent scene;
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
    private void deleteUser(ActionEvent event) throws SQLException {
        
              if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SchedulingApplication.getRb().getString("delete_user_confirmation"));
                alert.setTitle(SchedulingApplication.getRb().getString("delete_user_confirmation"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    
              conn.createStatement().executeUpdate("DELETE FROM user WHERE userId = " + (userDeleteTable.getSelectionModel().getSelectedItem().getUserId())  + ";");
              User.getAllUsers().remove(userDeleteTable.getSelectionModel().getSelectedItem());
              userDeleteTable.setItems(User.getAllUsers());
              
              Alert alert2 = new Alert(Alert.AlertType.INFORMATION,SchedulingApplication.getRb().getString("user_deleted"));
              alert2.setTitle(SchedulingApplication.getRb().getString("user_deleted"));
              Optional<ButtonType> result2 = alert2.showAndWait();
              if (result2.get() == ButtonType.OK) {}
                    
                }} else {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this user?");
              alert.setTitle("Delete Confirmation");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {
             
         
        
       
        
         conn.createStatement().executeUpdate("DELETE FROM user WHERE userId = " + (userDeleteTable.getSelectionModel().getSelectedItem().getUserId())  + ";");
        User.getAllUsers().remove(userDeleteTable.getSelectionModel().getSelectedItem());
        userDeleteTable.setItems(User.getAllUsers());
              Alert alert2 = new Alert(Alert.AlertType.INFORMATION,"Selected user has been deleted.");
              alert2.setTitle("Delete Information");
              Optional<ButtonType> result2 = alert2.showAndWait();
              if (result2.get() == ButtonType.OK) {}
    }}}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userDeleteTable.setItems(model.User.getAllUsers());
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("activeUser"));
        
    }    
}
