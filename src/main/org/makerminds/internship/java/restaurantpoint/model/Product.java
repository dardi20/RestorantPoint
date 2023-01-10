package org.makerminds.internship.java.restaurantpoint.model;

public class Product {

	private int productId;
	private String name;
	private double price;
	private String productCategory;

	public String getProductCategory() {
		return productCategory;
	}

	public Product() {
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Product(String name, int productId, double price) {

		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public Product(String name, double price) {

		this.name = name;
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
