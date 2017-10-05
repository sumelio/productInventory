package net.telintel.inventory.test.file.model;

 
/**
 * 
 * @author Freddy Lemus
 *
 */
public class Product {
	
 
	
	private int id;
	private String name;
	private String description;
	private Double price;
	private ProductType productType;
	
	
	public Product() {
 
	}
	public Product(String name, String description, Double price, ProductType productType) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.productType = productType;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

 

	 

}
