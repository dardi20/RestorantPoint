package org.makerminds.internship.java.restaurantpoint.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserData {

	private final String path = "/user_data.txt";

	public List<User> importUserData() {
		List<User> userList = new ArrayList<User>();
		try {
			List<String> userListAsString = Files.readAllLines(Paths.get(getClass().getResource(path).toURI()));
			for (String userAsString : userListAsString) {
				User user = new User();
				String[] userPropertiesAsString = userAsString.split(",");
				user.setUsername(userPropertiesAsString[0]);
				user.setPassword(userPropertiesAsString[1]);
				user.setUserStatus(generateUserStatus(userPropertiesAsString[2]));
				userList.add(user);
			}

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public static UserRole generateUserStatus(String string) {
		for (UserRole userStatus : UserRole.values()) {
			if (string.toUpperCase().equals(userStatus.toString())) {
				return userStatus;
			}
		}
		return null;
	}

}
