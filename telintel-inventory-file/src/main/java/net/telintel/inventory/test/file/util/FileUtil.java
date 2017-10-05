package net.telintel.inventory.test.file.util;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.telintel.inventory.test.file.model.File;


/**
 * Contain the useful file functions
 * 
 * @author Freddy Lemus
 *
 */
public class FileUtil {

	// Log
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	private static final String EXTENSION_TXT = ".txt";

	// Pattern 'space', 'dot' or ':'
	private static final String PATTERN_NAME = " |\\s|\\.|:";
    
	 //2017-10-01 11-47-30-677
	private static final String PATTERN_DATE = "yyyy-MM-dd HH:mm:ss.SSSS";
    
	
	
	// DataFormat for new name file
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATE);

	
	/**
	 *  Create a new name and saved a file in disk.
	 *  
	 * @param multimpartFile
	 * @param dirTemp
	 * @return
	 */
	public static File saveFile(MultipartFile multimpartFile, String dirTemp) {
		File file = null;
		try {

			Date now = new Date();

			// Put the newName like file_2017-10-01 11-47-30-677.txt
			String newName = multimpartFile.getOriginalFilename().replace(EXTENSION_TXT, "") + "_"
					+ simpleDateFormat.format(now).replaceAll(PATTERN_NAME, "-");
			newName += EXTENSION_TXT;

			// FInal directory saved
			String dir = dirTemp + newName;

			file = new File(dir, multimpartFile.getOriginalFilename(), newName, multimpartFile.getContentType(),
					multimpartFile.getSize());

			FileCopyUtils.copy(multimpartFile.getBytes(), new java.io.File(dir));
			file.setStatus(File.Status.OK);
			
		} catch (IOException e) {
			logger.error("FileUtil " + e.getMessage(), e);
			file = new File(e.getMessage(), File.Status.ERROR);
		}
		return file;
	}


	/**
	 *  THis method replace the characters special 
	 * @param name
	 * @return
	 */
	public static String formatNewName(String name) {
		
		return name.replace("\\'", "").replace("'", "");
	}

}
