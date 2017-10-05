package net.telintel.inventory.test.model.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import net.telintel.inventory.test.model.dao.entity.Product;
import net.telintel.inventory.test.model.dao.entity.ProductType;
import net.telintel.inventory.test.model.dao.repository.ProductDao;
import net.telintel.inventory.test.model.domain.Products;
import net.telintel.inventory.test.model.domain.Response;


/**
 * This class has the logic of business and implement the logic necessary in
 * order to access the database
 * 
 * @author Freddy Lemus
 *
 */
public class ModelManagerBusinessBean {
 

    @Autowired
    private ProductDao productDao;
    
	/**
	 * 
	 * @return
	 */
	public List<ProductType> getProductTypes() {
		// TODO Auto-generated method stub
		return productDao.getProductTYpes();
	}

	/**
	 * 
	 * @return
	 */
//	public Products getProductsByTypeIdAndPage(int id, int page, int pageSize) {
//		// TODO Auto-generated method stub
//		Products products = new Products();
//		List<Product> listProducts = new ArrayList<Product>();
//		int total = modelService.getProductsTotalByTypeId(id);
//		listProducts = modelService.getProductsByPage(id, page, pageSize, total);
//		products.getProducts().addAll(listProducts);
//		products.setTotalProduct(total);
//		return products;
//	}
//	
	public Products getProductsByTypeIdAndPage(int id, int page, int pageSize) {
		// TODO Auto-generated method stub
		Products products = new Products();
		List<Product> listProducts = new ArrayList<Product>();
		int total = productDao.getProductsTotalByTypeId(id);
		listProducts = productDao.getProductsByPage(id, page, pageSize, total);
		products.getProducts().addAll(listProducts);
		products.setTotalProduct(total);
		return products;
	}
	

	/**
	 * Create a new product in data base
	 *  
	 * @param name Product name
	 * @param description Product description 
	 * @param price 
	 * @param type
	 * @return
	 */
	public Response addProductInventory(String name, String description, double price, int type) {
		Response response = null;
		List<Product> listProdcuts = new ArrayList<Product>();
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(new BigDecimal(price));
		product.setProductType(productDao.getProductTypeById(type));
		listProdcuts.add(product);

		productDao.saveProduct(product);
		int total = productDao.getProductsTotalAll();
		response = new Response(HttpStatus.OK.value(), "Success", product, total);

		return response;
	}

	/**
	 *  Delete the product with id
	 *  
	 * @param id product identification
	 * 
	 * @return Response
	 */
	public Response removeProductInventory(int id) {

		Response response = null;
		productDao.removeProduct(id);

		int total = productDao.getProductsTotalAll();
		response = new Response(HttpStatus.OK.value(), "Success", null, total);

		return response;
	}
    
	
	
	/**
	 *  Update the product information.
	 * @param id product identification 
	 * @param name Product name
	 * @param description Product description
	 * @param price Product unit price
	 * @param type Product type or category
	 * 
	 * @return Response Object
	 */
	public Response updateProductInventory(int id, String name, String description, double price, int type) {
		Response response = null;
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(new BigDecimal(price));
		product.setProductType(productDao.getProductTypeById(type));

		productDao.updateProduct(product);

		int total = productDao.getProductsTotalAll();
		response = new Response(HttpStatus.OK.value(), "Success", product, total);

		return response;
	}

}
