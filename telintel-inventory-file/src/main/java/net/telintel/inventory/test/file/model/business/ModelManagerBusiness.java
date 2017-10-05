package net.telintel.inventory.test.file.model.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.fabric.xmlrpc.base.Array;

import net.telintel.inventory.test.file.model.ProcessFile;
import net.telintel.inventory.test.file.model.Product;
import net.telintel.inventory.test.file.model.ProcessFile.Status;
import net.telintel.inventory.test.file.model.dao.ModelManagerDAO;

/**
 * This class has the logic of business and implement the logic necessary in
 * order to access the database
 * 
 * @author Freddy Lemus
 *
 */
public class ModelManagerBusiness {

	private static final Logger logger = Logger.getLogger(ModelManagerBusiness.class);

	@Autowired
	ModelManagerDAO modelManagerDAO = new ModelManagerDAO();


	/**
	 * 
	 * @return
	 */
	public List<ProcessFile> getProcessFiles() {
		return modelManagerDAO.getProcessFile();
	}


}
