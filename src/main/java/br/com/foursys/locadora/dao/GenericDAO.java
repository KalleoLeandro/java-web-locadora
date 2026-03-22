package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import br.com.foursys.locadora.util.JdbcUtil;

public abstract class GenericDAO<T> {

	public void salvar(T object) throws Exception {
		Integer id = getId(object);
		if (id == null || id.intValue() <= 0) {
			inserir(object);
			return;
		}

		atualizar(object);
	}

	public void excluir(T object) throws Exception {
		Integer id = getId(object);
		if (id == null || id.intValue() <= 0) {
			return;
		}

		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(getDeleteSql())) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}

	private void inserir(T object) throws Exception {
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {
			preencherInsert(statement, object);
			statement.executeUpdate();
			try (java.sql.ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					setId(object, generatedKeys.getInt(1));
				}
			}
		}
	}

	private void atualizar(T object) throws Exception {
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(getUpdateSql())) {
			preencherUpdate(statement, object);
			statement.executeUpdate();
		}
	}

	protected void setNullableString(PreparedStatement statement, int index, String value) throws SQLException {
		if (value == null || value.trim().isEmpty()) {
			statement.setNull(index, Types.VARCHAR);
			return;
		}
		statement.setString(index, value);
	}

	protected void setNullableDouble(PreparedStatement statement, int index, Double value) throws SQLException {
		if (value == null) {
			statement.setNull(index, Types.DOUBLE);
			return;
		}
		statement.setDouble(index, value.doubleValue());
	}

	protected abstract Integer getId(T object);

	protected abstract void setId(T object, Integer id);

	protected abstract String getInsertSql();

	protected abstract String getUpdateSql();

	protected abstract String getDeleteSql();

	protected abstract void preencherInsert(PreparedStatement statement, T object) throws Exception;

	protected abstract void preencherUpdate(PreparedStatement statement, T object) throws Exception;
}
