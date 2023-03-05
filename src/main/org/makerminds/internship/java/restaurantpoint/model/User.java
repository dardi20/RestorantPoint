package org.makerminds.internship.java.restaurantpoint.model;

public class User {
	
	 @Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", userRole=" + userRole + "]";
	}
	 private String username;
	 private String password;
	 private UserRole userRole;
	 //this variable is used only for users that are waiters.
	 private String restaurant;
	 
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
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
