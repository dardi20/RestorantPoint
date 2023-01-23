package org.makerminds.internship.java.restaurantpoint.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Product;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restorantpoint.data.DatabaseData;

public class MenuItemManagerController {

	public Menu getMenuFromSelectedMenuName(String selectedMenuName) {
		Menu menu = null;
		try {
			menu = new DatabaseData().getMenuObjectFromMenuName(selectedMenuName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return menu;
	}

	public String[][] getMenuDataAsMatrix(Menu selectedMenu) {
		HashMap<Integer, Product> menuItems = selectedMenu.getMenuItems();
		String[][] menuAsMatrix = new String[menuItems.size()][3];
		int i = 0;
		for (Entry<Integer, Product> entry : menuItems.entrySet()) {
			Product product = entry.getValue();
			menuAsMatrix[i][0] = product.getProductId() + "";
			menuAsMatrix[i][1] = product.getName();
			menuAsMatrix[i][2] = product.getPrice() + "";
			i++;
		}
		return menuAsMatrix;
	}

	public void editMenuData(String string, Product selectedProduct, Menu selectedMenu, Product newProduct) {
		try {
			switch (string) {
			case "Delete":
				new DatabaseData().deleteProduct(selectedProduct);
				break;
			case "Create":
				new DatabaseData().createProduct(selectedProduct, selectedMenu.getMenuName(), newProduct);
				break;
			case "Update":
				new DatabaseData().updateProduct(selectedProduct, selectedMenu, newProduct);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Restaurant getRestaurants(String selectedRestaurantId) {
		Restaurant restaurant = null;
		try {
			restaurant = new DatabaseData().getRestaurantFromRestaurantId(selectedRestaurantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
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

	public String[] getMenuListAsArray(Restaurant selectedRestaurant) {
			String[] menuListAsArray = null;
			try {
				menuListAsArray = new DatabaseData().getMenusFromRestaurants(selectedRestaurant);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return menuListAsArray;
		}

}
