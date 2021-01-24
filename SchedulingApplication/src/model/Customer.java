/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Customer implements Comparable<Customer> {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static List<Integer> customerIdArray = new ArrayList();

    
    
    private int customerIdNumber;
    private String customerName;
    private String customerPhoneNumber;
    private String customerStreetAddress;
    private String customerCity;
    private String customerCountry;
    private String customerZipCode;
    private boolean activeCustomer;
    private static ResultSet customerResultSet;

    public static ResultSet getCustomerResultSet() {
        return customerResultSet;
    }

    public static void setCustomerResultSet(ResultSet customerResultSet) {
        Customer.customerResultSet = customerResultSet;
    }
    private static int numberOfCustomerObjects = 1;
    
    public Customer (int cID, String cName, String cPhone, String cStreet, String cCity, String cCountry, String cZipCode, boolean cActive) {
        
        this.customerIdNumber = cID;
        this.customerName = cName;
        this.customerPhoneNumber = cPhone;
        this.customerStreetAddress = cStreet;
        this.customerCity = cCity;
        this.customerCountry = cCountry;
        this.customerZipCode = cZipCode;
        this.activeCustomer = cActive;
        
    }

    public int getCustomerIdNumber() {
        return customerIdNumber;
    }

    public void setCustomerIdNumber(int customerIdNumber) {
        this.customerIdNumber = customerIdNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerStreetAddress() {
        return customerStreetAddress;
    }

    public void setCustomerStreetAddress(String customerStreetAddress) {
        this.customerStreetAddress = customerStreetAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public boolean isActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(boolean activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public static int getNumberOfCustomerObjects() {
        return numberOfCustomerObjects;
    }

    public static void setNumberOfCustomerObjects(int numberOfCustomerObjects) {
        Customer.numberOfCustomerObjects = numberOfCustomerObjects;
    }
    

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        Customer.allCustomers = allCustomers;
    }
    
    public static ResultSet setCustomerResultSet () throws SQLException {
    setCustomerResultSet(customerResultSet = conn.createStatement().executeQuery("SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city, country.country, customer.active\n" +
                       "FROM customer INNER JOIN address ON customer.addressId = address.addressId\n" +
                       "INNER JOIN city ON address.cityId = city.cityId\n" +
                       "INNER JOIN country ON city.countryId = country.countryId;"));
    return customerResultSet;
    
    }
    
    public static void initializeAllCustomersFromDatabase() throws SQLException {
    if (Customer.getAllCustomers().isEmpty()){
            
            while (Customer.getCustomerResultSet().next()) {
            int cID = Customer.getCustomerResultSet().getInt("customerId");
            String cName = Customer.getCustomerResultSet().getString("customerName");
            String cPhone = Customer.getCustomerResultSet().getString("phone");
            String cAddress = Customer.getCustomerResultSet().getString("address");
            String cCity = Customer.getCustomerResultSet().getString("city");
            String cCountry = Customer.getCustomerResultSet().getString("country");
            String cZipCode = Customer.getCustomerResultSet().getString("postalCode");
            Boolean cIsActive = Customer.getCustomerResultSet().getBoolean("active");
            
            
            
            Customer newCustomer = new Customer(cID, cName, cPhone, cAddress, cCity, cCountry, cZipCode, cIsActive);
           
            Customer.setNumberOfCustomerObjects(numberOfCustomerObjects + 1);
            Customer.getAllCustomers().add(newCustomer);
            }}}
    
    public static boolean customerExists(Customer customer0) {
    
        
        for (int i = 0; i < Customer.getAllCustomers().size(); i++){
        if (customer0.getCustomerIdNumber() == (Customer.getAllCustomers().get(i).getCustomerIdNumber())){
            return true;
            } else {
            
                    }
       
        }
     return false;
    }
    
    public static int generateCustomerId(ObservableList<Customer> clist) {
    if(!Customer.getAllCustomers().isEmpty()) {
    for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
    customerIdArray.add(Customer.getAllCustomers().get(i).getCustomerIdNumber());
    
    }
    
    Collections.sort(customerIdArray);
    
    for (int i = 0; i < customerIdArray.size(); i++) {
        
    if (!customerIdArray.get(i).equals(i+1)){
    
    return customerIdArray.get(i) - 1;
    
    } else {
    
    
    }
    }return Collections.max(customerIdArray) + 1;
    } else {
    	return 1;
    }
    }
    	
    

    public static List<Integer> getCustomerIdArray() {
        return customerIdArray;
    }

    public static void setCustomerIdArray(List<Integer> customerIdArray) {
        Customer.customerIdArray = customerIdArray;
    }
    
    @Override
    public int compareTo(Customer c) {
    return Integer.valueOf(this.getCustomerIdNumber()).compareTo(c.getCustomerIdNumber());
    }
    
    public static int getCustomerIdFromName(String custName){
    for (int i = 0; i < Customer.getAllCustomers().size(); i ++) {
    if (Customer.getAllCustomers().get(i).getCustomerName().equalsIgnoreCase(custName)) {
    return Customer.getAllCustomers().get(i).getCustomerIdNumber();
    } 
    
    }
    return -1;
    }
    
   }
    
   
   
    
    


