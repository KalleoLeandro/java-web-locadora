/**
 * 
 */
package br.com.foursys.locadora.util;

/**
 * ENUM responsável por armazenar as formas de pagamento
 * @author Kalleo Leandro dos Santos Leal
 * @since 05/05/2021
 * @version 1.0
 */
public enum FormaDePagamento {

	VISTA("Á Vista"),
	CREDITO("Crédito"),
	DEBITO("Débito"),
	CHEQUE("Cheque"),
	PIX("Pix"),
	BOLETO("Boleto");
	
	private String descricao;
	
	FormaDePagamento(String descricao)
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
