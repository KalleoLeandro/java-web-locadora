
package br.com.foursys.locadora.util;

/**
 * ENUM responsável por armazenar os tipos de perfil
 * @author Kalleo Leandro dos Santos Leal
 * @since 29/04/2021
 * @version 1.0
 */

public enum Perfil {
	ADM("Administrador"),
	DEV("Desenvolvedor"),
	USER("Usuario");
	
	private String descricao;
	
	Perfil(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
