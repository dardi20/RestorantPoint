package org.makerminds.internship.java.restaurantpoint.controller;

import java.sql.SQLException;
import java.util.List;

import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restorantpoint.data.DatabaseData;

public class MenuManagerController {

	public String[] getMenuListAsArray(Restaurant selectedRestaurant) {
		String[] menuListAsArray = null;
		try {
			menuListAsArray = new DatabaseData().getMenusFromRestaurants(selectedRestaurant);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuListAsArray;
	}

	public void editMenuData(String string, String newMenuName, Menu selectedMenu, Restaurant selectedRestaurant) {
		try {
			switch (string) {
			case "Create":
				new DatabaseData().createMenu(newMenuName, selectedRestaurant.getId());
				break;
			case "Update":
				new DatabaseData().updateMenu(selectedMenu.getMenuName(), newMenuName);
				break;
			case "Delete":
				new DatabaseData().deleteMenu(selectedMenu.getMenuName());
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getRestaurantsAsString() {
		List<String> restaurantsList = null;
		try {
			restaurantsList = new DatabaseData().getRestaurantsAsList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurantsList;
	}

	public Menu getMenuObjectFromMenuName(String selectedMenuAsString) {
		Menu menu = null;
		try {
			menu = new DatabaseData().getMenuFromMenuName(selectedMenuAsString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;
	}

	public Restaurant getRestaurantFromRestaurantName(String selectedRestaurantName) {
		Restaurant restaurant = null;
		try {
			restaurant = new DatabaseData().getRestaurantFromRestaurantName(selectedRestaurantName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

}
