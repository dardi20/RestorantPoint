package org.makerminds.internship.java.restorantpoint.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.makerminds.internship.java.restaurantpoint.model.Drink;
import org.makerminds.internship.java.restaurantpoint.model.Meal;
import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Product;
import org.makerminds.internship.java.restaurantpoint.model.User;
import org.makerminds.internship.java.restaurantpoint.model.UserData;

public class DatabaseData {
	private static final String connectionString = "jdbc:sqlserver://DESKTOP-ERHK37C:1433;username=user;password=user;databaseName=RestorantPoint;encrypt=false";
	private Connection con;

	public DatabaseData() throws SQLException {
		this.con = DriverManager.getConnection(connectionString);
	}

	public List<User> getUsers() {
		List<User> usersList = new ArrayList<User>();
		try {
			Statement stm = this.con.createStatement();
			boolean ret = stm.execute("Select * from Users");
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {
					User user = new User();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						switch (rs.getMetaData().getColumnName(i)) {
						case "username":
							user.setUsername(rs.getString(i));
							break;
						case "password":
							user.setPassword(rs.getString(i));
							break;
						case "user_role":
							user.setUserStatus(UserData.generateUserStatus(rs.getString(i)));
							break;
						}
					}
					usersList.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	public HashMap<Integer, Product> getMenu(String menuName) {
		HashMap<Integer, Product> menu = new HashMap();
		String isSugarFree = null;
		try {
			Statement stm = this.con.createStatement();
			boolean ret = stm.execute("Select * from Menu Where menu_name='" + menuName + "'");
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {

					Product product = new Product();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						switch (rs.getMetaData().getColumnName(i)) {
						case "id":
							product.setProductId(Integer.valueOf(rs.getString(i)));
							break;
						case "product_name":
							product.setName(rs.getString(i));
							break;
						case "product_price":
							product.setPrice(Integer.valueOf(rs.getString(i)));
							break;
						}
					}
					if (Integer.valueOf(rs.getString(1)) < 200) {
						Meal meal = (Meal) product;
						meal.setDescription(rs.getString("product_description"));
						menu.put(meal.getProductId(), meal);
					} else {
						Drink drink = (Drink) product;
						drink.setSugarFree(rs.getBoolean("sugar_free"));
						menu.put(drink.getProductId(), drink);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;

	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public static void main(String[] args) throws SQLException {
		new DatabaseData().getUsers().forEach((e) -> System.out.println(e.toString()));

	}

	public String[] getMenusFromRestaurants(String selectedRestaurant) {
		Statement stm;
		List<String> menuAsList = new ArrayList<>();
		try {
			stm = this.con.createStatement();
			boolean ret = stm.execute("Select menu_name from Menus where restaurant_id =" + selectedRestaurant);
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {
					menuAsList.add(rs.getString("menu_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transformListToArray(menuAsList);
	}

	private String[] transformListToArray(List<String> listOfString) {
		String[] arrayOfString = new String[listOfString.size()];
		int i = 0;
		for (String element : listOfString) {
			arrayOfString[i] = element;
			i++;
		}
		return arrayOfString;
	}

	public List<String> getRestaurantsAsList() {
		Statement stm;
		List<String> restaurantsAsList = new ArrayList<>();
		try {
			stm = this.con.createStatement();
			boolean ret = stm.execute("Select id from Restaurants");
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {
					restaurantsAsList.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurantsAsList;
	}

	public Menu getMenuFromMenuName(String selectedMenuAsString) {
		Statement stm;
		Menu menu = new Menu();
		try {
			stm = this.con.createStatement();
			boolean ret = stm.execute("Select * from Menus Where menu_name='" + selectedMenuAsString + "'");
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {
					menu.setId(Integer.valueOf(rs.getString("menu_id")));
					menu.setMenuName(rs.getString("menu_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;
	}

	public Menu getMenuObjectFromMenuName(String selectedMenuAsString) {
		Statement stm;
		Menu menu = new Menu();
		try {
			stm = this.con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			boolean ret = stm.execute(
					"Select Products.id,Products.product_name, Products.menu_name,Menus.menu_id,Menus.restaurant_id,Products.product_price,Products.product_description, Products.sugar_free from "
							+ "Products Join Menus ON Products.menu_name = Menus.menu_name Where Products.menu_name=('"
							+ selectedMenuAsString + "')");
			if (ret) {
				ResultSet rs = stm.getResultSet();
				while (rs.next()) {
					menu.setId(Integer.valueOf(rs.getString("menu_id")));
					menu.setMenuName(rs.getString("menu_name"));
					menu.setRestaurantId(Integer.valueOf(rs.getString("restaurant_id")));
					Product product = new Product();
					product.setProductId(Integer.valueOf(rs.getString("id")));
					product.setName(rs.getString("product_name"));
					product.setPrice(Double.valueOf(rs.getString("product_price")));
					if (Integer.valueOf(rs.getString(1)) < 200) {
						Meal meal = new Meal(product.getName(), product.getProductId(), product.getPrice());
						meal.setDescription(rs.getString("product_description"));
						menu.getMenuItems().put(meal.getProductId(), meal);
					} else{
						Drink drink = new Drink(product.getName(), product.getProductId(), product.getPrice());
						drink.setSugarFree(rs.getBoolean("sugar_free"));
						menu.getMenuItems().put(drink.getProductId(), drink);
					}
				}
				rs.beforeFirst();
				if(!rs.next()) {
						menu = getMenuFromMenuName(selectedMenuAsString);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menu;
	}

	public void createMenu(String newMenuName, String selectedRestaurant) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Insert Into Menus (menu_name , restaurant_id) Values ('" + newMenuName + "','"
					+ selectedRestaurant + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateMenu(String menuName, String newMenuName) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Update Menus SET menu_name ='" + newMenuName + "' Where menu_name ='" + menuName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMenu(String menuName) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Delete From Menus Where menu_name ='" + menuName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createProduct(Product selectedProduct, String selectedMenuName, Product newProduct) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Insert Into Products (id, product_name, product_price, menu_name) Values ("
					+ newProduct.getProductId() + ",'" + newProduct.getName() + "'," + newProduct.getPrice() + ",'"
					+ selectedMenuName + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteProduct(Product selectedProduct) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Delete From Products Where id =" + selectedProduct.getProductId() + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProduct(Product selectedProduct, Menu selectedMenu, Product newProduct) {
		Statement stm;
		try {
			stm = this.con.createStatement();
			stm.execute("Update Products SET product_name ='" + newProduct.getName() + "'," + "product_price="
					+ newProduct.getPrice() + " Where id =" + selectedProduct.getProductId() + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
