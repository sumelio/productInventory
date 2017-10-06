package net.telintel.inventory.test.file.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
 
import net.telintel.inventory.test.file.model.File;
import net.telintel.inventory.test.file.model.Product;
import net.telintel.inventory.test.file.model.ProductType;

public class FileManager {

	private static final Logger logger = Logger.getLogger(FileManager.class);

	
	private File file;
	private String path;
	
	
	public FileManager() {
	}
	
	
	public List<Product> listProduct;


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public List<Product> getListProduct() {
		if(listProduct == null) {
			listProduct = new ArrayList<>();
		}
		return listProduct;
	}


	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}
	
	public File readProducsFromFile(File  file) { 
		  this.file = file;
   
		try {

			java.io.File diskFile = new java.io.File(this.file.getPath());

			if (diskFile.exists()) {

				Scanner sc = new Scanner(diskFile);
                Product product = null;
                String firstLine =  sc.nextLine();
                logger.info(firstLine);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] fields = line.split("\\|");
					
					String name = fields[0];
					String description = fields[1];
					ProductType type = new ProductType(new Integer(fields[2]));
					Double unitPrice = new Double(fields[3]);
					product = new Product(name, description,unitPrice,type );
					
					this.getListProduct().add(product);
	 
				}
				
				sc.close();
				file.setLines(this.getListProduct().size());
			} else {
				logger.error("Nothing file = " + file.getPath());
				file.setStatus(File.Status.ERROR);
				file.setDetail("File not found");
			}
			file.setStatus(File.Status.OK);
			file.setDetail("OK_Read");
		} catch (NumberFormatException e) {
			logger.error("Error NumberFormatException " + e.getMessage(), e);
			file.setStatus(File.Status.ERROR);
			file.setDetail(e.getMessage());
		} catch(Exception e) {
			logger.error("Error Exception " + e.getMessage(), e);
			file.setStatus(File.Status.ERROR);
			file.setDetail(e.getMessage());
		}
		return this.file;
		
	}
	
}
