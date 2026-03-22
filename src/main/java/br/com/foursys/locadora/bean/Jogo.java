/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.foursys.locadora.bean;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author dmunhoz
 */
public class Jogo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idJogo;
	private String nome;
	private double valor;
	private String disponivel;
	private String promocao;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double valorPromocao;
	private String plataforma;
	private String anoLancamento;
	private int faixaEtaria;
	private String genero;
	private String imagem;
	private List<LocacaoJogo> locacaoJogoList;

	public Jogo() {
	}

	public Jogo(Integer idJogo) {
		this.idJogo = idJogo;
	}

	public Jogo(Integer idJogo, String nome, double valor, String disponivel, String promocao, String plataforma,
			String anoLancamento, int faixaEtaria, String genero, String imagem) {
		this.idJogo = idJogo;
		this.nome = nome;
		this.valor = valor;
		this.disponivel = disponivel;
		this.promocao = promocao;
		this.plataforma = plataforma;
		this.anoLancamento = anoLancamento;
		this.faixaEtaria = faixaEtaria;
		this.genero = genero;
		this.imagem = imagem;
	}

	public Integer getIdJogo() {
		return idJogo;
	}

	public void setIdJogo(Integer idJogo) {
		this.idJogo = idJogo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}

	public String getPromocao() {
		return promocao;
	}

	public void setPromocao(String promocao) {
		this.promocao = promocao;
	}

	public Double getValorPromocao() {
		return valorPromocao;
	}

	public void setValorPromocao(Double valorPromocao) {
		this.valorPromocao = valorPromocao;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(String anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public int getFaixaEtaria() {
		return faixaEtaria;
	}

	public void setFaixaEtaria(int faixaEtaria) {
		this.faixaEtaria = faixaEtaria;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getImagemExibicao() {
		if (imagem == null || imagem.trim().isEmpty()) {
			return "resources/demo/images/xml.png";
		}
		return imagem;
	}

	public List<LocacaoJogo> getLocacaoJogoList() {
		return locacaoJogoList;
	}

	public void setLocacaoJogoList(List<LocacaoJogo> locacaoJogoList) {
		this.locacaoJogoList = locacaoJogoList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idJogo != null ? idJogo.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Jogo)) {
			return false;
		}
		Jogo other = (Jogo) object;
		if ((this.idJogo == null && other.idJogo != null)
				|| (this.idJogo != null && !this.idJogo.equals(other.idJogo))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.foursys.locadora.bean.Jogo[ idJogo=" + idJogo + " ]";
	}

	public String getValorFormatado() {
		if ("Sim".equalsIgnoreCase(getPromocao())) {
			return NumberFormat.getCurrencyInstance().format(valorPromocao);
		} else {
			return NumberFormat.getCurrencyInstance().format(valor);
		}
	}

	public static Comparator<Jogo> JogoComparator = new Comparator<Jogo>() {
		public int compare(Jogo f1, Jogo f2) {
			int jogo1 = f1.getIdJogo();
			int jogo2 = f2.getIdJogo();

			// ordem crescente
			return jogo1 - jogo2;

			// ordem decrescente
			// return jogo2-jogo1;
		}
	};
}



