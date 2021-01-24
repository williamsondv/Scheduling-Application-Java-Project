/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapplication;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



/**
 *
 * @author willi
 */
public class SchedulingApplication extends Application {
   
    private static ResourceBundle rb = ResourceBundle.getBundle("schedulingapplication.Properties", Locale.getDefault());

    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        
        
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("view/LogInScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        util.DBConnection.makeConnection();
    }

    public static ResourceBundle getRb() {
        return rb;
    }

    public static void setRb(ResourceBundle rb) {
        SchedulingApplication.rb = rb;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
