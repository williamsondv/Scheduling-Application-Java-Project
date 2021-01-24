package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.Assert;

class UserTest {
	
	private static ObservableList<User> allUsers = FXCollections.observableArrayList();

	public ObservableList<User> getAllUsers(){
		return allUsers;
	}
	User user1 = new User(1, "Mohindar56", "Nomenga34", true);
	User user2 = new User(2, "Nelson56", "Formian34", true);
	User user3 = new User(3, "Anson56", "Tuatha34", true);
	User user4 = new User(4, "Hawke56", "NomNom34", true);
	
	
	@Test
	void testUserExists() {
		User.getAllUsers().add(user1);
		User.getAllUsers().add(user2);
		User.getAllUsers().add(user3);
		
		
		assertEquals(true,User.userExists(user1));
		assertEquals(false,User.userExists(user4));
		
		
		
	}

	

}
