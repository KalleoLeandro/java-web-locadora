package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.foursys.locadora.bean.Funcionario;
import br.com.foursys.locadora.controller.FuncionarioController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Perfil;
import br.com.foursys.locadora.util.Valida;

@ManagedBean(name = "loginBacking")
@SessionScoped
public class LoginBacking implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;
	
	private boolean user;
	private boolean dev;
	
		
	public static Funcionario funcionarioLogado;
	
	

	public Funcionario getFuncionarioLogado() {
		return funcionarioLogado;
	}

	public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
		LoginBacking.funcionarioLogado = funcionarioLogado;
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

	public boolean isUser() {
		return user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}
	
	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public void efetuarLogin() {
		if (validar()) {
			if (new FuncionarioController().buscarPorLogin(login) != null) {
				try {
					ArrayList<Funcionario> funcionarios = new FuncionarioController().buscarPorLogin(login);
					//Verificar e redirecionar caso exista login
					if(validaDados(funcionarios))
					{						
						try {
							FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
						} catch (IOException e) {							
							e.printStackTrace();
						}
					}
				} catch (Exception e) {

				}

			} else {
				JSFUtil.addErrorMessage("Login", "Login ou senha inválidos!");
			}
		}
	}	

	private boolean validar() {
		if (Valida.isEmptyOrNull(login)) {
			JSFUtil.addErrorMessage("Login", "Credenciais inválidas!");
			return false;
		}
		if (Valida.isEmptyOrNull(senha)) {
			JSFUtil.addErrorMessage("Login", "Credenciais inválidas!");
			return false;
		}
		return true;
	}
	
	private boolean validaDados(ArrayList<Funcionario> func) 
	{
		for(Funcionario funcionario : func)
		{
			if(funcionario.getSenha().equals(senha))
			{
				funcionarioLogado = funcionario;
				if(funcionario.getPerfilAcesso().equals(Perfil.USER.getDescricao()))
				{
					user = true;
				}
				else
				{
					user = false;
				}
				if(funcionario.getPerfilAcesso().equals(Perfil.DEV.getDescricao()))
				{
					dev = true;
				}
				else
				{
					dev = false;
				}
					
				return true;
			}
		}
		return false;
	}

}
