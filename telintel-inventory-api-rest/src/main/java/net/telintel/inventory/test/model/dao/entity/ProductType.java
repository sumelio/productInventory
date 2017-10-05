package net.telintel.inventory.test.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product_type database table.
 * 
 */
@Entity
@Table(name="product_type")
@NamedQuery(name="ProductType.findById", query="SELECT p FROM ProductType p where p.id = :id")
public class ProductType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

 

	public ProductType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
 

 

}