package net.telintel.inventory.test.controller;

import java.text.ParseException; 
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.telintel.inventory.test.model.business.ModelManagerBusinessBean;
import net.telintel.inventory.test.model.dao.entity.ProductType;
import net.telintel.inventory.test.model.domain.*;

 

/**
 * POJO perform like a Controller Bean in Spring and its methods return
 * ResponseEntity objects. This class has the methods of the Restful service
 * 
 * @author falemus
 *
 */
@RestController
public class ServiceRestController {

	static Logger log = Logger.getLogger(ServiceRestController.class.getName());

	@Autowired
	ModelManagerBusinessBean modelMagenerBusiness;

	 
	
	
	
	/**
	 * Return one list of all product's types
	 * 
	 * @return Object that contain the list of the products
	 * @throws ParseException
	 */
	@RequestMapping(value = "/product/types", method = RequestMethod.GET, headers = "Accept=application/json", produces="application/json")
	public ResponseEntity<List<ProductType>> getProductTypes() throws ParseException {

		ResponseEntity<List<ProductType>> responseEntity = new ResponseEntity<List<ProductType>>(
				modelMagenerBusiness.getProductTypes(), HttpStatus.OK);

		return responseEntity;

	}
	
	
	/**
	 *  Return a list of product inventory
	 * @param typeId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/products", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Products> getProducts(@RequestParam(value = "typeId") int typeId, @RequestParam(value = "page") int page,@RequestParam(value = "pageSize") int pageSize) throws ParseException {

		ResponseEntity<Products> responseEntity = new ResponseEntity<Products>(
				modelMagenerBusiness.getProductsByTypeIdAndPage(typeId, page, pageSize), HttpStatus.OK);

		return responseEntity;

	}	
	
	
	/**
	 * Create a product in the dataBase 
	 * 
	 * @param name
	 * @param description
	 * @param price
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/product/inventory/name/{name}/description/{description}/price/{price}/type/{type}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Response> postProductInventory(@PathVariable String name, 
			                                            @PathVariable String description,
			                                            @PathVariable double price,
			                                            @PathVariable int type) throws ParseException {

		Response response = modelMagenerBusiness.addProductInventory(name, description, price, type);

		return new ResponseEntity<Response>(response, HttpStatus.valueOf(response.getCode()));
	}
	
	
	/**
	 *  Delete a product 
	 *  
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/product/inventory/id/{id}", method = RequestMethod.DELETE,  headers = "Accept=application/json"  )
	public ResponseEntity<Response> deleteProductInventory(@PathVariable int id  ) throws ParseException {

		Response response = modelMagenerBusiness.removeProductInventory(id);

		return new ResponseEntity<Response>(response, HttpStatus.valueOf(response.getCode()));
	}
	
	/**
	 * Update a product
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param price
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/product/inventory/id/{id}/name/{name}/description/{description}/price/{price}/type/{type}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Response> putProductInventory(@PathVariable int id, 
			                                            @PathVariable String name, 
			                                            @PathVariable String description,
			                                            @PathVariable double price,
			                                            @PathVariable int type) throws ParseException {

		Response response = modelMagenerBusiness.updateProductInventory(id, name, description, price, type);

		return new ResponseEntity<Response>(response, HttpStatus.valueOf(response.getCode()));
	}

}