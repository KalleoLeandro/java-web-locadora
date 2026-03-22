package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Regiao;
import br.com.foursys.locadora.util.JdbcUtil;

public class EstadoDAO extends GenericDAO<Estado> {

	private static final String BASE_SELECT = "SELECT e.id_estado, e.codigo_uf, e.nome, e.uf, "
			+ "r.id_regiao AS regiao_id_regiao, r.nome AS regiao_nome "
			+ "FROM estado e INNER JOIN regiao r ON r.id_regiao = e.regiao_id_regiao ";

	@Override
	protected Integer getId(Estado object) {
		return object.getIdEstado();
	}

	@Override
	protected void setId(Estado object, Integer id) {
		object.setIdEstado(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO estado (codigo_uf, nome, uf, regiao_id_regiao) VALUES (?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE estado SET codigo_uf = ?, nome = ?, uf = ?, regiao_id_regiao = ? WHERE id_estado = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM estado WHERE id_estado = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Estado object) throws Exception {
		statement.setInt(1, object.getCodigoUf());
		statement.setString(2, object.getNome());
		statement.setString(3, object.getUf());
		statement.setInt(4, object.getRegiaoIdRegiao().getIdRegiao());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Estado object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(5, object.getIdEstado());
	}

	public ArrayList<Estado> buscarTodos() throws Exception {
		return buscarLista(BASE_SELECT + "ORDER BY e.id_estado", null);
	}

	public Estado buscarPorCodigo(int codigo) throws Exception {
		ArrayList<Estado> estados = buscarLista(BASE_SELECT + "WHERE e.id_estado = ?", statement -> statement.setInt(1, codigo));
		return estados.isEmpty() ? null : estados.get(0);
	}

	private ArrayList<Estado> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Estado> listaRetorno = new ArrayList<Estado>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearEstado(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Estado mapearEstado(ResultSet resultSet) throws Exception {
		Estado estado = new Estado();
		estado.setIdEstado(resultSet.getInt("id_estado"));
		estado.setCodigoUf(resultSet.getInt("codigo_uf"));
		estado.setNome(resultSet.getString("nome"));
		estado.setUf(resultSet.getString("uf"));

		Regiao regiao = new Regiao();
		regiao.setIdRegiao(resultSet.getInt("regiao_id_regiao"));
		regiao.setNome(resultSet.getString("regiao_nome"));
		estado.setRegiaoIdRegiao(regiao);
		return estado;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}
