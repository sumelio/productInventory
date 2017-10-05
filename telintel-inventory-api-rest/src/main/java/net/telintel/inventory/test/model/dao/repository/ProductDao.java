package net.telintel.inventory.test.model.dao.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import net.telintel.inventory.test.model.dao.entity.Product;
import net.telintel.inventory.test.model.dao.entity.ProductType;

public interface ProductDao {
 

	
	/**
	 * Create a product 
	 * 
	 * @param prod
	 */
	public void saveProduct(Product prod);

	
	/**
	 * Get all categories product
	 * 
	 * @return
	 */
	public List<ProductType> getProductTYpes();

 
 

	
	/**
	 *  Get product by category
	 * 
	 * @param typeId
	 * @return
	 */
	public int getProductsTotalByTypeId(int typeId);

	/**
	 * Return total stock from Product table
	 * 
	 * @return
	 */
	public int getProductsTotalAll();

	/**
	 * Get product registers by page
	 * 
	 * @param typeId
	 * @param page
	 * @param pageSIze
	 * @param count
	 * @return
	 */
	public List<Product> getProductsByPage(int typeId, int page, int pageSIze, int count);

	/**
	 * Update a product
	 * 
	 * @param product
	 */
	public void updateProduct(Product product);

	
	/**
	 * Delete a product
	 * 
	 * @param id
	 */
	@Transactional
	public void removeProduct(int id);


	public ProductType getProductTypeById(int type);

}