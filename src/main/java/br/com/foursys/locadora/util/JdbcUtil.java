package br.com.foursys.locadora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = System.getProperty("locadora.jdbc.url",
			"jdbc:mysql://localhost:3306/dtb-locadora-rich?zeroDateTimeBehavior=convertToNull");
	private static final String USER = System.getProperty("locadora.jdbc.user", "root");
	private static final String PASSWORD = System.getProperty("locadora.jdbc.password", "root");

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Nao foi possivel carregar o driver JDBC do MySQL.", e);
		}
	}

	private JdbcUtil() {
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static Connection getServerConnection() throws SQLException {
		return DriverManager.getConnection(getServerUrl(), USER, PASSWORD);
	}

	public static String getDatabaseName() {
		int jdbcPrefixIndex = URL.indexOf("://");
		int pathStart = URL.indexOf('/', jdbcPrefixIndex >= 0 ? jdbcPrefixIndex + 3 : 0);
		if (pathStart < 0 || pathStart == URL.length() - 1) {
			return "";
		}

		int queryStart = URL.indexOf('?', pathStart);
		if (queryStart < 0) {
			return URL.substring(pathStart + 1);
		}

		return URL.substring(pathStart + 1, queryStart);
	}

	public static String getServerUrl() {
		int jdbcPrefixIndex = URL.indexOf("://");
		int pathStart = URL.indexOf('/', jdbcPrefixIndex >= 0 ? jdbcPrefixIndex + 3 : 0);
		if (pathStart < 0) {
			return URL;
		}

		int queryStart = URL.indexOf('?', pathStart);
		if (queryStart < 0) {
			return URL.substring(0, pathStart + 1);
		}

		return URL.substring(0, pathStart + 1) + URL.substring(queryStart);
	}
}
