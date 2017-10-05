package net.telintel.inventory.test.model.domain;

import java.util.ArrayList;
import java.util.List;

import net.telintel.inventory.test.model.dao.entity.Product;

public class Products {
	private List<Product> products;
	private int totalProduct;

	public List<Product> getProducts() {
		if(products == null) {
			products = new ArrayList<Product>();
		}
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}
	
	
}
