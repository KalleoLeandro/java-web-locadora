package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Funcionario;
import br.com.foursys.locadora.bean.Regiao;
import br.com.foursys.locadora.util.JdbcUtil;

public class FuncionarioDAO extends GenericDAO<Funcionario> {

	private static final String BASE_SELECT = "SELECT f.id_funcionario, f.nome, f.cpf, f.rg, f.data_nascimento, f.idade, f.sexo, f.login, f.senha, f.perfil_acesso, "
			+ "ct.id_contato, ct.telefone, ct.celular, ct.email, "
			+ "e.id_endereco, e.tipo_logradouro, e.endereco, e.numero, e.complemento, e.bairro, e.cep, "
			+ "ci.id_cidade, ci.nome AS cidade_nome, "
			+ "es.id_estado, es.codigo_uf, es.nome AS estado_nome, es.uf, "
			+ "r.id_regiao, r.nome AS regiao_nome "
			+ "FROM funcionario f "
			+ "INNER JOIN contato ct ON ct.id_contato = f.contato_id_contato "
			+ "INNER JOIN endereco e ON e.id_endereco = f.endereco_id_endereco "
			+ "INNER JOIN cidade ci ON ci.id_cidade = e.cidade_id_cidade "
			+ "INNER JOIN estado es ON es.id_estado = ci.estado_id_estado "
			+ "INNER JOIN regiao r ON r.id_regiao = es.regiao_id_regiao ";

	@Override
	protected Integer getId(Funcionario object) {
		return object.getIdFuncionario();
	}

	@Override
	protected void setId(Funcionario object, Integer id) {
		object.setIdFuncionario(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO funcionario (nome, cpf, rg, data_nascimento, idade, sexo, login, senha, perfil_acesso, contato_id_contato, endereco_id_endereco) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE funcionario SET nome = ?, cpf = ?, rg = ?, data_nascimento = ?, idade = ?, sexo = ?, login = ?, senha = ?, perfil_acesso = ?, contato_id_contato = ?, endereco_id_endereco = ? "
				+ "WHERE id_funcionario = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM funcionario WHERE id_funcionario = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Funcionario object) throws Exception {
		statement.setString(1, object.getNome());
		statement.setString(2, object.getCpf());
		statement.setString(3, object.getRg());
		statement.setString(4, object.getDataNascimento());
		statement.setInt(5, object.getIdade());
		statement.setString(6, object.getSexo());
		statement.setString(7, object.getLogin());
		statement.setString(8, object.getSenha());
		statement.setString(9, object.getPerfilAcesso());
		statement.setInt(10, object.getContatoIdContato().getIdContato());
		statement.setInt(11, object.getEnderecoIdEndereco().getIdEndereco());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Funcionario object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(12, object.getIdFuncionario());
	}

	public ArrayList<Funcionario> buscarTodos() throws Exception {
		return buscarLista(BASE_SELECT + "ORDER BY f.id_funcionario", null);
	}

	public ArrayList<Funcionario> buscarPorNome(String nome) throws Exception {
		return buscarLista(BASE_SELECT + "WHERE f.nome LIKE ? ORDER BY f.nome",
				statement -> statement.setString(1, nome + "%"));
	}

	public Funcionario buscarPorCodigo(int codigo) throws Exception {
		ArrayList<Funcionario> funcionarios = buscarLista(BASE_SELECT + "WHERE f.id_funcionario = ?",
				statement -> statement.setInt(1, codigo));
		return funcionarios.isEmpty() ? null : funcionarios.get(0);
	}

	public ArrayList<Funcionario> buscarPorLogin(String login) throws Exception {
		return buscarLista(BASE_SELECT + "WHERE f.login = ? ORDER BY f.nome",
				statement -> statement.setString(1, login));
	}

	private ArrayList<Funcionario> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Funcionario> listaRetorno = new ArrayList<Funcionario>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearFuncionario(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Funcionario mapearFuncionario(ResultSet resultSet) throws Exception {
		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(resultSet.getInt("id_funcionario"));
		funcionario.setNome(resultSet.getString("nome"));
		funcionario.setCpf(resultSet.getString("cpf"));
		funcionario.setRg(resultSet.getString("rg"));
		funcionario.setDataNascimento(resultSet.getString("data_nascimento"));
		funcionario.setIdade(resultSet.getInt("idade"));
		funcionario.setSexo(resultSet.getString("sexo"));
		funcionario.setLogin(resultSet.getString("login"));
		funcionario.setSenha(resultSet.getString("senha"));
		funcionario.setPerfilAcesso(resultSet.getString("perfil_acesso"));

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

		funcionario.setContatoIdContato(contato);
		funcionario.setEnderecoIdEndereco(endereco);
		return funcionario;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}
