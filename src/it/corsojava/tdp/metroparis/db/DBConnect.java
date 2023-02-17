package it.corsojava.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;


public class DBConnect {
	static private HikariDataSource ds = null;
	private static String jdbcURL = "jdbc:mariadb://localhost/metroparis";
	
	public static Connection getConnection() {
		if(ds == null) {
			ds = new HikariDataSource();
			ds.setJdbcUrl(jdbcURL);
			ds.setUsername("root");
			ds.setPassword("Macintosh10");
		}
		
		try {
			return ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
			}
	}
}
