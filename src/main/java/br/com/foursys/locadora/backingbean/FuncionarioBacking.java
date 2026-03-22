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
import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.bean.Funcionario;
import br.com.foursys.locadora.controller.CidadeController;
import br.com.foursys.locadora.controller.ContatoController;
import br.com.foursys.locadora.controller.EnderecoController;
import br.com.foursys.locadora.controller.EstadoController;
import br.com.foursys.locadora.controller.FuncionarioController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Logradouro;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Perfil;
import br.com.foursys.locadora.util.Titulo;
import br.com.foursys.locadora.util.Valida;

/**
 * Classe para controlar as funções e métodos das telas de funcionário
 * @author Kalleo Leandro dos Santos Leal
 * @since 30/04/2021
 * @version 1.0
 */
@ManagedBean(name = "funcionarioBacking")
@SessionScoped
public class FuncionarioBacking implements Serializable{

	private static final long serialVersionUID = 1L;
	
		private Date date;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");	
	
	// Atributos de funcionário
		private String nomeFuncionario;
		private String cpf;
		private String rg;
		private Date data;	
		private String idade;
		private String sexo;
		private String login;
		private String senha;
		private String perfil;

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
		private Funcionario funcionario;
		private Endereco Objendereco;
		private Contato contato;	

		// Atributos de pesquisa
		private String nomePesquisar;
		private Funcionario funcionarioSelecionado;
		
		//Atributos de teste
		private boolean comboCidade = true;
		private int indexTab;
		

		// Listas

		private ArrayList<Funcionario> listaFuncionarios;

		private ArrayList<Cidade> listaCidade;

		private ArrayList<Estado> listaEstado;

		private ArrayList<String> listaPerfil;

		private ArrayList<String> listaLogradouro;
		
		//Construtor com funções

		public FuncionarioBacking() {
			listaFuncionarios = new ArrayList<Funcionario>();
			carregarPerfil();
			carregarLogradouro();
			carregarEstado();
			limpaCampos();			
		}
		
		//Getters e setters

		public String getNomeFuncionario() {
			return nomeFuncionario;
		}

		public void setNomeFuncionario(String nomeFuncionario) {
			this.nomeFuncionario = nomeFuncionario;
		}

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

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public String getPerfil() {
			return perfil;
		}

		public void setPerfil(String perfil) {
			this.perfil = perfil;
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

		public Funcionario getFuncionario() {
			return funcionario;
		}

		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
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

		public Funcionario getFuncionarioSelecionado() {
			return funcionarioSelecionado;
		}

		public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
			this.funcionarioSelecionado = funcionarioSelecionado;
		}

		public ArrayList<Funcionario> getListaFuncionarios() {
			return listaFuncionarios;
		}

		public void setListaFuncionarios(ArrayList<Funcionario> listaFuncionarios) {
			this.listaFuncionarios = listaFuncionarios;
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

		public ArrayList<String> getListaPerfil() {
			return listaPerfil;
		}

		public void setListaPerfil(ArrayList<String> listaPerfil) {
			this.listaPerfil = listaPerfil;
		}

		public ArrayList<String> getListaLogradouro() {
			return listaLogradouro;
		}

		public void setListaLogradouro(ArrayList<String> listaLogradouro) {
			this.listaLogradouro = listaLogradouro;
		}
		
		public void carregarPerfil() {
			listaPerfil = new ArrayList<String>();
			for (Perfil p : Perfil.values()) {
				listaPerfil.add(p.getDescricao());
			}
		}
		
		/*
		 * Método para cadastrar um funcionário na base 
		 * */
		
		public void cadastrar(){
			
			if (validar()) {
					// salvar os dados do funcionario na base
					try {
						getContatoCadastro();						
						getObjEnderecoCadastro();						
						getFuncionarioCadastro();						
					 	new EnderecoController().salvar(Objendereco);
				        new ContatoController().salvar(contato);						
						funcionario.setEnderecoIdEndereco(Objendereco);
						funcionario.setContatoIdContato(contato);						
					    new FuncionarioController().salvar(funcionario);
						limpaCampos();
						JSFUtil.addInfoMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.FUNCIONARIO_SALVO);
					} catch (Exception e) {
						JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.ERRO_SALVAR_FUNCIONARIO);
					}
				}
				
			}
		
		/*
		 * Método para retornar um funcionário para cadastro 
		 * */
		private void getFuncionarioCadastro() {
			funcionario = new Funcionario();
			funcionario.setNome(nomeFuncionario);
			funcionario.setCpf(cpf);				
			funcionario.setRg(rg);					
			funcionario.setDataNascimento(getDateToString());
			funcionario.setIdade(Integer.parseInt(idade));
			funcionario.setSexo(sexo);
			funcionario.setLogin(login);
			funcionario.setSenha(senha);
			funcionario.setPerfilAcesso(perfil);
			funcionario.setEnderecoIdEndereco(Objendereco);
			funcionario.setContatoIdContato(contato);			
		}


		/*
		 * Método para retornar um endereço para cadastro 
		 * */
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
		 * */

		private void getContatoCadastro() {
			contato = new Contato();
			contato.setTelefone(telefone);
			contato.setCelular(celular);
			contato.setEmail(email);			
		}
		

		/*
		 * Método para retornar um funcionário para alteração 
		 * */
		
		private void getFuncionarioAlterar() {
			funcionario = funcionarioSelecionado;			
			getObjEnderecoAlterar();
			getContatoAlterar();
			funcionario.setEnderecoIdEndereco(Objendereco);
			funcionario.setContatoIdContato(contato);			
		}
		

		/*
		 * Método para retornar um endereço para alteração 
		 * */

		private void getObjEnderecoAlterar() {
			Objendereco = funcionarioSelecionado.getEnderecoIdEndereco();
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
		 * */

		private void getContatoAlterar() {
			contato = funcionarioSelecionado.getContatoIdContato();
			contato.setTelefone(telefone);
			contato.setCelular(celular);
			contato.setEmail(email);
		}
		
		/*
		 * Método para controlar a ação do botão cancelar
		 * */
		
		public void cancelar() {
			limpaCampos();
		}
		

		/*
		 * Método para limpar os campos de preenchimento 
		 * */
		
		public void limpaCampos() {
			setNomeFuncionario(null);
			setCpf(null);
			setRg(null);
			setData(null);
			setIdade(null);
			setSexo(null);
			setLogin(null);
			setSenha(null);
			setDate(null);
			setPerfil(null);
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
			listaFuncionarios.clear();
			setNomePesquisar(null);
			setIndexTab(0);			
		}
		
		/*
		 * Método para pesquisar um funcionário
		 * */
		public String pesquisar()
		{
			try
			{
				listaFuncionarios = new FuncionarioController().buscarPorNome(nomePesquisar);
				if(LoginBacking.funcionarioLogado.getPerfilAcesso().equals("Administrador"))
				for(Funcionario f: listaFuncionarios)
				{
					if(f.getPerfilAcesso().equals("Desenvolvedor"))
					{
						listaFuncionarios.remove(f);
					}
				}
			}
			catch(Exception e)
			{
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.ERRO_PESQUISAR_FUNCIONARIO);
			}
			return "";
		}
		

		/*
		 * Método para carregar a combo de estados 
		 * */
		public void carregarEstado() {
			listaEstado=  new ArrayList<Estado>();
			try {
				listaEstado = new EstadoController().buscarTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		 * Método para carregar a combo de cidades 
		 * */
		public void carregarCidade()
		{
			listaCidade = new ArrayList<Cidade>();
			try
			{
				listaCidade = new CidadeController().buscarPorEstado(new Estado(estado));				
				comboCidade = false;
			}catch (Exception e) {
				e.printStackTrace();
				comboCidade = true;				
			}
		}
		
		/*
		 * Método para carregar a combo de logradouros 
		 * */
		
		public void carregarLogradouro() {
			listaLogradouro = new ArrayList<String>();
			for (Logradouro l : Logradouro.values()) {
				listaLogradouro.add(l.getDescricao());
			}
		}
		
		/*
		 * Método para carregar a combo de cidades na tela de alteração 
		 * */
		
		public void carregarCidadesAlterar()
		{
			listaCidade = new ArrayList<Cidade>();
			try {
				listaCidade = new CidadeController().buscarTodos();
				comboCidade = false;				
			} catch (Exception e) {
				// TODO: handle exception				
			}
		}		
		
		public String cadastroFuncionario(){
			limpaCampos();
			return "";
		}	
		
		/*
		 * Método para redirecionar o objeto funcionário para alteração, bem como setar os valores no objeto
		 * 
		 * */
		
		public void redirecionar(String opcao) throws ParseException
		{
			funcionarioRedirecionado();
			if(opcao.equals("alterar"))
			{
				try
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect("alt-funcionario.xhtml");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect("detalha-funcionario.xhtml");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
		/*
		 * Método para validar os campos de preenchimento
		 * */
		
		public void funcionarioRedirecionado() throws ParseException
		{
			nomeFuncionario = funcionarioSelecionado.getNome();
			cpf = funcionarioSelecionado.getCpf();
			rg = funcionarioSelecionado.getRg();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			date = new Date(format.parse(funcionarioSelecionado.getDataNascimento()).getTime());
			
			idade = funcionarioSelecionado.getIdade()+"";
			sexo = funcionarioSelecionado.getSexo();
			login = funcionarioSelecionado.getLogin();
			senha = funcionarioSelecionado.getSenha();
			perfil = funcionarioSelecionado.getPerfilAcesso();
			
			carregarEstado();
			carregarCidadesAlterar();
			
			logradouro = funcionarioSelecionado.getEnderecoIdEndereco().getTipoLogradouro();
			endereco = funcionarioSelecionado.getEnderecoIdEndereco().getEndereco();
			numero = funcionarioSelecionado.getEnderecoIdEndereco().getNumero()+"";
			complemento = funcionarioSelecionado.getEnderecoIdEndereco().getComplemento();
			bairro = funcionarioSelecionado.getEnderecoIdEndereco().getBairro();
			cep = funcionarioSelecionado.getEnderecoIdEndereco().getCep();
			estado = funcionarioSelecionado.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getIdEstado();
			cidade = funcionarioSelecionado.getEnderecoIdEndereco().getCidadeIdCidade().getIdCidade();
			telefone = funcionarioSelecionado.getContatoIdContato().getTelefone();
			celular = funcionarioSelecionado.getContatoIdContato().getCelular();
			email = funcionarioSelecionado.getContatoIdContato().getEmail();
		}
		
		public boolean validar() {
			// Dados pessoais
			if (Valida.isEmptyOrNull(nomeFuncionario)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.nomeVazio);
				return false;
			}
			System.out.println(cpf);
			if (Valida.isEmptyOrNull(cpf)) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.cpfVazio);
				return false;
			}			
			else if(Valida.cpfInvalido(cpf))
			{
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.cpfInvalido);
				return false;
			}
			if (Valida.isEmptyOrNull(rg)) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.rgVazio);
				return false;
			}
			if (date==null) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.dataNascimentoVazia);
				return false;
			} else if (Valida.isEmptyOrNull(idade)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.idadeVazia);
				return false;
			} else if (!Valida.isInteger(idade)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.idadeInvalida);
				return false;
			}
			if (Valida.isEmptyOrNull(sexo)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.sexoVazio);
				return false;
			}
			// Dados de endereço
			if (Valida.isEmptyOrNull(logradouro)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.logradouroVazio);
				return false;
			}
			if (Valida.isEmptyOrNull(endereco)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.enderecoVazio);
				return false;
			}
			if (Valida.isEmptyOrNull(numero)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.numeroVazio);
				return false;
			} else if (!Valida.isInteger(numero)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.numeroInvalido);
				return false;
			}
			if (Valida.isEmptyOrNull(bairro)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.bairroVazio);
				return false;
			}
			if (Valida.isIntZero(cidade)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.cidadeVazio);
				return false;
			}
			if (Valida.isIntZero(estado)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.estadoVazio);
				return false;
			}
			if (Valida.cepVazio(cep)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.cepVazio);
				return false;
			}
			
			//Dados de contato
			if (Valida.isEmptyOrNull(telefone)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.telefonevazio);
				return false;
			}
			if (Valida.isEmptyOrNull(celular)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.celularVazio);
				return false;
			}
			if (Valida.isEmptyOrNull(email)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.emailVazio);
				return false;
			}			
			// Dados de login
			if (Valida.isEmptyOrNull(perfil)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.perfilAcessoVazio);
				return false;
			}
			if (Valida.isEmptyOrNull(login)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.loginVazio);
				return false;
			}
			if (Valida.isEmptyOrNull(senha)) {
				// exibe uma mensagem de erro
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.senhaVazio);
				return false;
			}			
			return true;
		}
		
		/*
		 * Método para controlar a ação do botão alterar 
		 * */
		
		public void alterarFuncionario() 
		{
			if (validar()) {
				// salvar os dados do funcionario na base
				try {										
					getFuncionarioAlterar();						
				 	new EnderecoController().salvar(Objendereco);
			        new ContatoController().salvar(contato);				    
					limpaCampos();
					JSFUtil.addInfoMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.FUNCIONARIO_ALTERADO);
				} catch (Exception e) {
					JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.ERRO_ALTERAR_FUNCIONARIO);
				}
			}
		}
		
		/*
		 * Método para controlar a função do botão excluir
		 * */
		
		public void excluir()
		{
			try
			{
				new FuncionarioController().excluir(funcionarioSelecionado);
				new EnderecoController().excluir(funcionarioSelecionado.getEnderecoIdEndereco());
				new ContatoController().excluir(funcionarioSelecionado.getContatoIdContato());
				pesquisar();
				JSFUtil.addInfoMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.FUNCIONARIO_EXCLUIDO);
			}catch (Exception e) {
				JSFUtil.addErrorMessage(Titulo.CADASTRO_FUNCIONARIO, Mensagem.ERRO_EXCLUIR_FUNCIONARIO);
			}
		}
		
		/*
		 * Método para controlar a função do botão sair
		 * */
		
		public void sair() {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * Método para retornar a data formatada conforme o padrão BR
		 * */
		
		private String getDateToString()
		{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(date);
		}
}
