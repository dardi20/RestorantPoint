package org.makerminds.internship.java.restaurantpoint.model;

public class Restaurant {
	private String name;
	private int id;
	private String adress;
	private String city;

	public Restaurant(String name, int id, String address) {
		this.name = name;
		this.adress = address;
		this.id = id;
	}
	public Restaurant(String name,String address) {
		this.name = name;
		this.adress = address;
	}
	public Restaurant() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}

