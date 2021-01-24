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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import schedulingapplication.SchedulingApplication;
import view.LoginController;
import static util.DBConnection.conn;
/**
 * FXML Controller class
 *
 * @author willi
 */
public class AddUserController implements Initializable {

    @FXML
    private TextField UserIdField;
    @FXML
    private TextField userNameField;
    @FXML
    private ChoiceBox<String> userActiveDropDown;
    @FXML
    private TextField userPasswordField;
    private static boolean userActiveBool;
    private static int userActiveDigital;
    
    public static boolean isUserActiveBool() {
        return userActiveBool;
    }

    public boolean setUserActiveBool() {
        if (userActiveDropDown.getSelectionModel().getSelectedItem().contains("Yes")){
        userActiveBool = true;
        AddUserController.userActiveDigital = 1;
        return userActiveBool;
        
                } else {
        userActiveBool = false;
        AddUserController.userActiveDigital = 0;
        return userActiveBool;
        }
        
    }
   

    Stage stage;
    Parent scene;
    /**
     * Initializes the controller class.
     */
   

    @FXML
    private void addUserRecord(ActionEvent event) throws SQLException {
        
        setUserActiveBool();
        User newUser = new User(Integer.parseInt(UserIdField.getText()), userNameField.getText(), userPasswordField.getText(), userActiveBool);
        
        if (User.userExists(newUser) == false){
            setUserActiveBool();
            User.getAllUsers().add(newUser);
            
            conn.createStatement().executeUpdate("INSERT INTO user (userId,userName,password,active,createDate,createdBy,lastUpdate,lastUpdateBy) "
                + "VALUES ("+ UserIdField.getText() + ",'" + userNameField.getText() + "','" + userPasswordField.getText() + "'," + userActiveDigital + "," + "CURRENT_TIMESTAMP" + ",'" + LoginController.getLoggedInUser() + "'," + "CURRENT_TIMESTAMP" + ",'" + LoginController.getLoggedInUser() + "');" );
            
            User.setNumberOfUsers(User.getNumberOfUsers()+ 1);
             UserIdField.setText(Integer.toString(User.generateUserId(User.getAllUsers())));
             User.getUserIdArray().clear();
             
             if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, SchedulingApplication.getRb().getString("add_user_success"));
                alert.setTitle(SchedulingApplication.getRb().getString("add_user_success"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                } else if (!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr")) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"The User has been successfully added.");
              alert.setTitle("User Has Been Added");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {}
             
        } else {
                if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, SchedulingApplication.getRb().getString("existing_user"));
                alert.setTitle(SchedulingApplication.getRb().getString("existing_user"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                    
                } else {
            
              Alert alert = new Alert(Alert.AlertType.INFORMATION,"This User has already been added. Creating new userID.");
              alert.setTitle("Existing User");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {
              UserIdField.setText(Integer.toString(User.getAllUsers().get(User.getAllUsers().size() - 1).getUserId() + 1));
              
              }
        }}}
        
        
        
        
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
    
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> choiceBoxActiveUser = FXCollections.observableArrayList("Yes","No");
        userActiveDropDown.setItems(choiceBoxActiveUser);
        userActiveDropDown.getSelectionModel().selectFirst();
        UserIdField.setText(Integer.toString(User.generateUserId(User.getAllUsers())));
        User.getUserIdArray().clear();
        UserIdField.setEditable(false);
        
        User.getUserResultSet();
        try {
            User.initializeAllUsersFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    
}
