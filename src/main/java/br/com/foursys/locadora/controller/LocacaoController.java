package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.dao.LocacaoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class LocacaoController {
	public void salvar(Locacao locacao)
	{
		try
		{
			new LocacaoDAO().salvar(locacao);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Locacao> buscarDevolvido(String devolvido)
    {
        //Lista auxiliar
        ArrayList<Locacao> retorno = new ArrayList<Locacao>();
        try
        {
        	retorno = new LocacaoDAO().buscarDevolvido(devolvido); 	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
	}

	public ArrayList<Locacao> buscarTodos()
    {
        //Lista auxiliar
        ArrayList<Locacao> retorno = new ArrayList<Locacao>();
        try
        {
        	retorno = new LocacaoDAO().buscarTodos(); 	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
	}
}
