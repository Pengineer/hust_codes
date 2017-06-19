package edu.hust.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import edu.hust.bean.City;
import edu.hust.dao.JdbcDao;

//没有配置数据源，本类不可执行

public class Test2 {
	
	public static void main(String[] args) throws SQLException {
		
	}
	
	public static City findCity(int id) {
		String sql = "select id, c_name from t_table where id=?";
		Object[] args = new Object[]{id};
		
		JdbcDao jdbcDao = new JdbcDao();
		//RowMapper使用的是策略模式：为了通用，父类中不用指明查询的是什么对象，用Object代替，而父类中通过一个方法来确定对象，该方法被封装在RowMapper抽象接口中
		//用户要查询什么对象只需要复写该方法即可。
		Object city = (City) jdbcDao.queryObject(sql, args, new RowMapper() { 
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city = new City();
				city.setId(rs.getInt("id"));
				city.setName(rs.getString("c_name"));
				return city;//注意：可以看出，此处返回的对象并不是数据库中通过某一行对应的关系对象（区别于Hibernate）
			}
		});
		
		return (City)city;
	}
	
	@SuppressWarnings("unchecked")
	public int addCity(final City city) {
		JdbcDao jdbcDao = new JdbcDao();
		jdbcDao.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				String sql = "insert into city(name) values(?)"; //给指定的字段添加值
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(2, city.getName());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys(); //生成主键
				if(rs.next()) {
					city.setId(rs.getInt(1));
				}
				
				return city;
			}
		});
		
		return 1;
	}
}
