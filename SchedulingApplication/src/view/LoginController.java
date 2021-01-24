/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import schedulingapplication.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import util.DBConnection;
import static util.DBConnection.makeConnection;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class LoginController implements Initializable {
    
    public Stage stage;
    public Parent scene;
   

    @FXML
    private Label label;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button loginButton;
    private static String loggedInUser;
    private boolean passwordValid = false; 
    

        public static void setLoggedInUser(String lIUser) {
        LoginController.loggedInUser = lIUser;
    }
        
        public static String getLoggedInUser() {
        return loggedInUser;
    }
    @FXML
    private Label userNameLogInTxt;
    @FXML
    private Label passwordLogInTxt;
    @FXML
    private Label pleaseLogInTxt;
         @FXML
         void loginButton(ActionEvent event) throws IOException, SQLException, Exception {
         //Make Connection to Database   
         makeConnection();
         
         
         
         
        setLoggedInUser(usernameTxt.getText());
        Statement stmt = util.DBConnection.conn.createStatement();
        String selectUserName = "SELECT * FROM user";

        //Execute Statement and Create ResultSet Object
        ResultSet userNameColumn = stmt.executeQuery(selectUserName);
        
        FileWriter userLogInFw = new FileWriter("UserLogIn.txt", true);
        PrintWriter writer = new PrintWriter(userLogInFw);
        
        User.logUserLogIn(loggedInUser, writer);
        
        while (userNameColumn.next() == true) {
            
            if (userNameColumn.getString("password").equals(passwordTxt.getText()) && userNameColumn.getString("userName").equals(usernameTxt.getText())) {
                
                LoginController.setLoggedInUser(usernameTxt.getText());
                
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                passwordValid = true;
                break;
            }}
        
         if (passwordTxt.getText().isEmpty() || usernameTxt.getText().isEmpty()) {
                if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert alert = new Alert(Alert.AlertType.ERROR, SchedulingApplication.getRb().getString("login_empty"));
                alert.setTitle(SchedulingApplication.getRb().getString("login_empty"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                }
                } else if (!Locale.getDefault().getLanguage().equals("ru") || !Locale.getDefault().getLanguage().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password and Username must be entered. Please try again.");
                alert.setTitle("Password and Username must be entered. Please try again.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                }}
             
            if (!passwordValid == true){
            if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr")){
            Alert alert = new Alert(Alert.AlertType.ERROR, SchedulingApplication.getRb().getString("password_mismatch"));
            alert.setTitle(SchedulingApplication.getRb().getString("password_mismatch"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {}
            
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password and Username do not match. Please try again.");
                alert.setTitle("Password and Username do not match. Please try again.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                }}}}
        
        

                
                

        
         
            
        

    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("fr")){
       pleaseLogInTxt.setText(SchedulingApplication.getRb().getString("please_log_in"));
       passwordLogInTxt.setText(SchedulingApplication.getRb().getString("password"));
       userNameLogInTxt.setText(SchedulingApplication.getRb().getString("user_name_text"));
       loginButton.setText(SchedulingApplication.getRb().getString("log_in_button"));
       
       }
    }
}
