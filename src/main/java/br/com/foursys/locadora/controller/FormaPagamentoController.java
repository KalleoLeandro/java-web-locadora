package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.FormaPagamento;
import br.com.foursys.locadora.dao.FormaPagamentoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class FormaPagamentoController {
	
	
	public ArrayList<FormaPagamento> buscarTodos()
    {
        //Lista auxiliar
        ArrayList<FormaPagamento> retorno = new ArrayList<FormaPagamento>();
        try
        {
        	retorno = new FormaPagamentoDAO().buscarTodos();        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }	
	
	
	
}
