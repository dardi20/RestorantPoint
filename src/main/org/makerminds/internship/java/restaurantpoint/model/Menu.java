package org.makerminds.internship.java.restaurantpoint.model;

import java.sql.SQLException;
import java.util.HashMap;

import org.makerminds.internship.java.restorantpoint.data.DatabaseData;

public class Menu {
	private int id;
	private int restaurantId;
	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String menuName;

	private HashMap<Integer, Product> menuItems = new HashMap<Integer, Product>();

	public Menu() {

	}

//	public Menu(String menuName) {
//		try {
//			this.menuName = menuName;
//			this.menuItems = new DatabaseData().getMenu();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public HashMap<Integer, Product> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(HashMap<Integer, Product> menuItems) {
		this.menuItems = menuItems;
	}
}
