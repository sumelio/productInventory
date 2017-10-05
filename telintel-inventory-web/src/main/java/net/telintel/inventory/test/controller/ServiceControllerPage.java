package net.telintel.inventory.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

 

import org.springframework.security.core.userdetails.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

 
@Controller
public class ServiceControllerPage {
	
	private static final Logger logger = Logger.getLogger(ServiceControllerPage.class);

	
	@Value( "${maxFileUpdate}" )
	private String maxFileUpdate;
	
	
	/**
	 * Validate the page's error
	 * @param error
	 * @param logout
	 * @return
	 */
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");

		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");

		}
		model.setViewName("loginPage");

		return model;

	}

	
	/**
	 * Obtain the user's information and show inventory page
	 *
	 * @return Page inventory
	 */
	@RequestMapping(value = { "/inventory**" }, method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView(); 
		try {

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String name = user.getUsername(); // get logged in username
			boolean role = user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			model.addObject("username", name);
			model.addObject("isUserInRole", role);

			model.setViewName("inventory");
		} catch (Exception e) {
			model.setViewName("login");
		}
		return model;

	}
	
	
	/**
	 * Show page upload page and load the Maximum  files 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/multiUpload", method = RequestMethod.GET)
	public ModelAndView getMultiUploadPage() { 
		
		ModelAndView model = new ModelAndView();
		
		// this field should be parameterized
		logger.info("max files" + maxFileUpdate );
		model.addObject("maxFiles",maxFileUpdate); 
		
		model.setViewName("multiFileUploader");
		return model;
	}
	 


}