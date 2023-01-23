package org.makerminds.internship.java.restaurantpoint.controller;

import java.sql.SQLException;
import java.util.List;

import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restaurantpoint.model.Table;
import org.makerminds.internship.java.restorantpoint.data.DatabaseData;

public class TableManagerController {

	public Restaurant getRestaurant(String selectedRestaurantId) {
		Restaurant restaurant = null;
		try {
			restaurant = new DatabaseData().getRestaurantFromRestaurantId(selectedRestaurantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

	public String[][] getTableDataAsMatrix(Restaurant selectedRestaurant) {
		String[][] tableDataAsMatrix = null;
		try {
			List<Table> tableDataAsList = new DatabaseData()
					.getTableDataAsListFromRestaurantId(selectedRestaurant.getId());
			tableDataAsMatrix = new String[tableDataAsList.size()][2];
			int i = 0;
			for (Table table : tableDataAsList) {
				tableDataAsMatrix[i][0] = table.getTable_id() + "";
				tableDataAsMatrix[i][1] = table.getSeats() + "";
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableDataAsMatrix;
	}

	public void editTableData(String string, String tableId, Table newTable, String restuarantId) {
		try {
			switch (string) {
			case "Create":
				new DatabaseData().createTable(tableId, newTable, restuarantId);
				break;
			case "Update":
				new DatabaseData().updateTable(tableId, newTable);
				break;
			case "Delete":
				new DatabaseData().deleteTable(tableId);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
