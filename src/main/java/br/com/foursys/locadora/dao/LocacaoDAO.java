package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.bean.FormaPagamento;
import br.com.foursys.locadora.bean.Funcionario;
import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.util.JdbcUtil;

public class LocacaoDAO extends GenericDAO<Locacao> {

	private static final String BASE_SELECT = "SELECT l.id_locacao, l.data_locacao, l.data_devolucao, l.valor, l.devolvido, "
			+ "c.id_cliente, c.nome AS cliente_nome, "
			+ "fp.id_forma_pagamento, fp.descricao AS forma_pagamento_descricao, "
			+ "f.id_funcionario, f.nome AS funcionario_nome "
			+ "FROM locacao l "
			+ "INNER JOIN cliente c ON c.id_cliente = l.cliente_id_cliente "
			+ "INNER JOIN forma_pagamento fp ON fp.id_forma_pagamento = l.forma_pagamento_id_forma_pagamento "
			+ "INNER JOIN funcionario f ON f.id_funcionario = l.funcionario_id_funcionario ";

	@Override
	protected Integer getId(Locacao object) {
		return object.getIdLocacao();
	}

	@Override
	protected void setId(Locacao object, Integer id) {
		object.setIdLocacao(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO locacao (data_locacao, data_devolucao, valor, devolvido, cliente_id_cliente, forma_pagamento_id_forma_pagamento, funcionario_id_funcionario) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE locacao SET data_locacao = ?, data_devolucao = ?, valor = ?, devolvido = ?, cliente_id_cliente = ?, forma_pagamento_id_forma_pagamento = ?, funcionario_id_funcionario = ? "
				+ "WHERE id_locacao = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM locacao WHERE id_locacao = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Locacao object) throws Exception {
		statement.setString(1, object.getDataLocacao());
		statement.setString(2, object.getDataDevolucao());
		statement.setDouble(3, object.getValor());
		statement.setString(4, object.getDevolvido());
		statement.setInt(5, object.getClienteIdCliente().getIdCliente());
		statement.setInt(6, object.getFormaPagamentoIdFormaPagamento().getIdFormaPagamento());
		statement.setInt(7, object.getFuncionarioIdFuncionario().getIdFuncionario());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Locacao object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(8, object.getIdLocacao());
	}

	public ArrayList<Locacao> buscarDevolvido(String devolvido) throws Exception {
		return buscarLista(BASE_SELECT + "WHERE l.devolvido = ? ORDER BY l.id_locacao",
				statement -> statement.setString(1, devolvido));
	}

	public ArrayList<Locacao> buscarTodos() throws Exception {
		return buscarLista(BASE_SELECT + "ORDER BY l.id_locacao", null);
	}

	private ArrayList<Locacao> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Locacao> listaRetorno = new ArrayList<Locacao>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearLocacao(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Locacao mapearLocacao(ResultSet resultSet) throws Exception {
		Locacao locacao = new Locacao();
		locacao.setIdLocacao(resultSet.getInt("id_locacao"));
		locacao.setDataLocacao(resultSet.getString("data_locacao"));
		locacao.setDataDevolucao(resultSet.getString("data_devolucao"));
		locacao.setValor(resultSet.getDouble("valor"));
		locacao.setDevolvido(resultSet.getString("devolvido"));

		Cliente cliente = new Cliente();
		cliente.setIdCliente(resultSet.getInt("id_cliente"));
		cliente.setNome(resultSet.getString("cliente_nome"));

		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setIdFormaPagamento(resultSet.getInt("id_forma_pagamento"));
		formaPagamento.setDescricao(resultSet.getString("forma_pagamento_descricao"));

		Funcionario funcionario = new Funcionario();
		funcionario.setIdFuncionario(resultSet.getInt("id_funcionario"));
		funcionario.setNome(resultSet.getString("funcionario_nome"));

		locacao.setClienteIdCliente(cliente);
		locacao.setFormaPagamentoIdFormaPagamento(formaPagamento);
		locacao.setFuncionarioIdFuncionario(funcionario);
		return locacao;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}
