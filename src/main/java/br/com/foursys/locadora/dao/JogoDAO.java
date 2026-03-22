package br.com.foursys.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.util.JdbcUtil;

public class JogoDAO extends GenericDAO<Jogo> {

	@Override
	protected Integer getId(Jogo object) {
		return object.getIdJogo();
	}

	@Override
	protected void setId(Jogo object, Integer id) {
		object.setIdJogo(id);
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO jogo (nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getUpdateSql() {
		return "UPDATE jogo SET nome = ?, valor = ?, disponivel = ?, promocao = ?, valor_promocao = ?, plataforma = ?, ano_lancamento = ?, faixa_etaria = ?, genero = ?, imagem = ? "
				+ "WHERE id_jogo = ?";
	}

	@Override
	protected String getDeleteSql() {
		return "DELETE FROM jogo WHERE id_jogo = ?";
	}

	@Override
	protected void preencherInsert(PreparedStatement statement, Jogo object) throws Exception {
		statement.setString(1, object.getNome());
		statement.setDouble(2, object.getValor());
		statement.setString(3, object.getDisponivel());
		statement.setString(4, object.getPromocao());
		setNullableDouble(statement, 5, object.getValorPromocao());
		statement.setString(6, object.getPlataforma());
		statement.setString(7, object.getAnoLancamento());
		statement.setInt(8, object.getFaixaEtaria());
		statement.setString(9, object.getGenero());
		setNullableString(statement, 10, object.getImagem());
	}

	@Override
	protected void preencherUpdate(PreparedStatement statement, Jogo object) throws Exception {
		preencherInsert(statement, object);
		statement.setInt(11, object.getIdJogo());
	}

	public ArrayList<Jogo> buscarTodos() throws Exception {
		return buscarLista("SELECT id_jogo, nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem FROM jogo ORDER BY id_jogo",
				null);
	}

	public ArrayList<Jogo> buscarPorNome(String nome) throws Exception {
		return buscarLista(
				"SELECT id_jogo, nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem FROM jogo WHERE nome LIKE ? ORDER BY nome",
				statement -> statement.setString(1, nome + "%"));
	}

	public ArrayList<Jogo> buscarDisponiveis(String disponivel) throws Exception {
		return buscarLista(
				"SELECT id_jogo, nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem FROM jogo WHERE disponivel = ? ORDER BY nome",
				statement -> statement.setString(1, disponivel));
	}
	
	public ArrayList<Jogo> buscarPorFiltros(String nome, String plataforma, String genero, String disponivel, String promocao) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT id_jogo, nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem FROM jogo WHERE 1=1");
		List<String> parametros = new ArrayList<String>();

		if (nome != null && !nome.trim().isEmpty()) {
			sql.append(" AND nome LIKE ?");
			parametros.add(nome.trim() + "%");
		}
		if (plataforma != null && !plataforma.trim().isEmpty()) {
			sql.append(" AND plataforma = ?");
			parametros.add(plataforma.trim());
		}
		if (genero != null && !genero.trim().isEmpty()) {
			sql.append(" AND genero = ?");
			parametros.add(genero.trim());
		}
		if (disponivel != null && !disponivel.trim().isEmpty()) {
			sql.append(" AND disponivel = ?");
			parametros.add(disponivel.trim());
		}
		if (promocao != null && !promocao.trim().isEmpty()) {
			sql.append(" AND promocao = ?");
			parametros.add(promocao.trim());
		}

		sql.append(" ORDER BY nome");

		return buscarLista(sql.toString(), statement -> {
			for (int i = 0; i < parametros.size(); i++) {
				statement.setString(i + 1, parametros.get(i));
			}
		});
	}
	
	public ArrayList<String> buscarPlataformas() throws Exception {
		ArrayList<String> plataformas = new ArrayList<String>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT plataforma FROM jogo ORDER BY plataforma");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				plataformas.add(resultSet.getString("plataforma"));
			}
		}
		return plataformas;
	}

	public Jogo buscarPorCodigo(int codigo) throws Exception {
		ArrayList<Jogo> jogos = buscarLista(
				"SELECT id_jogo, nome, valor, disponivel, promocao, valor_promocao, plataforma, ano_lancamento, faixa_etaria, genero, imagem FROM jogo WHERE id_jogo = ?",
				statement -> statement.setInt(1, codigo));
		return jogos.isEmpty() ? null : jogos.get(0);
	}

	private ArrayList<Jogo> buscarLista(String sql, StatementSetter setter) throws Exception {
		ArrayList<Jogo> listaRetorno = new ArrayList<Jogo>();
		try (Connection connection = JdbcUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			if (setter != null) {
				setter.accept(statement);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					listaRetorno.add(mapearJogo(resultSet));
				}
			}
		}
		return listaRetorno;
	}

	private Jogo mapearJogo(ResultSet resultSet) throws Exception {
		Jogo jogo = new Jogo();
		jogo.setIdJogo(resultSet.getInt("id_jogo"));
		jogo.setNome(resultSet.getString("nome"));
		jogo.setValor(resultSet.getDouble("valor"));
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
		return jogo;
	}

	private interface StatementSetter {
		void accept(PreparedStatement statement) throws Exception;
	}
}


