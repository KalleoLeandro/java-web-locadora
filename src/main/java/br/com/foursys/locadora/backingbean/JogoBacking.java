package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.controller.JogoController;
import br.com.foursys.locadora.util.Genero;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Titulo;
import br.com.foursys.locadora.util.Valida;

/**
 * Classe responsável por controlar os componentes de front-end da tela
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

@ManagedBean(name = "jogoBacking")
@SessionScoped
public class JogoBacking implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Atributos da tela de cadastro
	
	
	private String nome;
	private String valor;
	private String disponivel;
	private String promocao;
	private String valorPromocao;	
	private String plataforma;
	private String anoLancamento;
	private String faixaEtaria;
	private String genero;
	private String imagem;
	
	//Atributos da tela de consulta
	private String nomePesquisar;
	private String plataformaPesquisar;
	private String generoPesquisar;
	private String disponivelPesquisar;
	private String promocaoPesquisar;
	private Jogo jogoSelecionado;	
	
	private Jogo jogo;
	private ArrayList<Jogo> listaJogos;
	
	private ArrayList<String> listaGeneros;
	private ArrayList<String> listaPlataformas;
	private ArrayList<String> listaDisponibilidade;
	private ArrayList<String> listaPromocoes;	
	
	public ArrayList<String> getListaGeneros() {
		return listaGeneros;
	}

	public void setListaGeneros(ArrayList<String> listaGeneros) {
		this.listaGeneros = listaGeneros;
	}
	
	public ArrayList<String> getListaPlataformas() {
		return listaPlataformas;
	}

	public void setListaPlataformas(ArrayList<String> listaPlataformas) {
		this.listaPlataformas = listaPlataformas;
	}

	public ArrayList<String> getListaDisponibilidade() {
		return listaDisponibilidade;
	}

	public void setListaDisponibilidade(ArrayList<String> listaDisponibilidade) {
		this.listaDisponibilidade = listaDisponibilidade;
	}

	public ArrayList<String> getListaPromocoes() {
		return listaPromocoes;
	}

	public void setListaPromocoes(ArrayList<String> listaPromocoes) {
		this.listaPromocoes = listaPromocoes;
	}

	public JogoBacking()
	{
		listaJogos = new ArrayList<Jogo>();
		carregarGeneros();
		carregarPlataformas();
		carregarFiltrosBooleanos();
		limpaCampos();
		carregarTodosJogos();
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
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
	public String getValorPromocao() {
		return valorPromocao;
	}
	public void setValorPromocao(String valorPromocao) {
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
	public String getFaixaEtaria() {
		return faixaEtaria;
	}
	public void setFaixaEtaria(String faixaEtaria) {
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
	
	public ArrayList<Jogo> getListaJogos() {
		return listaJogos;
	}
	public void setListaJogos(ArrayList<Jogo> listaJogos) {
		this.listaJogos = listaJogos;
	}	
	
	public String getNomePesquisar() {
		return nomePesquisar;
	}
	public void setNomePesquisar(String nomePesquisar) {
		this.nomePesquisar = nomePesquisar;
	}
	public String getPlataformaPesquisar() {
		return plataformaPesquisar;
	}
	public void setPlataformaPesquisar(String plataformaPesquisar) {
		this.plataformaPesquisar = plataformaPesquisar;
	}
	public String getGeneroPesquisar() {
		return generoPesquisar;
	}
	public void setGeneroPesquisar(String generoPesquisar) {
		this.generoPesquisar = generoPesquisar;
	}
	public String getDisponivelPesquisar() {
		return disponivelPesquisar;
	}
	public void setDisponivelPesquisar(String disponivelPesquisar) {
		this.disponivelPesquisar = disponivelPesquisar;
	}
	public String getPromocaoPesquisar() {
		return promocaoPesquisar;
	}
	public void setPromocaoPesquisar(String promocaoPesquisar) {
		this.promocaoPesquisar = promocaoPesquisar;
	}	
	
	public Jogo getJogoSelecionado() {
		return jogoSelecionado;
	}
	public void setJogoSelecionado(Jogo jogoSelecionado) {
		this.jogoSelecionado = jogoSelecionado;
	}	
	
	
	/*
	 * M�todo que captura a a��o do bot�o CADASTRAR na tela cad-jogo.jsp
	 */
	
	
	public void cadastrar()
	{
		if(validar())
		{
			//salvar os dados do jogo na base
			try
			{
				getJogo();
				new JogoController().salvar(jogo);
				limpaCampos();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_JOGO,Mensagem.JOGO_SALVO);
			}
			catch(Exception e)
			{
				JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.ERRO_SALVAR_JOGO);
			}			
		}
		
	}
	
	public void alterarJogo()
	{
		if(validar())
		{
			//salvar os dados do jogo na base
			try
			{
				getJogoAlterar();
				new JogoController().salvar(jogo);
				limpaCampos();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_JOGO, Mensagem.JOGO_ALTERADO);
			}
			catch(Exception e)
			{
				JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.ERRO_ALTERAR_JOGO);
			}			
		}		
	}
	
	private boolean validar()
	{
		if(Valida.isEmptyOrNull(nome))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.nomeVazio);
			return false;
		}
		if(!Valida.isDouble(valor))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.valorVazio);
			return false;
		}
		else if(Valida.isDoubleZero(Double.parseDouble(valor)))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.valorInvalido);
			return false;
		}
		if(Valida.isEmptyOrNull(disponivel))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.disponivelVazio);
			return false;
		}
		if(Valida.isEmptyOrNull(promocao))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.promocaoVazio);
			return false;
		}
		if("Sim".equalsIgnoreCase(promocao))
		{
			if(!Valida.isDouble(valorPromocao))
			{
				//exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.valorPromocaoVazio);
				return false;
			}
			else if(Valida.isDoubleZero(Double.parseDouble(valorPromocao)))
			{
				//exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.valorPromocaoInvalido);
				return false;
			}			
		}		
		if(Valida.isEmptyOrNull(plataforma))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.plataformaVazio);
			return false;
		}
		
		if(!Valida.isInteger(anoLancamento))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.anoLancamentoVazio);
			return false;
		}
		else if(Valida.isIntZero(Integer.parseInt(anoLancamento)))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.anoLancamentoInvalido);
			return false;		
		}
		
		if(!Valida.isInteger(faixaEtaria))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.faixaEtariaVazia);
			return false;
		}
		else if(Valida.isIntZero(Integer.parseInt(faixaEtaria)))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.faixaEtariaInvalida);
			return false;
		}		
		if(Valida.isEmptyOrNull(genero))
		{
			//exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.generoVazio);
			return false;
		}
		if(Valida.isEmptyOrNull(imagem))
		{
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.imagemVazia);
			return false;
		}
		
		return true;
		
	}
	
	/*
	 * M�todo que captura a a��o do bot�o CANCELAR na tela cad-jogo.jsp
	 * */
	
	public void cancelar()
	{
		limpaCampos();
	}
	
	/*
	 * M�todo que captura a a��o do bot�o SAIR na tela cad-jogo.jsp 
	 * */
	public void sair()
	{
		limpaCampos();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/*
	 * M�todo para abrir a tela de cadastro
	 * */
	
	public String cadastroJogo()
	{
		limpaCampos();
		return "";
	}
	
	/*
	 * M�todo para abrir a tela de consulta
	 * */
	
	public String consultarJogo()
	{
		nomePesquisar = null;
		plataformaPesquisar = null;
		generoPesquisar = null;
		disponivelPesquisar = null;
		promocaoPesquisar = null;
		carregarPlataformas();
		carregarTodosJogos();
		return "";
	}
	
	/*
	 * M�todo para retornar um objeto jogo
	 * */
	
	private void getJogo()
	{
		jogo =  new Jogo();
		jogo.setNome(nome);
		jogo.setValor(Double.parseDouble(valor));
		jogo.setDisponivel(disponivel);
		jogo.setPromocao(promocao);
		if("Sim".equalsIgnoreCase(promocao))
		{
			jogo.setValorPromocao(Double.parseDouble(valorPromocao));
		}
		jogo.setPlataforma(plataforma);
		jogo.setAnoLancamento(anoLancamento);
		jogo.setFaixaEtaria(Integer.parseInt(faixaEtaria));
		jogo.setGenero(genero);
		jogo.setImagem(imagem);		
	}
	
	private void limpaCampos()
	{
		setNome(null);
		setValor(null);
		setDisponivel(null);
		setPromocao(null);
		setValorPromocao(null);
		setFaixaEtaria(null);
		setPlataforma(null);
		setAnoLancamento(null);
		setGenero(null);
		setImagem(null);
		listaJogos.clear();
	}
	
	
	/*
	 * M�todo para pesquisar jogo
	 * */
	
	public String pesquisar()
	{
		try
		{
			if(Valida.isEmptyOrNull(nomePesquisar)
					&& Valida.isEmptyOrNull(plataformaPesquisar)
					&& Valida.isEmptyOrNull(generoPesquisar)
					&& Valida.isEmptyOrNull(disponivelPesquisar)
					&& Valida.isEmptyOrNull(promocaoPesquisar))
			{
				carregarTodosJogos();
			}
			else
			{
				listaJogos = new JogoController().buscarPorFiltros(nomePesquisar, plataformaPesquisar, generoPesquisar,
						disponivelPesquisar, promocaoPesquisar);
			}
		}
		catch(Exception e)
		{
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.ERRO_PESQUISAR_JOGO);
		}
		return "";
	}

	private void carregarTodosJogos()
	{
		try
		{
			listaJogos = new JogoController().buscarTodos();
		}
		catch(Exception e)
		{
			listaJogos = new ArrayList<Jogo>();
		}
	}
	
	public void limparFiltros()
	{
		nomePesquisar = null;
		plataformaPesquisar = null;
		generoPesquisar = null;
		disponivelPesquisar = null;
		promocaoPesquisar = null;
		carregarTodosJogos();
	}
	
	public void redirecionar(String opcao)
	{	jogoRedirecionado();
		if(opcao.equals("alterar"))
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("alt-jogo.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("detalha-jogo.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void jogoRedirecionado()
	{
		nome = jogoSelecionado.getNome();
		valor = jogoSelecionado.getValor()+"";
		disponivel = jogoSelecionado.getDisponivel();
		promocao = jogoSelecionado.getPromocao();
		if(jogoSelecionado.getValorPromocao() == null)
		{
			valorPromocao = "";
		}
		else
		{
			valorPromocao = jogoSelecionado.getValorPromocao()+"";
		}
		
		plataforma = jogoSelecionado.getPlataforma();
		faixaEtaria = jogoSelecionado.getFaixaEtaria()+ "";
		anoLancamento = jogoSelecionado.getAnoLancamento();
		genero = jogoSelecionado.getGenero();
		imagem = jogoSelecionado.getImagem();
	}
	
	public void voltar()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("list-jogo.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void excluir()
	{
		try
		{
			new JogoController().excluir(jogoSelecionado);
			limpaCampos();
			JSFUtil.addInfoMessage(Titulo.CADASTRO_JOGO, Mensagem.JOGO_EXCLUIDO);
		}
		catch(Exception e)
		{
			JSFUtil.addErrorMessage(Titulo.CADASTRO_JOGO, Mensagem.ERRO_EXCLUIR_JOGO);
		}				
	}
	
	private void getJogoAlterar()
	{
		jogo =  jogoSelecionado;
		jogo.setNome(nome);
		jogo.setValor(Double.parseDouble(valor));
		jogo.setDisponivel(disponivel);
		jogo.setPromocao(promocao);
		if("Sim".equalsIgnoreCase(promocao))
		{
			jogo.setValorPromocao(Double.parseDouble(valorPromocao));
		}
		jogo.setPlataforma(plataforma);
		jogo.setAnoLancamento(anoLancamento);
		jogo.setFaixaEtaria(Integer.parseInt(faixaEtaria));
		jogo.setGenero(genero);
		jogo.setImagem(imagem);		
	}
	
	/*
	 * Método para carregar a lista de gêneros
	 * */
	
	public void carregarGeneros()
	{
		listaGeneros = new ArrayList<String>();
		for(Genero g: Genero.values())
		{
			listaGeneros.add(g.getDescricao());	
		}
	}
	
	public void carregarPlataformas()
	{
		try
		{
			listaPlataformas = new JogoController().buscarPlataformas();
		}
		catch(Exception e)
		{
			listaPlataformas = new ArrayList<String>();
		}
	}
	
	private void carregarFiltrosBooleanos()
	{
		listaDisponibilidade = new ArrayList<String>();
		listaDisponibilidade.add("Sim");
		listaDisponibilidade.add("Nao");
		
		listaPromocoes = new ArrayList<String>();
		listaPromocoes.add("Sim");
		listaPromocoes.add("Nao");
	}

}



