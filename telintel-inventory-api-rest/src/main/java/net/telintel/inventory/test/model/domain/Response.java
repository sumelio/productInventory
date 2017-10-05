package net.telintel.inventory.test.model.domain;

import net.telintel.inventory.test.model.dao.entity.Product;

public class Response {
	private int code;
	private String message;
	private int totalStock;
	private Product product;
	
	 

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Response(int code, String message, Product product) {
		this.code = code;
		this.message = message;
		this.product = product;
	}
	
	public Response(int code, String message, Product product,  int total) {
		this.code = code;
		this.message = message;
		this.product = product;
		this.totalStock = total;
	}
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(int totalStock) {
		this.totalStock = totalStock;
	}

 
	
	
}
