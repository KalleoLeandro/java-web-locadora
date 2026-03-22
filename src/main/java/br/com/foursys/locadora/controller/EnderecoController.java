package br.com.foursys.locadora.controller;


import br.com.foursys.locadora.bean.Endereco;
import br.com.foursys.locadora.dao.EnderecoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class EnderecoController {
	public void salvar(Endereco endereco)
	{
		try
		{
			new EnderecoDAO().salvar(endereco);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Endereco buscarPorCodigo(int codigo)
    {
        //Lista auxiliar
        Endereco retorno = new Endereco();
        try
        {
        	retorno = new EnderecoDAO().buscarPorCodigo(codigo);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	public void excluir(Endereco endereco)
	{
		try
		{
			new EnderecoDAO().excluir(endereco);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
