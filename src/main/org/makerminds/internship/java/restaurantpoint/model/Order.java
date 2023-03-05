package org.makerminds.internship.java.restaurantpoint.model;

import java.util.HashMap;

public class Order {
	private int orderId;
	private int tableId;
	//oreder items key corresponds to product id, value corresponds to ordered quantity
	private HashMap<Integer, Integer> orderItems;
}
