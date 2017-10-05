package net.telintel.inventory.test.model.dao.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.telintel.inventory.test.controller.ServiceRestController;
import net.telintel.inventory.test.model.dao.entity.Product;
import net.telintel.inventory.test.model.dao.entity.ProductType;

@Repository(value = "productDao")
public class ProductDaoImpl implements ProductDao {


	static Logger log = Logger.getLogger(ServiceRestController.class.getName());

	
	
	private EntityManager em = null;

	/*
	 * Sets the entity manager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Transactional
	public void saveProduct(Product prod) {
		em.persist(prod);
	}

	@Override
	public List<ProductType> getProductTYpes() {
		return em.createQuery("select p from ProductType p order by p.id", ProductType.class).getResultList();

	}

	@Override
	public int getProductsTotalByTypeId(int typeId) {

		BigInteger total = (BigInteger) em.createNativeQuery("select count(*) from product where product_type_id = :id").setParameter("id",
				typeId).getSingleResult();

		return total.intValue();
	}

	@Override
	public int getProductsTotalAll() {
		BigInteger total = (BigInteger) em.createNativeQuery("select count(*) from product").getSingleResult();
		return total.intValue();

	}

	@Override
	public List<Product> getProductsByPage(int typeId, int page, int pageSIze, int count) {

		// select * from where product_type_id = ? product LIMIT ?, ?

		return em.createNamedQuery("Product.pages", Product.class).setParameter("typeId", new Integer(typeId))
				.setMaxResults(pageSIze).setFirstResult(page * pageSIze).getResultList();

	}

	
	@Transactional
	@Override
	public void updateProduct(Product product) {
		em.merge(product);

	}

	@Transactional
	@Override
	public void removeProduct(int id) {
		log.info("---------------" + em.createNamedQuery("Product.ById", Product.class).setParameter("id", id).getSingleResult().getName());
		em.remove(em.createNamedQuery("Product.ById", Product.class).setParameter("id", id).getSingleResult());
	}

	@Override
	public ProductType getProductTypeById(int type) {
		return em.createNamedQuery("ProductType.findById", ProductType.class).setParameter("id", type)
				.getSingleResult();

	}

}