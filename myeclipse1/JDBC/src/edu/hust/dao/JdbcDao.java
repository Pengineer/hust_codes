package edu.hust.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class JdbcDao {
	private JdbcTemplate jdbcTemplate;

	public List<String[]> query(String sql) {
    	return jdbcTemplate.query(sql, new ResultSetExtractor<List<String[]>>() {
			public List<String[]> extractData(ResultSet rs) throws SQLException {
				int columnNumber = rs.getMetaData().getColumnCount();
				List<String[]> res = new ArrayList<String[]>();
				while(rs.next()) {
					String[] objs = new String[columnNumber];
					for (int i = 0; i < columnNumber; i++) {
						objs[i] = rs.getString(i + 1);
					}
					res.add(objs);
				}
				return res;
			}
		});
	}
	
	public Object queryObject(String sql,Object args[], RowMapper rse) { //无需，只要getJdbcTemplate()即可
		return jdbcTemplate.queryForObject(sql, args, rse);
	}

	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		return jdbcTemplate.getDataSource().getConnection().getMetaData();
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
