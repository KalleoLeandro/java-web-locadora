/**
 * 
 */
package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.bean.FormaPagamento;
import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.bean.LocacaoJogo;
import br.com.foursys.locadora.controller.ClienteController;
import br.com.foursys.locadora.controller.JogoController;
import br.com.foursys.locadora.controller.FormaPagamentoController;
import br.com.foursys.locadora.controller.LocacaoController;
import br.com.foursys.locadora.controller.LocacaoJogoController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Titulo;
import br.com.foursys.locadora.util.Valida;

/**
 * Classe para controlar as funções e métodos das telas de locação
 * 
 * @author Kalleo Leandro dos Santos Leal
 * @since 05/05/2021
 * @version 1.0
 */

@ManagedBean(name = "locacaoBacking")
@ViewScoped
public class LocacaoBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos de datas

	private Date dataAtual;

	private String dataLocacao;
	private Date dataDevolucao;
	
	private Date dataMaxima;
	
	// Atributos de cliente
	private String codigo;
	private String nomeCliente;
	private String cpf;
	private Cliente clienteSelecionado;

	// Atributos de jogo
	private int jogoEscolhido;
	private Jogo jogoSelecionado;

	// Atributos de locação
	private Locacao locacao;

	// Atributos de listas
	private ArrayList<Jogo> listaJogos;
	private ArrayList<Cliente> listaClientes;
	private ArrayList<Jogo> listaJogosTabela;
	private ArrayList<FormaPagamento> listaFormaDePagamento;

	// Atributos de forma de pagamento
	private int formaDePagamento;
	private String valor;
	private double valorTotal;

	// Atributos de controle
	private boolean bloqueio;
	private int indexTab;

	public LocacaoBacking() {
		carregaFormasDePagamento();
		carregarDataAtual();
		carregaDataMaxima();
		clienteSelecionado = new Cliente();
		listaJogosTabela = new ArrayList<Jogo>();
		valorTotal = 0;
		setBloqueio(true);
	}

	public boolean isBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(boolean bloqueio) {
		this.bloqueio = bloqueio;
	}

	public int getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(int formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Date getDataMaxima() {
		return dataMaxima;
	}

	public void setDataMaxima(Date dataMaxima) {
		this.dataMaxima = dataMaxima;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getJogoEscolhido() {
		return jogoEscolhido;
	}

	public void setJogoEscolhido(int jogoEscolhido) {
		this.jogoEscolhido = jogoEscolhido;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getDataLocacao() {
		return dataLocacao;
	}	

	public void setDataLocacao(String dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public ArrayList<Jogo> getListaJogos() {
		return listaJogos;
	}

	public Jogo getJogoSelecionado() {
		return jogoSelecionado;
	}

	public void setJogoSelecionado(Jogo jogoSelecionado) {
		this.jogoSelecionado = jogoSelecionado;
	}

	public void setListaJogos(ArrayList<Jogo> listaJogos) {
		this.listaJogos = listaJogos;
	}

	public ArrayList<Jogo> getListaJogosTabela() {
		return listaJogosTabela;
	}

	public void setListaJogosTabela(ArrayList<Jogo> listaJogosTabela) {
		this.listaJogosTabela = listaJogosTabela;
	}

	public ArrayList<FormaPagamento> getListaFormaDePagamento() {
		return listaFormaDePagamento;
	}

	public void setListaFormaDePagamento(ArrayList<FormaPagamento> listaFormaDePagamento) {
		this.listaFormaDePagamento = listaFormaDePagamento;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public String cadastroLocacao() {
		limpaCampos();
		return "";
	}

	/*
	 * salvar
	 */

	public void salvar() {
		if (validar()) {
			try {
				getLocacao();
				new LocacaoController().salvar(locacao);
				salvarLocacaoJogos();
				limpaCampos();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_LOCACAO,Mensagem.LOCACAO_SALVO);
			} catch (Exception e) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.LOCACAO_ERRO);
			}
		}
	}

	private void getLocacao() {
		locacao = new Locacao();

		locacao.setDataLocacao(dataLocacao);
		locacao.setDataDevolucao(getDateToString(dataDevolucao));

		locacao.setValor(valorTotal);
		locacao.setDevolvido("Nao");

		locacao.setFuncionarioIdFuncionario(LoginBacking.funcionarioLogado);
		locacao.setFormaPagamentoIdFormaPagamento(getFormaPagamentoItem());
		locacao.setClienteIdCliente(clienteSelecionado);

	}

	private boolean validar() {

		if (clienteSelecionado == null) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.CLIENTE_VAZIO);
			return false;
		}

		if (Valida.isIntZero(listaJogosTabela.size())) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.JOGO_VAZIO);
			return false;
		}

		if (Valida.isIntZero(formaDePagamento)) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.FORMA_PAGAMENTO_VAZIO);
			return false;
		}

		if (Valida.isDateNull(dataDevolucao)) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.DATA_DEVOLUCAO_VAZIO);
			return false;
		}
		return true;
	}

	/*
	 * Método para limpar os campos de preenchimento
	 */

	public void cancelar() {
		limpaCampos();
		setBloqueio(true);
	}

	private void limpaCampos() {
		setNomeCliente(null);
		setCpf(null);
		clienteSelecionado = new Cliente();		
		listaJogos.clear();
		listaJogosTabela.clear();
		listaClientes.clear();
		setFormaDePagamento(0);
		valorTotal = 0;
		setValor(null);
		setIndexTab(0);
		setBloqueio(true);
	}	

	private void carregaFormasDePagamento() {

		try {
			listaFormaDePagamento = new FormaPagamentoController().buscarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	private void carregaJogos() {
		try {
			listaJogos = new JogoController().buscarDisponiveis("Sim");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String carregaClienteSelecionado() {
		try {
			listaClientes = new ClienteController().buscarTodos();
			for (Cliente c : listaClientes) {

				if (c.getCpf().equals(cpf)) {
					clienteSelecionado = c;
				}
			}
			setBloqueio(false);
			carregaJogos();
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.ERRO_PESQUISAR_CLIENTE);
		}
		return "";
	}

	private void carregarDataAtual() {
		dataAtual = new Date();
		dataLocacao = getDateToString(dataAtual);
	}

	public void adicionarJogo() {
		if (jogoEscolhido > 0) {
			int index = listaJogos.indexOf(new Jogo(jogoEscolhido));
			Jogo jogo = listaJogos.get(index);

			if (jogo.getFaixaEtaria() > getClienteSelecionado().getIdade()) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.JOGO_N_PERMITIDO);
			} else {
				listaJogosTabela.add(jogo);
				listaJogos.remove(jogo);

				Collections.sort(listaJogosTabela, Jogo.JogoComparator);

				if (jogo.getValorPromocao() != null) {
					valorTotal += jogo.getValorPromocao();
					valor = NumberFormat.getCurrencyInstance().format(jogo.getValorPromocao());
				} else {
					valorTotal += jogo.getValor();
					valor = NumberFormat.getCurrencyInstance().format(jogo.getValor());
				}				
			}

		} else {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_LOCACAO, Mensagem.JOGO_VAZIO);
		}
	}
	
	

	public void excluir() {
		listaJogosTabela.remove(jogoSelecionado);
		listaJogos.add(jogoSelecionado);

		Collections.sort(listaJogos, Jogo.JogoComparator);

		if ("Sim".equalsIgnoreCase(jogoSelecionado.getPromocao())) {
			valorTotal -= jogoSelecionado.getValorPromocao();
		} else {
			valorTotal -= jogoSelecionado.getValor();
		}
		valor = NumberFormat.getCurrencyInstance().format(valorTotal);

	}

	/*
	 * Método para controlar a função do botão sair
	 */

	private FormaPagamento getFormaPagamentoItem() {
		int indice = listaFormaDePagamento.indexOf(new FormaPagamento(formaDePagamento));
		return listaFormaDePagamento.get(indice);
	}

	public void desbloqueioJogos() {
		if (clienteSelecionado != null) {
			setBloqueio(false);
		} else {
			setBloqueio(true);
		}
		limpaCampos();
	}
	
	private void salvarLocacaoJogos() {
		for (Jogo jogo : listaJogosTabela) {
			try {
				LocacaoJogo locacaoJogo = new LocacaoJogo();
				locacaoJogo.setJogoIdJogo(jogo);
				locacaoJogo.setLocacaoIdLocacao(locacao);

				new LocacaoJogoController().salvar(locacaoJogo);

				jogo.setDisponivel("Nao");
				new JogoController().salvar(jogo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carregaDataMaxima()
	{
		dataMaxima = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dataMaxima); 
		c.add(Calendar.DATE, 30);
		dataMaxima = c.getTime();
	}

	public void sair() {
		limpaCampos();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
}


