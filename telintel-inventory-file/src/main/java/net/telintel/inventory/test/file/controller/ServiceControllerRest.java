package net.telintel.inventory.test.file.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.telintel.inventory.test.file.model.File;
import net.telintel.inventory.test.file.model.Files;
import net.telintel.inventory.test.file.model.ProcessFile;
import net.telintel.inventory.test.file.model.business.BusinessExecuteBean;
import net.telintel.inventory.test.file.model.business.ModelManagerBusiness;
import net.telintel.inventory.test.file.util.FileUtil;


/**
 * 
 * @author m
 *
 */
@RestController
public class ServiceControllerRest {

	// Log
	private static final Logger logger = Logger.getLogger(ServiceControllerRest.class);

	@Autowired
	private Environment env;


	@Autowired
	private BusinessExecuteBean businessExecute;
	
	@Autowired
	private ModelManagerBusiness modelManagerBusiness;
	
	
	
	private static String UPLOAD_FILE_LOCATION = "file.upload.location";
	

	/**
	 * Upload the files list and saved in disk 
	 * 
	 * @param multimpartFile
	 * @param redirectAttributes
	 * @return Json structure
	 */
	@PostMapping("/upload/files")
	public ResponseEntity<Files> uploadFile(@RequestParam("files") MultipartFile multimpartFile,
			RedirectAttributes redirectAttributes) {

		logger.info("POST file name = " + multimpartFile.getOriginalFilename());

		// Saved the file
		File file_ = FileUtil.saveFile(multimpartFile, env.getProperty(UPLOAD_FILE_LOCATION));

		List<File> listfiles = new ArrayList<>();
		Files files = new Files();
		listfiles.add(file_);
		files.setFiles(listfiles);

		ResponseEntity<Files> list = new ResponseEntity<Files>(files, HttpStatus.OK);
		return list;
	}


	/**
	 * 
	 * received a file list and create the threads in order to read and insert all product in Bd.
	 * 
	 * @param names
	 * @return
	 */
	@PostMapping(value = "/products/files/names/{names}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Files> product(@PathVariable String[] names) {

	 
		List<File> listfiles = new ArrayList<>();
	 
	 
		businessExecute.processAsync(names, listfiles, env.getProperty(UPLOAD_FILE_LOCATION));

		ResponseEntity<Files> list = new ResponseEntity<Files>(new Files(), HttpStatus.OK);
		return list;
	}
	
	
	

	
	/**
	 * 
	 * @param names
	 * @return
	 */
	@GetMapping(value = "/processFiles")
	public ResponseEntity<List<ProcessFile>> getProcessFile() {

	 
		List<ProcessFile> listProcess = modelManagerBusiness.getProcessFiles();

		

		ResponseEntity<List<ProcessFile>> list = new ResponseEntity<List<ProcessFile>>(listProcess, HttpStatus.OK);
		return list;
	}
	
	 

}
