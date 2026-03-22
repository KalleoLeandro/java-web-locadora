package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cidade;
import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.dao.CidadeDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class CidadeController {
	
	public ArrayList<Cidade> buscarPorEstado(Estado estado)
	{
		ArrayList<Cidade> retorno = new ArrayList<Cidade>();
		try {
			retorno = new CidadeDAO().buscarPorEstado(estado);
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return retorno;
	}
	
	
	
	public ArrayList<Cidade> buscarTodos()
    {
		ArrayList<Cidade> retorno = new ArrayList<Cidade>();        
        try
        {
        	retorno = new CidadeDAO().buscarTodos();        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }	
}
