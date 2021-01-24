/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static util.DBConnection.conn;

/**
 *
 * @author willi
 */
public class User {
   
    private String password;
    private String userName;
    private boolean activeUser;
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static int numberOfUsers = 1;
    private int userId;
    private static ResultSet userResultSet;
    private static List<Integer> userIdArray = new ArrayList();
    private static File UserLogIn = new File("UserLogIn.txt");
    
    
    public static ResultSet getUserResultSet() {
        return userResultSet;
    }

    public static void setUserResultSet(ResultSet userResultSet) {
        User.userResultSet = userResultSet;
    }
    
    
    public User(int uId, String uName, String uPassword, boolean uactive){
    this.userId = uId;
    this.userName = uName;
    this.password = uPassword;
    this.activeUser = uactive;
    
    }

    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ObservableList<User> allUsers) {
        User.allUsers = allUsers;
    }

   

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    public static void setNumberOfUsers(int numberOfUsers) {
        User.numberOfUsers = numberOfUsers;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public static List<Integer> getUserIdArray() {
        return userIdArray;
    }

    public static void setUserIdArray(List<Integer> userIdArray) {
        User.userIdArray = userIdArray;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }
    
    public static ResultSet setUserResultSet() throws SQLException {
    setUserResultSet(userResultSet = conn.createStatement().executeQuery("SELECT userId, userName, password, active FROM user"));
    return userResultSet;
    }
    
    public static void initializeAllUsersFromDatabase() throws SQLException {
    if (User.getAllUsers().isEmpty()){
           
    while (User.getUserResultSet().next()) {
        
         int uId = User.getUserResultSet().getInt("userId");
         String uName = User.getUserResultSet().getString("userName");
         String uPassword = getUserResultSet().getString("password");
         boolean uActive = User.getUserResultSet().getBoolean("active");
         
         User newUser = new User(uId, uName, uPassword, uActive);
         
         User.getAllUsers().add(newUser);
         User.setNumberOfUsers(User.getNumberOfUsers() + 1);
    }
    
    }
    
    }
    
    public static boolean userExists(User userO) {
    
        
        for (int i = 0; i < User.getAllUsers().size(); i++){
        if (userO.getUserId() == (User.getAllUsers().get(i).getUserId())){
            return true;
            } 
            
        }
    return false;
    }
   
    public static int generateUserId(ObservableList<User> ulist) {
    for (int i = 0; i < User.getAllUsers().size(); i++) {
    userIdArray.add(User.getAllUsers().get(i).getUserId());
    
    }
    
    Collections.sort(userIdArray);
    
    for (int i = 0; i < userIdArray.size(); i++) {
        
    if (!userIdArray.get(i).equals(i+1)){
    
    return userIdArray.get(i) - 1;
    
    } else {
    
    
    }
    }return Collections.max(userIdArray) + 1;
    }
    
    public static int getUserIdForLoggedInUser (String uName) {
    for (int i = 0; i < User.getAllUsers().size(); i++) {
    if (User.getAllUsers().get(i).getUserName().contains(uName)) {
    return User.getAllUsers().get(i).getUserId();
    }
    
    } return -1;
    
    
    }
    
    public static ObservableList findAppointmentsByUser (User user) {
    ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
    for (int i = 0; i < Appointment.getAllAppointments().size(); i++){
    if (Appointment.getAllAppointments().get(i).getUserId() == user.getUserId())
        userAppointments.add(Appointment.getAllAppointments().get(i));
    } return userAppointments;
    }
    
    public static void logUserLogIn(String loggedUser, PrintWriter writer) throws IOException {
   
    StringBuilder sb = new StringBuilder();
    if(UserLogIn.createNewFile()){
    sb.append(loggedUser);
    sb.append(" ");
    sb.append(LocalDateTime.now().toString());
    writer.append(sb.toString());
    writer.close();
    
    } else {
   
    sb.append(loggedUser);
    sb.append(" ");
    sb.append(LocalDateTime.now().toString());
    writer.println();
    writer.append(sb.toString());
    writer.close();
    
       }
    }
    
    
    
    }
    
    
