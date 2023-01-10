package org.makerminds.internship.java.restaurantpoint.controller;

import java.sql.SQLException;
import java.util.List;

import org.makerminds.internship.java.restaurantpoint.model.User;
import org.makerminds.internship.java.restorantpoint.data.DatabaseData;
//usingg the singleton pattern

public class LoginController {
//	private UserData userData = new UserData();
//	private List<User> userDataAsList = userData.importUserData();
	private User loggedInUser = null;
	private static final LoginController INSTANCE = new LoginController();

	private LoginController() {

	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void logInUser(String username, String password) {
		List<User> userDataAsList;
		try {
			userDataAsList = new DatabaseData().getUsers();
			for (User user : userDataAsList) {
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					loggedInUser = user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static LoginController getInstance() {
		return INSTANCE;
	}

	public boolean isStringNullOrBlank(String stringText) {
		if(stringText==null || stringText.equals("")) {
			return true;
		}
		return false;
	}

}
