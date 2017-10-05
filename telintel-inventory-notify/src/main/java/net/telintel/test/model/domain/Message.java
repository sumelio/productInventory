package net.telintel.test.model.domain;

import java.util.List;

public class Message {

	private String message;
	private int id;
	private int count;
	private String category;

	private List<Product> product;

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
	
	

	public Message(String message, int id, int count, String category, List<Product> product) {
		super();
		this.message = message;
		this.id = id;
		this.count = count;
		this.category = category;
		this.product = product;
	}

	public Message() {

	}

	public Message(int id, String message, List<Product> product) {
		this.id = id;
		this.message = message;
		this.product = product;
	}

	public Message(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
