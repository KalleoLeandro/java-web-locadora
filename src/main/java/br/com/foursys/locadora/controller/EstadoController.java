package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Estado;
import br.com.foursys.locadora.dao.EstadoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class EstadoController {
	public ArrayList<Estado> buscarTodos()
	{
		ArrayList<Estado> retorno = new ArrayList<Estado>();
		try
		{
			retorno = new EstadoDAO().buscarTodos();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return retorno;
	}
}
