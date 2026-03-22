package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Regiao;
import br.com.foursys.locadora.util.JdbcUtil;

public class ClienteDAO extends GenericDAO<Cliente> {

	private static final String BASE_SELECT = "SELECT c.id_cliente, c.nome, c.cpf, c.rg, c.data_nascimento, c.idade, c.sexo, "
			+ "ct.id_contato, ct.telefone, ct.celular, ct.email, "
			+ "e.id_endereco, e.tipo_logradouro, e.endereco, e.numero, e.complemento, e.bairro, e.cep, "
			+ "ci.id_cidade, ci.nome AS cidade_nome, "
			+ "es.id_estado, es.codigo_uf, es.nome AS estado_nome, es.uf, "
			+ "r.id_regiao, r.nome AS regiao_nome "
			+ "FROM cliente c "
			+ "INNER JOIN contato ct ON ct.id_contato = c.contato_id_contato "
			+ "INNER JOIN endereco e ON e.id_endereco = c.endereco_id_endereco "
			+ "INNER JOIN cidade ci ON ci.id_cidade = e.cidade_id_cidade "
			+ "INNER JOIN estado es ON es.id_estado = ci.estado_id_estado "
			+ "INNER JOIN regiao r ON r.id_regiao = es.regiao_id_regiao ";

	@Override
	protected Integer getId(Cliente object) {
		return object.getIdCliente();
	}

	@Override
	protected void setId(Cliente object, Integer id) {
		object.setIdCliente(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO cliente (nome, cpf, rg, data_nascimento, idade, sexo, contato_id_contato, endereco_id_endereco) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE cliente SET nome = ?, cpf = ?, rg = ?, data_nascimento = ?, idade = ?, sexo = ?, contato_id_contato = ?, endereco_id_endereco = ? "
				+ "WHERE id_cliente = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM cliente WHERE id_cliente = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Cliente object) throws Exception {
		statement.setString(1, object.getNome());
		statement.setString(2, object.getCpf());
		statement.setString(3, object.getRg());
		statement.setString(4, object.getDataNascimento());
		statement.setInt(5, object.getIdade());
		statement.setString(6, object.getSexo());
		statement.setInt(7, object.getContatoIdContato().getIdContato());
		statement.setInt(8, object.getEnderecoIdEndereco().getIdEndereco());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Cliente object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(9, object.getIdCliente());
	}

	public ArrayList<Cliente> buscarTodos() throws Exception {
		return buscarLista(BASE_SELECT + "ORDER BY c.id_cliente", null);
	}

	public ArrayList<Cliente> buscarPorNome(String nome) throws Exception {
		return buscarLista(BASE_SELECT + "WHERE c.nome LIKE ? ORDER BY c.nome", statement -> statement.setString(1, nome + "%"));
	}

	public Cliente buscarPorCodigo(int codigo) throws Exception {
		ArrayList<Cliente> clientes = buscarLista(BASE_SELECT + "WHERE c.id_cliente = ?", statement -> statement.setInt(1, codigo));
		return clientes.isEmpty() ? null : clientes.get(0);
	}

	private ArrayList<Cliente> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Cliente> listaRetorno = new ArrayList<Cliente>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearCliente(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Cliente mapearCliente(ResultSet resultSet) throws Exception {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(resultSet.getInt("id_cliente"));
		cliente.setNome(resultSet.getString("nome"));
		cliente.setCpf(resultSet.getString("cpf"));
		cliente.setRg(resultSet.getString("rg"));
		cliente.setDataNascimento(resultSet.getString("data_nascimento"));
		cliente.setIdade(resultSet.getInt("idade"));
		cliente.setSexo(resultSet.getString("sexo"));

		Contato contato = new Contato();
		contato.setIdContato(resultSet.getInt("id_contato"));
		contato.setTelefone(resultSet.getString("telefone"));
		contato.setCelular(resultSet.getString("celular"));
		contato.setEmail(resultSet.getString("email"));

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

		Endereco endereco = new Endereco();
		endereco.setIdEndereco(resultSet.getInt("id_endereco"));
		endereco.setTipoLogradouro(resultSet.getString("tipo_logradouro"));
		endereco.setEndereco(resultSet.getString("endereco"));
		endereco.setNumero(resultSet.getInt("numero"));
		endereco.setComplemento(resultSet.getString("complemento"));
		endereco.setBairro(resultSet.getString("bairro"));
		endereco.setCep(resultSet.getString("cep"));
		endereco.setCidadeIdCidade(cidade);

		cliente.setContatoIdContato(contato);
		cliente.setEnderecoIdEndereco(endereco);
		return cliente;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}
