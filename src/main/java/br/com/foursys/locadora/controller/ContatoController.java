package br.com.foursys.locadora.controller;


import br.com.foursys.locadora.bean.Contato;
import br.com.foursys.locadora.dao.ContatoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class ContatoController {
	public void salvar(Contato Contato)
	{
		try
		{
			new ContatoDAO().salvar(Contato);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Contato buscarPorCodigo(int codigo)
    {
        //Lista auxiliar
        Contato retorno = new Contato();
        try
        {
        	retorno = new ContatoDAO().buscarPorCodigo(codigo);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	public void excluir(Contato contato)
	{
		try
		{
			new ContatoDAO().excluir(contato);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
