package it.corsojava.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private final static String jdbcURL = "jdbc:mariadb://localhost/metroparis?user=root&password=12345678";
	
	public static Connection getConnection() {
		Connection c;
		try {
			c = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Impossibile connettersi al database.", e);
		}
		return c;
	}

}
