package csdc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class JdbcDao {

    private JdbcTemplate jdbcTemplate;

    public List<String[]> query(String sql) {
    	return jdbcTemplate.query(sql, new ResultSetExtractor<List<String[]>>() {
			public List<String[]> extractData(ResultSet rs) throws SQLException {
				int columnNumber = rs.getMetaData().getColumnCount();
				List res = new ArrayList();
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

    
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    

    // JDBC-backed implementations of the methods on the CorporateEventDao follow...
}