package br.com.foursys.locadora.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseInitializer {

	private static final String SCRIPT_PATH = "/db/init.sql";
	private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

	private DatabaseInitializer() {
	}

	public static void initialize() {
		try {
			ensureDatabaseExists();
			if (isSchemaInitialized()) {
				ensureJogoSeedData();
				LOGGER.info("Banco de dados ja inicializado.");
				return;
			}

			runInitializationScript();
			LOGGER.info("Banco de dados inicializado com sucesso.");
		} catch (Exception e) {
			throw new IllegalStateException("Nao foi possivel inicializar o banco de dados da locadora.", e);
		}
	}

	public static void initializeSafely() {
		try {
			initialize();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Inicializacao automatica do banco indisponivel. Verifique se o MySQL esta em execucao e se a URL JDBC esta correta.", e);
		}
	}

	private static void ensureDatabaseExists() throws SQLException {
		String databaseName = JdbcUtil.getDatabaseName();
		if (databaseName == null || databaseName.trim().isEmpty()) {
			throw new SQLException("Nao foi possivel identificar o nome do banco na URL JDBC.");
		}

		try (Connection connection = JdbcUtil.getServerConnection(); Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + databaseName + "` DEFAULT CHARACTER SET utf8");
		}
	}

	private static boolean isSchemaInitialized() throws SQLException {
		try (Connection connection = JdbcUtil.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			if (!tableExists(metaData, "cliente")) {
				return false;
			}
			return tableExists(metaData, "jogo");
		}
	}

	private static void ensureJogoSeedData() throws SQLException, IOException {
		try (Connection connection = JdbcUtil.getConnection()) {
			if (!tableHasData(connection, "jogo")) {
				LOGGER.info("Tabela jogo vazia. Reaplicando seed de jogos.");
				reseedJogos(connection);
			}
		}
	}

	private static boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
		try (ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] { "TABLE" })) {
			return resultSet.next();
		}
	}

	private static boolean tableHasData(Connection connection, String tableName) throws SQLException {
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM " + tableName)) {
			if (resultSet.next()) {
				return resultSet.getInt(1) > 0;
			}
			return false;
		}
	}

	private static void runInitializationScript() throws SQLException, IOException {
		try (Connection connection = JdbcUtil.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute("SET FOREIGN_KEY_CHECKS=0");
			try {
				for (String sql : splitStatements(loadScript())) {
					if (!sql.isEmpty() && !shouldSkip(sql)) {
						statement.execute(sql);
					}
				}
			} finally {
				statement.execute("SET FOREIGN_KEY_CHECKS=1");
			}
		}
	}

	private static void reseedJogos(Connection connection) throws SQLException, IOException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("SET FOREIGN_KEY_CHECKS=0");
			try {
				try {
					statement.executeUpdate("DELETE FROM locacao_jogo");
				} catch (SQLException e) {
					LOGGER.log(Level.FINE, "Tabela locacao_jogo ainda nao disponivel para limpeza.", e);
				}
				statement.executeUpdate("DELETE FROM jogo");

				for (String sql : splitStatements(extractJogoSeedBlock(loadScript()))) {
					if (!sql.isEmpty() && !shouldSkip(sql)) {
						statement.execute(sql);
					}
				}
			} finally {
				statement.execute("SET FOREIGN_KEY_CHECKS=1");
			}
		}
	}

	private static String loadScript() throws IOException {
		InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(SCRIPT_PATH);
		if (inputStream == null) {
			throw new IOException("Script de inicializacao nao encontrado em " + SCRIPT_PATH);
		}

		StringBuilder script = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			String line;
			boolean firstLine = true;
			while ((line = reader.readLine()) != null) {
				if (firstLine && !line.isEmpty() && line.charAt(0) == '\uFEFF') {
					line = line.substring(1);
				}
				firstLine = false;

				if (line.trim().startsWith("--")) {
					continue;
				}
				script.append(line).append('\n');
			}
		}
		return script.toString();
	}

	private static List<String> splitStatements(String script) {
		String normalizedScript = script.replace("\r", "");
		StringBuilder current = new StringBuilder();
		List<String> statements = new ArrayList<String>();
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;

		for (int i = 0; i < normalizedScript.length(); i++) {
			char currentChar = normalizedScript.charAt(i);
			char nextChar = i + 1 < normalizedScript.length() ? normalizedScript.charAt(i + 1) : '\0';

			if (!inSingleQuote && !inDoubleQuote && currentChar == '/' && nextChar == '*') {
				int commentEnd = normalizedScript.indexOf("*/", i + 2);
				if (commentEnd < 0) {
					break;
				}
				i = commentEnd + 1;
				continue;
			}

			if (currentChar == '\'' && !inDoubleQuote && !isEscaped(normalizedScript, i)) {
				inSingleQuote = !inSingleQuote;
			} else if (currentChar == '"' && !inSingleQuote && !isEscaped(normalizedScript, i)) {
				inDoubleQuote = !inDoubleQuote;
			}

			if (currentChar == ';' && !inSingleQuote && !inDoubleQuote) {
				String statement = current.toString().trim();
				if (!statement.isEmpty()) {
					statements.add(statement);
				}
				current.setLength(0);
				continue;
			}

			current.append(currentChar);
		}

		String tail = current.toString().trim();
		if (!tail.isEmpty()) {
			statements.add(tail);
		}

		return statements;
	}

	private static String extractJogoSeedBlock(String script) throws IOException {
		String startMarker = "-- Copiando dados para a tabela dtb-locadora-rich.jogo:";
		String endMarker = "-- Copiando estrutura para tabela dtb-locadora-rich.forma_pagamento";
		int startIndex = script.indexOf(startMarker);
		if (startIndex < 0) {
			throw new IOException("Bloco de seed de jogos nao encontrado no script de inicializacao.");
		}

		int endIndex = script.indexOf(endMarker, startIndex);
		if (endIndex < 0) {
			return script.substring(startIndex);
		}

		return script.substring(startIndex, endIndex);
	}

	private static boolean isEscaped(String value, int index) {
		int escapeCount = 0;
		for (int i = index - 1; i >= 0 && value.charAt(i) == '\\'; i--) {
			escapeCount++;
		}
		return escapeCount % 2 != 0;
	}

	private static boolean shouldSkip(String sql) {
		String normalized = sql.trim().toUpperCase();
		return normalized.startsWith("USE ")
				|| normalized.startsWith("SET ")
				|| normalized.startsWith("ALTER TABLE ") && normalized.contains(" DISABLE KEYS")
				|| normalized.startsWith("ALTER TABLE ") && normalized.contains(" ENABLE KEYS");
	}
}
