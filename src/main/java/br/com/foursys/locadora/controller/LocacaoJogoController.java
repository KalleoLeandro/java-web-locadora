package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.bean.LocacaoJogo;
import br.com.foursys.locadora.dao.LocacaoJogoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class LocacaoJogoController {
	public void salvar(LocacaoJogo locacaoJogo)
	{
		try
		{
			new LocacaoJogoDAO().salvar(locacaoJogo);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<LocacaoJogo> buscarPorLocacao(Locacao locacao)
    {
        //Lista auxiliar
        ArrayList<LocacaoJogo> retorno = new ArrayList<LocacaoJogo>();
        try
        {
        	retorno = new LocacaoJogoDAO().buscarPorLocacao(locacao); 	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
	}
}

