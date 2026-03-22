/**
 * 
 */
package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.bean.LocacaoJogo;
import br.com.foursys.locadora.controller.JogoController;
import br.com.foursys.locadora.controller.LocacaoController;
import br.com.foursys.locadora.controller.LocacaoJogoController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Titulo;
import br.com.foursys.locadora.util.Valida;

/**
 * Classe para controlar as funções e métodos das telas de devolução
 * 
 * @author Kalleo Leandro dos Santos Leal
 * @since 05/05/2021
 * @version 1.0
 */
@ManagedBean(name = "devolucaoBacking")
@ViewScoped
public class DevolucaoBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos de locação
	private int locacao;
	private int tipoPesquisa;
	private Locacao devolucao;
	private Locacao locacaoSelecionada;
	private Date dataInicio;
	private Date dataHoje;

	// Atributos de exibição
	private String nomeCliente;
	private String dataLocacao;
	private String dataDevolucao;
	@SuppressWarnings("unused")
	private String dataAtual;

	// Atributos de lista
	private ArrayList<Locacao> listaLocacao;
	private ArrayList<Locacao> listaLocacoes;
	private ArrayList<LocacaoJogo> listaLocacoesJogos;
	private ArrayList<Jogo> listaJogos;
	private ArrayList<Locacao> listaAuxiliar;
	
	//Atributo de controle
	private boolean bloqueio;

	// Atributos de exibição
	private Cliente cliente;

	public DevolucaoBacking() {
		listaLocacao = new ArrayList<Locacao>();
		listaJogos = new ArrayList<Jogo>();
		limparCampos();
		dataAtual = getDataAtual();
		carregarLocacao();
		carregarTodasLocacoes();
		dataHoje = new Date();
		setBloqueio(true);
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataHoje() {
		return dataHoje;
	}

	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}

	public int getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(int tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public int getLocacao() {
		return locacao;
	}

	public void setLocacao(int locacao) {
		this.locacao = locacao;
	}

	public ArrayList<Locacao> getListaLocacao() {
		return listaLocacao;
	}

	public void setListaLocacao(ArrayList<Locacao> listaLocacao) {
		this.listaLocacao = listaLocacao;
	}

	public ArrayList<Locacao> getListaLocacoes() {
		return listaLocacoes;
	}

	public void setListaLocacoes(ArrayList<Locacao> listaLocacoes) {
		this.listaLocacoes = listaLocacoes;
	}

	public ArrayList<Jogo> getListaJogos() {
		return listaJogos;
	}

	public void setListaJogos(ArrayList<Jogo> listaJogos) {
		this.listaJogos = listaJogos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Locacao> getListaAuxiliar() {
		return listaAuxiliar;
	}

	public void setListaAuxiliar(ArrayList<Locacao> listaAuxiliar) {
		this.listaAuxiliar = listaAuxiliar;
	}

	public ArrayList<LocacaoJogo> getListaLocacoesJogos() {
		return listaLocacoesJogos;
	}

	public void setListaLocacoesJogos(ArrayList<LocacaoJogo> listaLocacoesJogos) {
		this.listaLocacoesJogos = listaLocacoesJogos;
	}

	public Locacao getLocacaoSelecionada() {
		return locacaoSelecionada;
	}

	public void setLocacaoSelecionada(Locacao locacaoSelecionada) {
		this.locacaoSelecionada = locacaoSelecionada;
	}

	public Locacao getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Locacao devolucao) {
		this.devolucao = devolucao;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(String dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public String getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}

	public boolean isBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(boolean bloqueio) {
		this.bloqueio = bloqueio;
	}

	public void carregarLocacao() {
		try {
			listaLocacao = new LocacaoController().buscarDevolvido("Nao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		limparCampos();
	}

	public void carregarComponentes() {
		if (locacao > 0) {
			listaJogos = new ArrayList<Jogo>();
			devolucao = getLocacaoLista();
			setNomeCliente(devolucao.getClienteIdCliente().getNome());
			setDataLocacao(devolucao.getDataLocacao());
			setDataDevolucao(devolucao.getDataDevolucao());
			setDataAtual(getDataAtual());

			for (LocacaoJogo locacaoJogo : new LocacaoJogoController().buscarPorLocacao(devolucao)) {
				listaJogos.add(locacaoJogo.getJogoIdJogo());
			}
		} else {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_DEVOLUCAO, Mensagem.CLIENTE_VAZIO);
		}
	}

	public void salvar() {

		try {
			devolucao.setDevolvido("Sim");
			if (retornaDataDevolucao()) {
				devolucao.setDataDevolucao(devolucao.getDataLocacao());
			} else {
				devolucao.setDataDevolucao(getDataAtual());
			}

			new LocacaoController().salvar(devolucao);
			if (comparaDatas(devolucao.getDataDevolucao(),getDataAtual())) {
				JSFUtil.addInfoMessage(Titulo.CADASTRO_DEVOLUCAO, Mensagem.DEVOLUCAO_ATRASO);
			}
			alterarJogosStatus();
			JSFUtil.addInfoMessage(Titulo.CADASTRO_DEVOLUCAO, Mensagem.DEVOLUCAO_SALVO);
			cancelar();
			carregarTodasLocacoes();
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_DEVOLUCAO, Mensagem.DEVOLUCAO_ERRO_SALVO);
		}
	}

	public void cancelar() {
		limparCampos();
	}

	public void voltar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("list-locacoes.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void limparListaTabela()
	{
		if(!listaJogos.isEmpty())
		{
			listaJogos.clear();
		}
		listaLocacoesJogos = new LocacaoJogoController().buscarPorLocacao(locacaoSelecionada);
		for (LocacaoJogo f : listaLocacoesJogos) {

			listaJogos.add(f.getJogoIdJogo());
		}
	}

	public void sair() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void limparCampos() {
		setNomeCliente(null);
		setDataLocacao(null);
		setDataDevolucao(null);
		setTipoPesquisa(4);
		setDataAtual(getDataAtual());
		listaJogos = new ArrayList<Jogo>();
		listaLocacao.clear();
		carregarTodasLocacoes();
		setLocacao(0);
	}

	public void pesquisar() {
		listaLocacoes.clear();
		switch (tipoPesquisa) {
		case 1:			
			setBloqueio(false);
			break;
		case 2:
			carregarLocacoesDevolvido("Sim");
			setBloqueio(true);
			break;
		case 3:
			carregarLocacoesDevolvido("Nao");
			setBloqueio(true);
			break;
		case 4:
			carregarTodasLocacoes();
			setBloqueio(true);
			break;
		}
	}

	private void carregarTodasLocacoes() {
		try {
			listaLocacoes = new LocacaoController().buscarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void carregarLocacoesDevolvido(String devolvido) {
		try {
			listaLocacoes = new LocacaoController().buscarDevolvido(devolvido);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarLocacoesPorData() {
		if(Valida.isDateNull(dataInicio))
		{
			JSFUtil.addErrorMessage(Titulo.LISTA_LOCACAO, Mensagem.DATA_INICIO_VAZIO);
		}
		else
		{			
			listaLocacoes.clear();			
			try {
				listaAuxiliar = new LocacaoController().buscarTodos();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			for(Locacao l: listaAuxiliar)
			{			
				
				if(comparaDatas(getDataConvertida(dataInicio),l.getDataLocacao()))
				{
					System.out.println(l.getDataLocacao());
					listaLocacoes.add(l);								
				}			
			}			
			listaAuxiliar.clear();
		}
	}

	private Locacao getLocacaoLista() {
		int index = listaLocacao.indexOf(new Locacao(locacao));
		return listaLocacao.get(index);
	}

	private void alterarJogosStatus() {
		for (Jogo jogo : listaJogos) {
			jogo.setDisponivel("Sim");
			try {
				new JogoController().salvar(jogo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private boolean retornaDataDevolucao() {

		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(devolucao.getDataDevolucao()));
			d.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(getDataAtual()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (d.before(c)) {
			return true;
		}
		return false;
	}

	private boolean comparaDatas(String a, String b) {
		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(a));
			d.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(b));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (d.equals(c) || d.after(c)) {
			return true;
		}
		return false;
	}

	private String getDataAtual() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(new Date());
	}
	private String getDataConvertida(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(data);
	}
}


