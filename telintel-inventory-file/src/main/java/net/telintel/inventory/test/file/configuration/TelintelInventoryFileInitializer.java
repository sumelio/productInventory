package net.telintel.inventory.test.file.configuration;

import java.io.BufferedInputStream;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * @author Freddy Lemus
 * Date 03-10-2017
 *
 */
public class TelintelInventoryFileInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger logger = Logger.getLogger(TelintelInventoryFileInitializer.class);
    
	private static final String NAME_PROPERTIES = "telintel-inventory-file_dev.properties";
    private static final String LOCATION = "file.upload.location";
    private static final String FILE_SIZE = "file.max.file.size";
    private static final String REQUEST_SIZE = "file.request.size";
    private static final String SIZE_THRESHOLD = "file.size.threshold";

	@Override
	protected Class<?>[] getRootConfigClasses() {
		logger.info("getRootConfigClasses ========================");
		return new Class[] { TelintelInventoryFileConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(getMultipartConfigElement());
	}

	
	/**
	 *  Configuaration for ultipart
	 *  
	 * @return
	 */
	private MultipartConfigElement getMultipartConfigElement() {

		MultipartConfigElement multipartConfigElement = null;

		Properties properties = new Properties();
		try (BufferedInputStream input = new BufferedInputStream(
				TelintelInventoryFileInitializer.class.getClassLoader().getResourceAsStream(NAME_PROPERTIES))) {

			properties.load(input);
			
			String updateLocation = properties.getProperty(LOCATION);
			long fileMaxFileSize = new Long(properties.getProperty(FILE_SIZE)).longValue();
			long fileMaxRequestSize = new Long(properties.getProperty(REQUEST_SIZE)).longValue();
			int fileSizeThreshold = new Integer(properties.getProperty(SIZE_THRESHOLD)).intValue();

			logger.info("================================ updateLocation=" + updateLocation);
			multipartConfigElement = new MultipartConfigElement(updateLocation, fileMaxFileSize, fileMaxRequestSize,
					fileSizeThreshold);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return multipartConfigElement;
	}

}