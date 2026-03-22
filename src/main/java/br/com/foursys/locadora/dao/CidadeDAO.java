package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Regiao;
import br.com.foursys.locadora.util.JdbcUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	private static final String BASE_SELECT = "SELECT c.id_cidade, c.nome, "
			+ "e.id_estado, e.codigo_uf, e.nome AS estado_nome, e.uf, "
			+ "r.id_regiao, r.nome AS regiao_nome "
			+ "FROM cidade c "
			+ "INNER JOIN estado e ON e.id_estado = c.estado_id_estado "
			+ "INNER JOIN regiao r ON r.id_regiao = e.regiao_id_regiao ";

	@Override
	protected Integer getId(Cidade object) {
		return object.getIdCidade();
	}

	@Override
	protected void setId(Cidade object, Integer id) {
		object.setIdCidade(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO cidade (nome, estado_id_estado) VALUES (?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE cidade SET nome = ?, estado_id_estado = ? WHERE id_cidade = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM cidade WHERE id_cidade = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Cidade object) throws Exception {
		statement.setString(1, object.getNome());
		statement.setInt(2, object.getEstadoIdEstado().getIdEstado());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Cidade object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(3, object.getIdCidade());
	}

	public ArrayList<Cidade> buscarTodos() throws Exception {
		return buscarLista(BASE_SELECT + "ORDER BY c.nome", null);
	}

	public ArrayList<Cidade> buscarPorEstado(Estado estado) throws Exception {
		return buscarLista(BASE_SELECT + "WHERE c.estado_id_estado = ? ORDER BY c.nome",
				statement -> statement.setInt(1, estado.getIdEstado()));
	}

	private ArrayList<Cidade> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Cidade> listaRetorno = new ArrayList<Cidade>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearCidade(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Cidade mapearCidade(ResultSet resultSet) throws Exception {
		Cidade cidade = new Cidade();
		cidade.setIdCidade(resultSet.getInt("id_cidade"));
		cidade.setNome(resultSet.getString("nome"));

		Regiao regiao = new Regiao();
		regiao.setIdRegiao(resultSet.getInt("id_regiao"));
		regiao.setNome(resultSet.getString("regiao_nome"));

		Estado estado = new Estado();
		estado.setIdEstado(resultSet.getInt("id_estado"));
		estado.setCodigoUf(resultSet.getInt("codigo_uf"));
		estado.setNome(resultSet.getString("estado_nome"));
		estado.setUf(resultSet.getString("uf"));
		estado.setRegiaoIdRegiao(regiao);

		cidade.setEstadoIdEstado(estado);
		return cidade;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}
