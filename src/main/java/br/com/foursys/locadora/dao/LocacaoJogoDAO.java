package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.bean.LocacaoJogo;
import br.com.foursys.locadora.util.JdbcUtil;

public class LocacaoJogoDAO extends GenericDAO<LocacaoJogo> {

	private static final String BASE_SELECT = "SELECT lf.id_locacao_jogo, "
			+ "l.id_locacao, l.data_locacao, l.data_devolucao, l.valor, l.devolvido, "
			+ "f.id_jogo, f.nome, f.valor AS jogo_valor, f.disponivel, f.promocao, f.valor_promocao, f.plataforma, f.ano_lancamento, f.faixa_etaria, f.genero, f.imagem "
			+ "FROM locacao_jogo lf "
			+ "INNER JOIN locacao l ON l.id_locacao = lf.locacao_id_locacao "
			+ "INNER JOIN jogo f ON f.id_jogo = lf.jogo_id_jogo ";

	@Override
	protected Integer getId(LocacaoJogo object) {
		return object.getIdLocacaoJogo();
	}

	@Override
	protected void setId(LocacaoJogo object, Integer id) {
		object.setIdLocacaoJogo(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO locacao_jogo (jogo_id_jogo, locacao_id_locacao) VALUES (?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE locacao_jogo SET jogo_id_jogo = ?, locacao_id_locacao = ? WHERE id_locacao_jogo = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM locacao_jogo WHERE id_locacao_jogo = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, LocacaoJogo object) throws Exception {
		statement.setInt(1, object.getJogoIdJogo().getIdJogo());
		statement.setInt(2, object.getLocacaoIdLocacao().getIdLocacao());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, LocacaoJogo object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(3, object.getIdLocacaoJogo());
	}

	public ArrayList<LocacaoJogo> buscarPorLocacao(Locacao locacao) throws Exception {
		ArrayList<LocacaoJogo> listaRetorno = new ArrayList<LocacaoJogo>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(BASE_SELECT + "WHERE lf.locacao_id_locacao = ? ORDER BY lf.locacao_id_locacao")) {
			statement.setInt(1, locacao.getIdLocacao());
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearLocacaoJogo(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private LocacaoJogo mapearLocacaoJogo(ResultSet resultSet) throws Exception {
		LocacaoJogo locacaoJogo = new LocacaoJogo();
		locacaoJogo.setIdLocacaoJogo(resultSet.getInt("id_locacao_jogo"));

		Locacao locacao = new Locacao();
		locacao.setIdLocacao(resultSet.getInt("id_locacao"));
		locacao.setDataLocacao(resultSet.getString("data_locacao"));
		locacao.setDataDevolucao(resultSet.getString("data_devolucao"));
		locacao.setValor(resultSet.getDouble("valor"));
		locacao.setDevolvido(resultSet.getString("devolvido"));

		Jogo jogo = new Jogo();
		jogo.setIdJogo(resultSet.getInt("id_jogo"));
		jogo.setNome(resultSet.getString("nome"));
		jogo.setValor(resultSet.getDouble("jogo_valor"));
		jogo.setDisponivel(resultSet.getString("disponivel"));
		jogo.setPromocao(resultSet.getString("promocao"));
		double valorPromocao = resultSet.getDouble("valor_promocao");
		if (!resultSet.wasNull()) {
			jogo.setValorPromocao(valorPromocao);
		}
		jogo.setPlataforma(resultSet.getString("plataforma"));
		jogo.setAnoLancamento(resultSet.getString("ano_lancamento"));
		jogo.setFaixaEtaria(resultSet.getInt("faixa_etaria"));
		jogo.setGenero(resultSet.getString("genero"));

		jogo.setImagem(resultSet.getString("imagem"));

		locacaoJogo.setLocacaoIdLocacao(locacao);
		locacaoJogo.setJogoIdJogo(jogo);
		return locacaoJogo;
	}
}



