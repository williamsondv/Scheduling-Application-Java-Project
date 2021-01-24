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
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAttribute;
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
import model.User;
import schedulingapplication.SchedulingApplication;
import static util.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class UserUpdateScreenController implements Initializable {

    @FXML
    private ChoiceBox<String> activeUserDropdown;
    @FXML
    private Button updateUserRecord;
    @FXML
    private Button mainScreen;
    @FXML
    private Button closeProgram;
    @FXML
    private TableView<User> userSelectionTable;
    @FXML
    private TableColumn<User, Integer> userIdCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> userPasswordCol;
    @FXML
    private TableColumn<User, Boolean> activeUserCol;
    
    Stage stage;
    Parent scene;
    private int activeDigital;
    
    @FXML
    private TextField userIdTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField userPasswordTxt;
    /**
     * Initializes the controller class.
     */
    

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

    @FXML
    private void updateUser(ActionEvent event) throws SQLException {
        
        
        if (!userSelectionTable.getSelectionModel().isEmpty()){
        int userIndex = User.getAllUsers().indexOf(userSelectionTable.getSelectionModel().getSelectedItem());
        int userId = Integer.parseInt(userIdTxt.getText());
        String userName = userNameTxt.getText();
        String userPassword = userPasswordTxt.getText();
        boolean active;
        if(activeUserDropdown.getSelectionModel().getSelectedItem().contains("Yes")){
        active = true; 
        activeDigital = 1;
        } else {
        active = false;
        activeDigital = 0;
        }
        
        
        
        
        User newUser = new User(userId, userName, userPassword, active);
        
        User.getAllUsers().set(userIndex, newUser);
        userSelectionTable.setItems(User.getAllUsers());
        
        conn.createStatement().executeUpdate("UPDATE user SET userName = '" + newUser.getUserName() + "',password ='" + newUser.getPassword() + "', active = " + activeDigital + ", lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = '" + LoginController.getLoggedInUser() + "' WHERE userId = " + newUser.getUserId() + ";");
              
              if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SchedulingApplication.getRb().getString("user_update_confirmation"));
                alert.setTitle(SchedulingApplication.getRb().getString("user_update_confirmation"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){}
                } else if (!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr")){
              Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"User record has been successfully updated.");
              alert1.setTitle("Successful Update");
              Optional<ButtonType> result = alert1.showAndWait();
              if (result.get() == ButtonType.OK);{}
                }
                
             } else if (Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr")) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SchedulingApplication.getRb().getString("select_user"));
                alert.setTitle(SchedulingApplication.getRb().getString("select_user"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){}
             } else {
              Alert alert = new Alert(Alert.AlertType.WARNING,"Please select a user record to update.");
              alert.setTitle("Select Record");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK);{}
                  }   
    }
    
    
    private void  populateTextFields() throws InterruptedException  {
        try {
        Thread.sleep(20);
        userNameTxt.setText(userSelectionTable.getSelectionModel().getSelectedItem().getUserName());
        userPasswordTxt.setText(userSelectionTable.getSelectionModel().getSelectedItem().getPassword());
        userIdTxt.setText(Integer.toString(userSelectionTable.getSelectionModel().getSelectedItem().getUserId()));
        if (userSelectionTable.getSelectionModel().getSelectedItem().isActiveUser()) {
        activeUserDropdown.getSelectionModel().select("Yes");
        } else {
        activeUserDropdown.getSelectionModel().select("No");
        }
        } catch (NullPointerException e) {
        
        
        }
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> choiceBoxActiveUser = FXCollections.observableArrayList("Yes","No");
        activeUserDropdown.setItems(choiceBoxActiveUser);
        activeUserDropdown.getSelectionModel().selectFirst();
        
        userIdTxt.setEditable(false);
       
        userSelectionTable.setItems(model.User.getAllUsers());
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        activeUserCol.setCellValueFactory(new PropertyValueFactory<>("activeUser"));
        
        //A Lambda expression is a function which can be created without belonging to any class. It is used here to reduce code.
        userSelectionTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                populateTextFields();
            } catch (InterruptedException ex) {
                Logger.getLogger(UserUpdateScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
    
    }
    }    

