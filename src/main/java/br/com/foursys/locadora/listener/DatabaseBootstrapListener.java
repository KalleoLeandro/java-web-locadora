package br.com.foursys.locadora.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.foursys.locadora.util.DatabaseInitializer;

public class DatabaseBootstrapListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(DatabaseBootstrapListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String enabled = sce.getServletContext().getInitParameter("locadora.db.init.enabled");
		if (!"true".equalsIgnoreCase(enabled)) {
			return;
		}

		String failOnError = sce.getServletContext().getInitParameter("locadora.db.init.failOnError");
		if ("true".equalsIgnoreCase(failOnError)) {
			DatabaseInitializer.initialize();
			return;
		}

		DatabaseInitializer.initializeSafely();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		shutdownMySqlCleanupThread();
		deregisterJdbcDrivers();
	}

	private void shutdownMySqlCleanupThread() {
		try {
			com.mysql.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
		} catch (Throwable e) {
			LOGGER.log(Level.FINE, "Nao foi necessario encerrar a thread de cleanup do MySQL.", e);
		}
	}

	private void deregisterJdbcDrivers() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				LOGGER.log(Level.FINE, "Nao foi possivel remover o driver JDBC " + driver, e);
			}
		}
	}
}
