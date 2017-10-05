package net.telintel.inventory.test.file.model;

/**
 *  
 * @author Freddy Lemus
 *
 */
public class ProductType {
	private int id;
	private String name;

	public ProductType() {
		super();
	}

	public ProductType(String name) {
		super();
		this.name = name;
	}

	public ProductType(int id) {
		super();
		this.id = id;
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

}
