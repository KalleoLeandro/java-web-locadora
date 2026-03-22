package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.util.JdbcUtil;

public class ContatoDAO extends GenericDAO<Contato> {

	@Override
	protected Integer getId(Contato object) {
		return object.getIdContato();
	}

	@Override
	protected void setId(Contato object, Integer id) {
		object.setIdContato(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO contato (telefone, celular, email) VALUES (?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE contato SET telefone = ?, celular = ?, email = ? WHERE id_contato = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM contato WHERE id_contato = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Contato object) throws Exception {
		setNullableString(statement, 1, object.getTelefone());
		setNullableString(statement, 2, object.getCelular());
		setNullableString(statement, 3, object.getEmail());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Contato object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(4, object.getIdContato());
	}

	public ArrayList<Contato> buscarTodos() throws Exception {
		ArrayList<Contato> listaRetorno = new ArrayList<Contato>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT id_contato, telefone, celular, email FROM contato ORDER BY id_contato");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				listaRetorno.add(mapearContato(resultSet));
			}
		}
		return listaRetorno;
	}

	public Contato buscarPorCodigo(int codigo) throws Exception {
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT id_contato, telefone, celular, email FROM contato WHERE id_contato = ?")) {
			statement.setInt(1, codigo);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapearContato(resultSet);
				}
			}
		}
		return null;
	}

	private Contato mapearContato(ResultSet resultSet) throws Exception {
		Contato contato = new Contato();
		contato.setIdContato(resultSet.getInt("id_contato"));
		contato.setTelefone(resultSet.getString("telefone"));
		contato.setCelular(resultSet.getString("celular"));
		contato.setEmail(resultSet.getString("email"));
		return contato;
	}
}
