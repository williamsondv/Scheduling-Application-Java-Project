/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import static java.time.ZoneOffset.UTC;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.zoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static model.User.setUserResultSet;
import static util.DBConnection.conn;
import view.AddAppointmentScreenController;

/**
 *
 * @author willi
 */
public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String appointmentType;
    private String appointmentUrl;
    private String appointmentStartTime;
    private String appointmentEndTime;
    private String appointmentTitle;
    private static ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
   

    public static ObservableList<String> getAppointmentTimeComboBoxList1() {
        return appointmentTimeComboBoxList1;
    }

    public static void setAppointmentTimeComboBoxList1(ObservableList<String> appointmentTimeComboBoxList1) {
        Appointment.appointmentTimeComboBoxList1 = appointmentTimeComboBoxList1;
    }

    public static ObservableList<String> getAppointmentEndTimeComboBoxList1() {
        return appointmentEndTimeComboBoxList1;
    }

    public static void setAppointmentEndTimeComboBoxList1(ObservableList<String> appointmentEndTimeComboBoxList1) {
        Appointment.appointmentEndTimeComboBoxList1 = appointmentEndTimeComboBoxList1;
    }
    private String appointmentLocation;
    private String appointmentContact;
    private String appointmentDescription;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static List<Integer> appointmentIdArray = new ArrayList();
    private static ResultSet appointmentResultSet;
    private static ObservableList<String> appointmentTimeComboBoxList = FXCollections.observableArrayList("00:00","00:15","00:30","00:45","01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45","03:00","03:15","03:30","03:45","04:00","04:15","04:30","04:45",
                "05:00","05:15","05:30","05:45","06:00","06:15","06:30","06:45","07:00","07:15","07:30","07:45","08:00","08:15","08:30","08:45","09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45"
                ,"13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45",
                "21:00","21:15","21:30","21:45","22:00","22:15","22:30","22:45","23:00","23:15","23:30","23:45");
    private static ObservableList<String> appointmentLengthList = FXCollections.observableArrayList("15" , "30" , "45" , "60");
    private static ObservableList<String> appointmentTimeComboBoxList1 = FXCollections.observableArrayList("09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45"
                ,"13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00");
    private static ObservableList<String> appointmentEndTimeComboBoxList1 = FXCollections.observableArrayList("09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45"
                ,"13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00");
    private static ObservableList<Integer> appointmentsByMonthComboBoxList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
    
    
    public static ObservableList<Integer> getAppointmentsByMonthComboBoxList() {
        return appointmentsByMonthComboBoxList;
    }

    public static void setAppointmentsByMonthComboBoxList(ObservableList<Integer> appointmentsByMonthComboBoxList) {
        Appointment.appointmentsByMonthComboBoxList = appointmentsByMonthComboBoxList;
    }
    
    
    public static ObservableList<String> getAppointmentTimeComboBoxList() {
        return appointmentTimeComboBoxList;
    }

    public void setAppointmentTimeComboBoxList(ObservableList<String> appointmentTimeComboBoxList) {
        this.appointmentTimeComboBoxList = appointmentTimeComboBoxList;
    }

    public static ObservableList<String> getAppointmentLengthList() {
        return appointmentLengthList;
    }

    public void setAppointmentLengthList(ObservableList<String> appointmentLengthList) {
        this.appointmentLengthList = appointmentLengthList;
    }
    
    public Appointment(int aId, int cId, int uId, String aTitle, String aDescription, String aLocation, String aContact, String aType, String aUrl, String aStartTime, String aEndTime) {
    this.appointmentId = aId;
    this.customerId = cId;
    this.userId = uId;
    this.appointmentTitle = aTitle;
    this.appointmentDescription = aDescription;
    this.appointmentLocation = aLocation;
    this.appointmentContact = aContact;
    this.appointmentType = aType;
    this.appointmentUrl = aUrl;
    this.appointmentStartTime = aStartTime;
    this.appointmentEndTime = aEndTime;
    
    }

    public static List<Integer> getAppointmentIdArray() {
        return appointmentIdArray;
    }

    public static void setAppointmentIdArray(List<Integer> appointmentIdArray) {
        Appointment.appointmentIdArray = appointmentIdArray;
    }

    public static ResultSet getAppointmentResultSet() {
        return appointmentResultSet;
    }

    public static void setAppointmentResultSet(ResultSet appointmentResultSet) {
        Appointment.appointmentResultSet = appointmentResultSet;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        Appointment.allAppointments = allAppointments;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentUrl() {
        return appointmentUrl;
    }

    public void setAppointmentUrl(String appointmentUrl) {
        this.appointmentUrl = appointmentUrl;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentContact() {
        return appointmentContact;
    }

    public void setAppointmentContact(String appointmentContact) {
        this.appointmentContact = appointmentContact;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
    
    public static int generateAppointmentId(ObservableList<Appointment> alist) {
    if(!Appointment.getAllAppointments().isEmpty()) {
    for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
    appointmentIdArray.add(Appointment.getAllAppointments().get(i).getAppointmentId());
    
    }
    
    Collections.sort(appointmentIdArray);
    
    for (int i = 0; i < appointmentIdArray.size(); i++) {
        
    if (!appointmentIdArray.get(i).equals(i+1)){
    
    return appointmentIdArray.get(i) - 1;
    
    } else {
    
    
    }
    }return Collections.max(appointmentIdArray) + 1;
    } else {
    	return 1;}
    }
    
    public static ResultSet setAppointmentResultSet() throws SQLException {
    setAppointmentResultSet(appointmentResultSet = conn.createStatement().executeQuery("SELECT appointmentId, customerId, userId, title, description, location, contact, type, url, start, end FROM appointment"));
    return appointmentResultSet;
    }
    
    public static void initializeAllAppointmentsFromDatabase() throws SQLException {
    if (Appointment.getAllAppointments().isEmpty()){
        
          
            
            while (Appointment.getAppointmentResultSet().next()) {
            int aId = Appointment.getAppointmentResultSet().getInt("appointmentId");
            int cId = Appointment.getAppointmentResultSet().getInt("customerId");
            int uId = Appointment.getAppointmentResultSet().getInt("userId");
            String aTitle = Appointment.getAppointmentResultSet().getString("title");
            String aDescription = Appointment.getAppointmentResultSet().getString("description");
            String aLocation = Appointment.getAppointmentResultSet().getString("location");
            String aContact = Appointment.getAppointmentResultSet().getString("contact");
            String aType = Appointment.getAppointmentResultSet().getString("type");
            String aUrl = Appointment.getAppointmentResultSet().getString("url");
            String aStart = Appointment.getAppointmentResultSet().getString("start").substring(0,19);
            String aEnd = Appointment.getAppointmentResultSet().getString("end").substring(0, 19);
           
           
           LocalDateTime startldt = Appointment.getAppointmentResultSet().getTimestamp("start").toLocalDateTime();
           ZonedDateTime startZtdUtc = ZonedDateTime.of(startldt, UTC);
           ZonedDateTime startZtdLocal = startZtdUtc.withZoneSameInstant(localZoneId);
           String startZtdLocalString = startZtdLocal.format(formatter);
           
           LocalDateTime endldt = Appointment.getAppointmentResultSet().getTimestamp("end").toLocalDateTime();
           ZonedDateTime endZtdUtc = ZonedDateTime.of(endldt, UTC);
           ZonedDateTime endZtdLocal = endZtdUtc.withZoneSameInstant(localZoneId);
           String endZtdLocalString = endZtdLocal.format(formatter);
           
           
           
           
           
           Appointment newAppointment = new Appointment(aId, cId, uId, aTitle, aDescription, aLocation, aContact, aType, aUrl, startZtdLocalString, endZtdLocalString);
           
            
           Appointment.getAllAppointments().add(newAppointment);
           }}}
    
    public static int generateTimePickerIndex(String time){
    for (int i = 0; i < Appointment.getAppointmentTimeComboBoxList1().size(); i ++) {
    if (time.contains(Appointment.getAppointmentTimeComboBoxList1().get(i))) {
    return i;
    }
    } return -1;
    }
    
    public static String generateAppointmentEndTime(int timeIndex, String appLength) {
    int advanceIndex = 0;
    for (int i = 0; i < Appointment.getAppointmentLengthList().size(); i++) {
    if (appLength.contentEquals(Appointment.getAppointmentLengthList().get(i))) {
    advanceIndex = i + 1;
    return Appointment.getAppointmentEndTimeComboBoxList1().get(timeIndex + advanceIndex);
    }
    
    }
    
    return null;
    }

    public static int generateIndexFromAppointmentId(int appId) {
    for (int i = 0; i < Appointment.getAllAppointments().size(); i ++) {
    if (Appointment.getAllAppointments().get(i).getAppointmentId() == appId){
    return i;
    }
    
    }
    return -1;
    
    }
    
    public static boolean checkForAppointmentOverlap (LocalDateTime startTime, LocalDateTime endTime){
        LocalDate endTimeDate = endTime.toLocalDate();
        LocalDate startTimeDate = startTime.toLocalDate();
        LocalTime endTimeTime = endTime.toLocalTime();
        LocalTime startTimeTime = startTime.toLocalTime();
    
     for (int i=0; i < Appointment.getAllAppointments().size(); i++){
     if (startTimeDate.equals(LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalDate())){
         if (startTimeTime.isBefore(LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentEndTime(), formatter).toLocalTime()) && LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalTime().isBefore(endTimeTime)){
         return true;
         }
         }
         }return false;
         }
    
    public static ObservableList displayWeeklyAppointments (ObservableList allApps, LocalDateTime ldtNow) {
               ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
               for (int i = 0; i < Appointment.getAllAppointments().size(); i ++) {
                   if (LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalDate().isAfter(ldtNow.toLocalDate().minusDays(1)) && LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalDate().isBefore(ldtNow.toLocalDate().plusDays(8))){
                       
                   weeklyAppointments.add(Appointment.getAllAppointments().get(i));
                   }
               } return weeklyAppointments;
    }
    
    public static ObservableList displayMonthlyAppointments (ObservableList allApps, LocalDateTime ldtNow) {
    ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    int maxNumberOfDaysInMonth = ldtNow.getMonth().maxLength();
    int actualNumberOfDaysInMonth = maxNumberOfDaysInMonth - ldtNow.toLocalDate().getDayOfMonth();
    for (int i = 0; i < Appointment.getAllAppointments().size(); i ++){
    if (LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalDate().isAfter(ldtNow.toLocalDate().minusDays(1)) && LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).toLocalDate().isBefore(ldtNow.toLocalDate().plusDays(actualNumberOfDaysInMonth))){
                       
                   monthlyAppointments.add(Appointment.getAllAppointments().get(i));
                   }
    
    } return monthlyAppointments;
    }
    
    public static boolean appointmentWithinFifteenMinutesOfLogIn (LocalDateTime ldtNow) {
    LocalTime localT = ldtNow.toLocalTime();
    for (int i = 0; i < Appointment.getAllAppointments().size(); i ++) {
        if (LocalDate.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).isEqual(ldtNow.toLocalDate())) {
            if (LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).isAfter(ldtNow) && LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).isBefore(ldtNow.plusMinutes(16))){
            return true;
            }
        
        }
    
    }return false;
    
    }
    
    public static int generateNumberOfAppointmentsByMonth(int month){
        int numberOfAppointments = 0;
    for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
    if (LocalDateTime.parse(Appointment.getAllAppointments().get(i).getAppointmentStartTime(), formatter).getMonthValue() == month){
    numberOfAppointments++;
    }
    } return numberOfAppointments;
    }
    
    public static int generateNumberOfAppointmentsByLocation(String location) {
    int numberOfAppointments= 0;
    for (int i = 0; i < Appointment.getAllAppointments().size(); i++){
    if(Appointment.getAllAppointments().get(i).getAppointmentLocation().contains(location))
        numberOfAppointments++;
    } return numberOfAppointments;
    }
    
    
}    
    

