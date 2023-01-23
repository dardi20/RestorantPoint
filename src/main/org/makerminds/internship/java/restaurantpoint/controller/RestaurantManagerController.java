package org.makerminds.internship.java.restaurantpoint.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;

import org.makerminds.internship.java.restaurantpoint.model.Drink;
import org.makerminds.internship.java.restaurantpoint.model.Meal;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restorantpoint.data.DatabaseData;

public class RestaurantManagerController {
	public String[][] getRestaurantTableDataAsMatrix() {
		String[][] restaurantTableData = null;
		try {
			List<Restaurant> restaurants = new DatabaseData().getRestaurants();
			restaurantTableData = new String[restaurants.size()][2];
			int i = 0;
			for (Restaurant restaurant : restaurants) {
				restaurantTableData[i][0] = restaurant.getName();
				restaurantTableData[i][1] = restaurant.getAdress();
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurantTableData;
	}

	public void editRestaurantData(String string, String restaurantName, Restaurant newRestaurant) {
		try {
			switch (string) {
			case "Create":
				new DatabaseData().createRestaurant(restaurantName, newRestaurant);
				break;
			case "Update":
				new DatabaseData().updateRestaurant(restaurantName, newRestaurant);
				break;
			case "Delete":
				new DatabaseData().deleteRestaurant(restaurantName);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
