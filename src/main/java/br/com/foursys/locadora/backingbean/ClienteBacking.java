/**
 * 
 */
package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.controller.CidadeController;
import br.com.foursys.locadora.controller.ClienteController;
import br.com.foursys.locadora.controller.ContatoController;
import br.com.foursys.locadora.controller.EnderecoController;
import br.com.foursys.locadora.controller.EstadoController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Logradouro;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Titulo;
import br.com.foursys.locadora.util.Valida;

/**
 * Classe para controlar as funções e métodos das telas de funcionário
 * 
 * @author Kalleo Leandro dos Santos Leal
 * @since 30/04/2021
 * @version 1.0
 */
@ManagedBean(name = "clienteBacking")
@SessionScoped
public class ClienteBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	// Atributos de funcionário
	private String nomeCliente;

	private String cpf;
	private String rg;
	private Date data;
	private String idade;
	private String sexo;

	// Atributos de estado
	private String logradouro;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String uf;

	private String nomeCidade;
	private String nomeEstado;

	private int cidade;
	private int estado;

	// Atributos de contato
	private String telefone;
	private String celular;
	private String email;

	// Atributos de objeto
	private Cliente cliente;
	private Endereco Objendereco;
	private Contato contato;

	// Atributos de pesquisa
	private String nomePesquisar;
	private Cliente clienteSelecionado;

	// Atributos de teste
	private boolean comboCidade = true;
	private int indexTab;

	// Listas

	private ArrayList<Cliente> listaClientes;

	private ArrayList<Cidade> listaCidade;

	private ArrayList<Estado> listaEstado;

	private ArrayList<String> listaLogradouro;

	// Construtor com funções

	public ClienteBacking() {

		listaClientes = new ArrayList<Cliente>();
		carregarLogradouro();
		carregarEstado();
		limpaCampos();
	}

	// Getters e setters

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public boolean isComboCidade() {
		return comboCidade;
	}

	public void setComboCidade(boolean comboCidade) {
		this.comboCidade = comboCidade;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public int getCidade() {
		return cidade;
	}

	public void setCidade(int cidade) {
		this.cidade = cidade;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getObjendereco() {
		return Objendereco;
	}

	public void setObjendereco(Endereco objendereco) {
		Objendereco = objendereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public String getNomePesquisar() {
		return nomePesquisar;
	}

	public void setNomePesquisar(String nomePesquisar) {
		this.nomePesquisar = nomePesquisar;
	}

	public ArrayList<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(ArrayList<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public ArrayList<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(ArrayList<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public ArrayList<String> getListaLogradouro() {
		return listaLogradouro;
	}

	public void setListaLogradouro(ArrayList<String> listaLogradouro) {
		this.listaLogradouro = listaLogradouro;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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

	/*
	 * Método para cadastrar um cliente na base
	 */

	public void cadastrar() {

		if (validar()) {
			// salvar os dados do cliente na base
			try {
				getContatoCadastro();
				getObjEnderecoCadastro();
				getClienteCadastro();
				new EnderecoController().salvar(Objendereco);
				new ContatoController().salvar(contato);
				cliente.setEnderecoIdEndereco(Objendereco);
				cliente.setContatoIdContato(contato);
				new ClienteController().salvar(cliente);
				limpaCampos();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_CLIENTE, Mensagem.CLIENTE_SALVO);
			} catch (Exception e) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.ERRO_SALVAR_CLIENTE);
			}
		}

	}

	/*
	 * Método para retornar um cliente para cadastro
	 */
	private void getClienteCadastro() {
		cliente = new Cliente();
		cliente.setNome(nomeCliente);
		cliente.setCpf(cpf);
		cliente.setRg(rg);
		cliente.setDataNascimento(getDateToString());
		cliente.setIdade(Integer.parseInt(idade));
		cliente.setSexo(sexo);
		cliente.setEnderecoIdEndereco(Objendereco);
		cliente.setContatoIdContato(contato);
	}

	/*
	 * Método para retornar um endereço para cadastro
	 */
	private void getObjEnderecoCadastro() {
		Objendereco = new Endereco();
		Objendereco.setTipoLogradouro(logradouro);
		Objendereco.setEndereco(endereco);
		Objendereco.setNumero(Integer.parseInt(numero));
		Objendereco.setComplemento(complemento);
		Objendereco.setBairro(bairro);
		Objendereco.setCep(cep);
		Objendereco.setCidadeIdCidade(new Cidade(cidade));
	}

	/*
	 * Método para retornar um contato para cadastro
	 */

	private void getContatoCadastro() {
		contato = new Contato();
		contato.setTelefone(telefone);
		contato.setCelular(celular);
		contato.setEmail(email);
	}

	/*
	 * Método para retornar um cliente para alteração
	 */

	private void getClienteAlterar() {
		cliente = clienteSelecionado;
		getObjEnderecoAlterar();
		getContatoAlterar();
		cliente.setEnderecoIdEndereco(Objendereco);
		cliente.setContatoIdContato(contato);
	}

	/*
	 * Método para retornar um endereço para alteração
	 */

	private void getObjEnderecoAlterar() {
		Objendereco = clienteSelecionado.getEnderecoIdEndereco();
		Objendereco.setTipoLogradouro(logradouro);
		Objendereco.setEndereco(endereco);
		Objendereco.setNumero(Integer.parseInt(numero));
		Objendereco.setComplemento(complemento);
		Objendereco.setBairro(bairro);
		Objendereco.setCep(cep);
		Objendereco.setCidadeIdCidade(new Cidade(cidade));
	}

	/*
	 * Método para retornar um contato para alteração
	 */

	private void getContatoAlterar() {
		contato = clienteSelecionado.getContatoIdContato();
		contato.setTelefone(telefone);
		contato.setCelular(celular);
		contato.setEmail(email);
	}

	/*
	 * Método para controlar a ação do botão cancelar
	 */

	public void cancelar() {
		limpaCampos();
	}

	/*
	 * Método para limpar os campos de preenchimento
	 */

	public void limpaCampos() {
		setNomeCliente(null);
		setCpf(null);
		setRg(null);
		setData(null);
		setIdade(null);
		setSexo(null);
		setDate(null);
		setLogradouro(null);
		setEndereco(null);
		setComplemento(null);
		setCidade(0);
		setEstado(0);
		setComboCidade(false);
		setNumero(null);
		setBairro(null);
		setCep(null);
		setTelefone(null);
		setCelular(null);
		setEmail(null);
		setListaClientes(null);
		setNomePesquisar(null);		
		setIndexTab(0);
		
	}

	/*
	 * Método para pesquisar um funcionário
	 */
	public String pesquisar() {
		try {
			listaClientes = new ClienteController().buscarPorNome(nomePesquisar);
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.ERRO_PESQUISAR_CLIENTE);
		}
		return "";
	}

	/*
	 * Método para carregar a combo de estados
	 */
	public void carregarEstado() {
		listaEstado = new ArrayList<Estado>();
		try {
			listaEstado = new EstadoController().buscarTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Método para carregar a combo de cidades
	 */
	public void carregarCidade() {
		listaCidade = new ArrayList<Cidade>();
		try {
			listaCidade = new CidadeController().buscarPorEstado(new Estado(estado));
			comboCidade = false;
		} catch (Exception e) {
			e.printStackTrace();
			comboCidade = true;
		}
	}

	/*
	 * Método para carregar a combo de logradouros
	 */

	public void carregarLogradouro() {
		listaLogradouro = new ArrayList<String>();
		for (Logradouro l : Logradouro.values()) {
			listaLogradouro.add(l.getDescricao());
		}
	}

	/*
	 * Método para carregar a combo de cidades na tela de alteração
	 */

	public void carregarCidadesAlterar() {
		listaCidade = new ArrayList<Cidade>();
		try {
			listaCidade = new CidadeController().buscarTodos();
			comboCidade = false;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String cadastroCliente() {
		limpaCampos();
		return "";
	}

	/*
	 * Método para redirecionar o objeto cliente para alteração, bem como setar os
	 * valores no objeto
	 * 
	 */

	public void redirecionar(String opcao) throws ParseException
	{
		clienteRedirecionado();
		if(opcao.equals("alterar"))
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("alt-cliente.xhtml");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("detalha-cliente.xhtml");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void clienteRedirecionado() throws ParseException {

		nomeCliente = clienteSelecionado.getNome();
		cpf = clienteSelecionado.getCpf();
		rg = clienteSelecionado.getRg();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		date = new Date(format.parse(clienteSelecionado.getDataNascimento()).getTime());

		idade = clienteSelecionado.getIdade() + "";
		sexo = clienteSelecionado.getSexo();

		carregarEstado();
		carregarCidadesAlterar();

		logradouro = clienteSelecionado.getEnderecoIdEndereco().getTipoLogradouro();
		endereco = clienteSelecionado.getEnderecoIdEndereco().getEndereco();
		numero = clienteSelecionado.getEnderecoIdEndereco().getNumero() + "";
		complemento = clienteSelecionado.getEnderecoIdEndereco().getComplemento();
		bairro = clienteSelecionado.getEnderecoIdEndereco().getBairro();
		cep = clienteSelecionado.getEnderecoIdEndereco().getCep();
		estado = clienteSelecionado.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getIdEstado();
		cidade = clienteSelecionado.getEnderecoIdEndereco().getCidadeIdCidade().getIdCidade();
		telefone = clienteSelecionado.getContatoIdContato().getTelefone();
		celular = clienteSelecionado.getContatoIdContato().getCelular();
		email = clienteSelecionado.getContatoIdContato().getEmail();
	}

	/*
	 * Método para validar os campos de preenchimento
	 */

	public boolean validar() {
		// Dados pessoais
		if (Valida.isEmptyOrNull(nomeCliente)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.nomeVazio);
			return false;
		}
		if (Valida.isEmptyOrNull(cpf)) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.cpfVazio);
			return false;
		} else if (Valida.cpfInvalido(cpf)) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.cpfInvalido);
			return false;
		}
		if (Valida.isEmptyOrNull(rg)) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.rgVazio);
			return false;
		}
		if (date == null) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.dataNascimentoVazia);
			return false;
		} else if (Valida.isEmptyOrNull(idade)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.idadeVazia);
			return false;
		} else if (!Valida.isInteger(idade)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.idadeInvalida);
			return false;
		}
		if (Valida.isEmptyOrNull(sexo)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.sexoVazio);
			return false;
		}
		// Dados de endereço
		if (Valida.isEmptyOrNull(logradouro)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.logradouroVazio);
			return false;
		}
		if (Valida.isEmptyOrNull(endereco)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.enderecoVazio);
			return false;
		}
		if (Valida.isEmptyOrNull(numero)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.numeroVazio);
			return false;
		} else if (!Valida.isInteger(numero)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.numeroInvalido);
			return false;
		}
		if (Valida.isEmptyOrNull(bairro)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.bairroVazio);
			return false;
		}
		if (Valida.isIntZero(cidade)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.cidadeVazio);
			return false;
		}
		if (Valida.isIntZero(estado)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.estadoVazio);
			return false;
		}
		if (Valida.cepVazio(cep)) {
			// exibe uma mensagem de erro
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.cepVazio);
			return false;
		}
		return true;
	}

	/*
	 * Método para controlar a ação do botão alterar
	 */

	public void alterarCliente() {
		if (validar()) {
			// salvar os dados do cliente na base
			try {
				getClienteAlterar();
				new EnderecoController().salvar(Objendereco);
				new ContatoController().salvar(contato);
				limpaCampos();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_CLIENTE, Mensagem.CLIENTE_ALTERADO);
			} catch (Exception e) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.ERRO_ALTERAR_CLIENTE);
			}
		}
	}

	/*
	 * Método para controlar a função do botão excluir
	 */

	public void excluir() {
		try {
			new ClienteController().excluir(clienteSelecionado);
			new EnderecoController().excluir(clienteSelecionado.getEnderecoIdEndereco());
			new ContatoController().excluir(clienteSelecionado.getContatoIdContato());
			pesquisar();
			JSFUtil.addInfoMessage(Titulo.CADASTRO_CLIENTE, Mensagem.CLIENTE_EXCLUIDO);
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_CLIENTE, Mensagem.ERRO_EXCLUIR_CLIENTE);
		}
	}

	/*
	 * Método para controlar a função do botão sair
	 */

	public void sair() {
		limpaCampos();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Método para controlar a função do botão sair
	 */

	public void voltar() {
		limpaCampos();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("list-cliente.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Método para retornar a data formatada conforme o padrão BR
	 */

	private String getDateToString() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
}
