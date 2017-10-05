package net.telintel.inventory.test.file.configuration;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import net.telintel.inventory.test.file.clientWebSocket.NotifyClientBean;
import net.telintel.inventory.test.file.model.business.BusinessExecuteBean;
import net.telintel.inventory.test.file.model.business.ModelManagerBusiness;
import net.telintel.inventory.test.file.model.dao.ModelManagerDAO;

@Configuration
@EnableWebMvc
@PropertySource({ "classpath:telintel-inventory-${envTarget:file_dev}.properties" })
@ComponentScan(basePackages = "net.telintel.inventory.test.file.controller")
public class TelintelInventoryFileConfiguration {

	private static final Logger logger = Logger.getLogger(TelintelInventoryFileConfiguration.class);

	@Autowired
	private Environment env;

	/**
	 * Limit namedParameterJdbcTemplate
	 */
	private static final int CACHE_Limit = 100;
	
	
	@Bean(name = "multipartResolver")
	public StandardServletMultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}

	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Bean
	public ModelManagerDAO getModelManagerDAO() {
		ModelManagerDAO managerDAO = new ModelManagerDAO();
		return managerDAO;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() throws SQLException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriver(new com.mysql.jdbc.Driver());
		dataSource.setUrl(env.getProperty("jdbc.connection.url"));
		dataSource.setUsername(env.getProperty("jdbc.connection.user"));
		dataSource.setPassword(env.getProperty("jdbc.connection.password"));
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() throws SQLException {

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new com.mysql.jdbc.Driver());
		dataSource.setUrl(env.getProperty("jdbc.connection.url"));
		dataSource.setUsername(env.getProperty("jdbc.connection.user"));
		dataSource.setPassword(env.getProperty("jdbc.connection.password"));
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		namedParameterJdbcTemplate.setCacheLimit(CACHE_Limit);
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public BusinessExecuteBean getBusinessExecute() {
		return new BusinessExecuteBean();
	}

	@Bean
	public NotifyClientBean getNotifyClient() {
		return new NotifyClientBean();
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");

		registry.addResourceHandler("/libs/**").addResourceLocations("/libs/");
		registry.addResourceHandler("/app/**").addResourceLocations("/app/");
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

	@Bean
	public ModelManagerBusiness getModelManagerBusiness() {
		return new ModelManagerBusiness();
	}

}