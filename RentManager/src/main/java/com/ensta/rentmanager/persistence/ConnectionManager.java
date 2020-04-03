package com.ensta.rentmanager.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

public class ConnectionManager {
	private static final String DB_CONNECTION = "jdbc:h2:~/RentManagerDatabase";
	private static final String DB_TEST = "jdbc:h2:~/RentManagerDatabaseTest";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	private static JdbcDataSource datasource = null;
	private static JdbcDataSource datasourceTest = null;

	private static void init() {
		if (datasource == null) {
			datasource = new JdbcDataSource();
			datasource.setURL(DB_CONNECTION);
			datasource.setUser(DB_USER);
			datasource.setPassword(DB_PASSWORD);
		}
		
		if(datasourceTest== null) {
			datasourceTest = new JdbcDataSource();
			datasourceTest.setURL(DB_TEST);
			datasourceTest.setUser(DB_USER);
			datasourceTest.setPassword(DB_PASSWORD);
		}
	}
	
	public static Connection getConnectionForTest() throws SQLException{
		init();
		return datasourceTest.getConnection();
	}

	public static Connection getConnection() throws SQLException {
		init();
		return datasource.getConnection();
	}
	
	

}
