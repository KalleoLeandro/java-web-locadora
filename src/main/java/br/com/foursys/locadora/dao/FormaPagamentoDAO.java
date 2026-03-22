package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.FormaPagamento;
import br.com.foursys.locadora.util.JdbcUtil;

public class FormaPagamentoDAO extends GenericDAO<FormaPagamento> {

	@Override
	protected Integer getId(FormaPagamento object) {
		return object.getIdFormaPagamento();
	}

	@Override
	protected void setId(FormaPagamento object, Integer id) {
		object.setIdFormaPagamento(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO forma_pagamento (descricao) VALUES (?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE forma_pagamento SET descricao = ? WHERE id_forma_pagamento = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM forma_pagamento WHERE id_forma_pagamento = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, FormaPagamento object) throws Exception {
		statement.setString(1, object.getDescricao());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, FormaPagamento object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(2, object.getIdFormaPagamento());
	}

	public ArrayList<FormaPagamento> buscarTodos() throws Exception {
		ArrayList<FormaPagamento> listaRetorno = new ArrayList<FormaPagamento>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT id_forma_pagamento, descricao FROM forma_pagamento ORDER BY id_forma_pagamento");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				FormaPagamento formaPagamento = new FormaPagamento();
				formaPagamento.setIdFormaPagamento(resultSet.getInt("id_forma_pagamento"));
				formaPagamento.setDescricao(resultSet.getString("descricao"));
				listaRetorno.add(formaPagamento);
			}
		}
		return listaRetorno;
	}
}
