package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Regiao;
import br.com.foursys.locadora.util.JdbcUtil;

public class EnderecoDAO extends GenericDAO<Endereco> {

	private static final String BASE_SELECT = "SELECT e.id_endereco, e.tipo_logradouro, e.endereco, e.numero, e.complemento, e.bairro, e.cep, "
			+ "c.id_cidade, c.nome AS cidade_nome, "
			+ "es.id_estado, es.codigo_uf, es.nome AS estado_nome, es.uf, "
			+ "r.id_regiao, r.nome AS regiao_nome "
			+ "FROM endereco e "
			+ "INNER JOIN cidade c ON c.id_cidade = e.cidade_id_cidade "
			+ "INNER JOIN estado es ON es.id_estado = c.estado_id_estado "
			+ "INNER JOIN regiao r ON r.id_regiao = es.regiao_id_regiao ";

	@Override
	protected Integer getId(Endereco object) {
		return object.getIdEndereco();
	}

	@Override
	protected void setId(Endereco object, Integer id) {
		object.setIdEndereco(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO endereco (tipo_logradouro, endereco, numero, complemento, bairro, cep, cidade_id_cidade) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE endereco SET tipo_logradouro = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cep = ?, cidade_id_cidade = ? "
				+ "WHERE id_endereco = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM endereco WHERE id_endereco = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Endereco object) throws Exception {
		statement.setString(1, object.getTipoLogradouro());
		statement.setString(2, object.getEndereco());
		statement.setInt(3, object.getNumero());
		setNullableString(statement, 4, object.getComplemento());
		statement.setString(5, object.getBairro());
		statement.setString(6, object.getCep());
		statement.setInt(7, object.getCidadeIdCidade().getIdCidade());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Endereco object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(8, object.getIdEndereco());
	}

	public Endereco buscarPorCodigo(int codigo) throws Exception {
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(BASE_SELECT + "WHERE e.id_endereco = ?")) {
			statement.setInt(1, codigo);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapearEndereco(resultSet);
				}
			}
		}
		return null;
	}

	private Endereco mapearEndereco(ResultSet resultSet) throws Exception {
		Endereco endereco = new Endereco();
		endereco.setIdEndereco(resultSet.getInt("id_endereco"));
		endereco.setTipoLogradouro(resultSet.getString("tipo_logradouro"));
		endereco.setEndereco(resultSet.getString("endereco"));
		endereco.setNumero(resultSet.getInt("numero"));
		endereco.setComplemento(resultSet.getString("complemento"));
		endereco.setBairro(resultSet.getString("bairro"));
		endereco.setCep(resultSet.getString("cep"));

		Regiao regiao = new Regiao();
		regiao.setIdRegiao(resultSet.getInt("id_regiao"));
		regiao.setNome(resultSet.getString("regiao_nome"));

		Estado estado = new Estado();
		estado.setIdEstado(resultSet.getInt("id_estado"));
		estado.setCodigoUf(resultSet.getInt("codigo_uf"));
		estado.setNome(resultSet.getString("estado_nome"));
		estado.setUf(resultSet.getString("uf"));
		estado.setRegiaoIdRegiao(regiao);

		Cidade cidade = new Cidade();
		cidade.setIdCidade(resultSet.getInt("id_cidade"));
		cidade.setNome(resultSet.getString("cidade_nome"));
		cidade.setEstadoIdEstado(estado);

		endereco.setCidadeIdCidade(cidade);
		return endereco;
	}
}
