package net.telintel.inventory.test.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")


@NamedQueries({
	@NamedQuery(name="Product.total", query="SELECT count(p)  FROM Product p"),
	@NamedQuery(name="Product.ById", query="select p from Product p where p.id = :id"),
	@NamedQuery(name="Product.pages", query="select p from Product p where p.productType.id = :typeId"),
}) 

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String name;

	@Column(name="unit_price")
	private BigDecimal price;

 
	@ManyToOne
	@JoinColumn(name="product_type_id")
	private ProductType productType;

	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price ;
	}

	public void setPrice(BigDecimal unitPrice) {
		this.price = unitPrice;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}