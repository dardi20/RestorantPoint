package org.makerminds.internship.java.restaurantpoint.model;

public class User {
	
	 @Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", userRole=" + userRole + "]";
	}
	private String username;
	 private String password;
	 private UserRole userRole;
	 
	public String getUsername() {
		return username;
	}
	public UserRole getUserStatus() {
		return userRole;
	}
	public void setUserStatus(UserRole userStatus) {
		this.userRole = userStatus;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
}
