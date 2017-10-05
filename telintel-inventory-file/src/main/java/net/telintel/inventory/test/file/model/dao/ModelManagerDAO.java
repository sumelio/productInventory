package net.telintel.inventory.test.file.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.mysql.jdbc.Statement;

import net.telintel.inventory.test.file.model.ProcessFile;
import net.telintel.inventory.test.file.model.Product; 

/**
 * This class has the methods in order to access the database and create or get
 * information.
 * 
 * @author Freddy Lemus
 *
 */
public class ModelManagerDAO {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ModelManagerDAO.class);

	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParamJdbcTemplate;

	/**
	 *  Simple create new register
	 *  
	 * @param processFile
	 */
	public void insertProcess(ProcessFile processFile) {

		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(
						"INSERT INTO processFile(nameThread, startDate, status) VALUES (?, CURRENT_TIMESTAMP(6) , ?) ",
						Statement.RETURN_GENERATED_KEYS);

				statement.setString(1, processFile.getThreal().getName());
				statement.setString(2, processFile.getStatus().toString());

				return statement;
			}
		}, holder);

		processFile.setId(holder.getKey().intValue());

	}

	/**
	 *  Simple update process
	 *  
	 * @param processFile
	 */
	public void updateProcess(ProcessFile processFile) {
		String sqlUpdate = "update  processFile set totalProducts = ?, endDate= CURRENT_TIMESTAMP(6), status = ? where id = ?";
		jdbcTemplate.update(sqlUpdate, processFile.getFile().getLines(), processFile.getStatus().toString(),
				processFile.getId());

	}

	
	/**
	 *  Return the list of process file from table 
	 *  
	 * @return
	 */
	public List<ProcessFile> getProcessFile() {

		String sqlSelect = "select id, nameThread, startDate, endDate, status, totalProducts from processFile order by id   DESC LIMIT 5";
		List<ProcessFile> listContact = jdbcTemplate.query(sqlSelect, new RowMapper<ProcessFile>() {
			ProcessFile processFile = new ProcessFile();

			public ProcessFile mapRow(ResultSet rs, int rowNum) throws SQLException {

				processFile = new ProcessFile();
				processFile.setId(rs.getInt("id"));
				processFile.setNameThreal(rs.getString("nameThread"));
				processFile.setStart(rs.getTimestamp("startDate"));
				processFile.setEnd(rs.getTimestamp("endDate"));
				processFile.setStatus(ProcessFile.Status.valueOf(rs.getString("status")));
				processFile.setLinesFile(rs.getInt("totalProducts"));

				return processFile;
			}

		});

		return listContact;
	}
	
	

	/**
	 * Consult the total stock
	 * 
	 * @return
	 */
	public int getProductTotalStock() {
		return jdbcTemplate.queryForObject("select count(*) from product ", Integer.class).intValue();
	}
	
	

	/**
	 * Create all products contain in the product list, user batch NamedParamJdbcTemplate
	 * 
	 * @param listProdcuts
	 */
	@SuppressWarnings("unchecked")
	public synchronized int[]  createProductBatch(List<Product> listProdcuts) {

		String sql = " INSERT INTO product (name, description, unit_price, product_type_id) VALUES "
				+ "(:name, :description, :unit_price, :product_type_id)";

		List<Map<String, Object>> batchValues = new ArrayList<>(listProdcuts.size());
		listProdcuts.forEach(product -> {
			batchValues.add(new MapSqlParameterSource("name", product.getName())
					.addValue("description", product.getDescription()).addValue("unit_price", product.getPrice())
					.addValue("product_type_id", product.getProductType().getId()).getValues());

		});
		logger.info("Before Batch .......................................");
		int[] updateCounts = namedParamJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[listProdcuts.size()]));
	 
		logger.info("After Batch .......................................updateCounts " + updateCounts.length);
	    return updateCounts;
	
	}

}
