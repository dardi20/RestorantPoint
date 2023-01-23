package org.makerminds.internship.java.restaurantpoint.model;

import java.util.ArrayList;

public class Table {
private int table_id;
private int seats;
private int restaurant_id;
private ArrayList<Product>orderedProducts;
private int waiterId;
public int getTable_id() {
	return table_id;
}

public Table(int seats, int restaurant_id, ArrayList<Product> orderedProducts, int waiterId) {
	this.seats = seats;
	this.restaurant_id = restaurant_id;
	this.orderedProducts = orderedProducts;
	this.waiterId = waiterId;
}

public Table() {
}

public Table(int table_id, int seats) {
	this.table_id = table_id;
	this.seats = seats;
}

public Table(int table_id, int seats, int restaurant_id, ArrayList<Product> orderedProducts, int waiterId) {
	this.table_id = table_id;
	this.seats = seats;
	this.restaurant_id = restaurant_id;
	this.orderedProducts = orderedProducts;
	this.waiterId = waiterId;
}

public void setTable_id(int table_id) {
	this.table_id = table_id;
}
public int getSeats() {
	return seats;
}
public void setSeats(int seats) {
	this.seats = seats;
}
public int getRestaurant_id() {
	return restaurant_id;
}
public void setRestaurant_id(int restaurant_id) {
	this.restaurant_id = restaurant_id;
}
public ArrayList<Product> getOrderedProducts() {
	return orderedProducts;
}
public void setOrderedProducts(ArrayList<Product> orderedProducts) {
	this.orderedProducts = orderedProducts;
}
public int getWaiterId() {
	return waiterId;
}
public void setWaiterId(int waiterId) {
	this.waiterId = waiterId;
}

}
