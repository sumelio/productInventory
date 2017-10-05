package net.telintel.inventory.test.file.model.business;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;

import net.telintel.inventory.test.file.clientWebSocket.NotifyClientBean;
import net.telintel.inventory.test.file.model.File;
import net.telintel.inventory.test.file.model.ProcessFile;
import net.telintel.inventory.test.file.model.Product;
import net.telintel.inventory.test.file.model.ProcessFile.Status;
import net.telintel.inventory.test.file.model.dao.ModelManagerDAO;
import net.telintel.inventory.test.file.util.FileManager;
import net.telintel.inventory.test.file.util.FileUtil;


/**
 * This Bean implement all logic in order to load and register product information from files
 * 
 * @author Freddy Lemus
 *
 */
public class BusinessExecuteBean {

	private static final Logger logger = Logger.getLogger(BusinessExecuteBean.class);

	@Autowired
	private ModelManagerDAO modelManagerDAO;

	@Autowired
	private NotifyClientBean notifyClient;
	
 


	/**
	 * Package limit of batch
	 * 
	 */
	private static final int LIMIT_BATCH = 1000;
	
	
	/**
	 * Execute the necessary steps in order to read, insert in BD and notification the load
	 * of the files. THe main steps are;
	 * 1. Read
	 * 2. Register in BD
	 * 3. Notification 'notfy topic' 
	 * 
	 * @param names
	 * @param listfiles
	 * @param path
	 * @param modelManagerDAO
	 * @throws URISyntaxException
	 */
	public void processAsync(String[] names, List<File> listfiles, String path) {

		ExecutorService executor = Executors.newFixedThreadPool(names.length);

		Arrays.asList(names).forEach((name) -> { 
			File file_ = new File();
			file_.setNameNew(FileUtil.formatNewName(name));
			file_.setPath(path + file_.getNameNew());
			listfiles.add(file_);

			Runnable registerProduct = () -> {

				// Step 1. Init thread
				Thread.currentThread().setName("Thread_" + name);
				logger.info(":::::" + Thread.currentThread().getName() + " is running ");
				ProcessFile processFile = new ProcessFile(file_, Thread.currentThread());
				

				// Step 2. Read file
				modelManagerDAO.insertProcess(processFile);
				
				processFile.setStatus(Status.READ_FILE_OK);
				modelManagerDAO.updateProcess(processFile);
				FileManager fileManager = new FileManager();
				fileManager.readProducsFromFile(file_);
				
				processFile.setStatus(Status.READ_FILE_OK);
				modelManagerDAO.updateProcess(processFile);
				
				if (processFile.isOKReadProduct()) {
					
					processFile.setStatus(Status.DATA_BASE_PROCESSING);
					modelManagerDAO.updateProcess(processFile);
					
					// Step 3. Insert DataBase
					this.createProductBatch(processFile, fileManager.getListProduct());;

					if (processFile.isOKInsertDBProduct()) {
				

						// Step 4. Notify
						// StompSession stompSession = notifyClient.getSession();
						notifyClient.getSession();
						int totalStock = modelManagerDAO.getProductTotalStock();
						processFile.setTotalStock(totalStock);
						
						processFile.setStatus(Status.NOFITY_SENT_PROCESSING);
						modelManagerDAO.updateProcess(processFile); 
						
						if ( notifyClient.isConnected()) {
							
							
							notifyClient.sendMessage(processFile.getMessageFormatJson());
                            
							processFile.setStatus(Status.NOFITY_SENT_OK);
							modelManagerDAO.updateProcess(processFile);
							notifyClient.getSession().disconnect();
						}else {
							processFile.setStatus(Status.NOFITY_SENT_ERROR);
							modelManagerDAO.updateProcess(processFile);
							notifyClient.getSession().disconnect();
						} 
						

					} else {
						processFile.setStatus(Status.DATA_BASE_ERROR);
					}
					modelManagerDAO.updateProcess(processFile);

				} else {
					processFile.setStatus(Status.READ_FILE_ERROR);

				}
				modelManagerDAO.updateProcess(processFile);

				logger.info(Thread.currentThread().getName() + " is finishing ");

			};

			executor.execute(registerProduct);
		}

		);
		notifyClient.getSession().disconnect();
		logger.info(notifyClient.getSession() + " is closing session webSocket ");
      
		executor.shutdown(); // CLose Executor

		// while (!executor.isTerminated()) {
		// System.out.println("enddd");
		// }

	}
	

	/**
	 * Implement the logic in order to create all product in the data base. The
	 * package limit is LIMIT_BATCH. THere is a synchronized block because there
	 * could be more than one process use this resource in one instance.
	 * 
	 * @param processFile
	 * @param lsitProducts
	 * @throws Exception
	 */
	public void createProductBatch(ProcessFile processFile, List<Product> listProduct) {
		int i = 0;

		List<Product> subListtProduct = new ArrayList<Product>();
		try {
			synchronized (this) {

				for (Product product : listProduct) {

					subListtProduct.add(product);
					i++;

					if (i % LIMIT_BATCH == 0) {
						// Do this each LIMIT_BATCH
						modelManagerDAO.createProductBatch(subListtProduct);
						subListtProduct  = new ArrayList<Product>(); 
					}
				}
                
				// The list < LIMIT_BATCH
				if (!subListtProduct.isEmpty()) {
					modelManagerDAO.createProductBatch(subListtProduct);
				}
			}

			processFile.setStatus(Status.DATA_BASE_OK);
			modelManagerDAO.updateProcess(processFile);

		} catch (Exception e) {
			processFile.setStatus(ProcessFile.Status.DATA_BASE_ERROR);
			modelManagerDAO.updateProcess(processFile);
			logger.error("Error  insert batch ");
		}
	}

}
