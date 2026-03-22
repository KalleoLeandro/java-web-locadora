package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Cliente;
import br.com.foursys.locadora.dao.ClienteDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class ClienteController {
	public void salvar(Cliente cliente)
	{
		try
		{
			new ClienteDAO().salvar(cliente);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Cliente> buscarPorNome(String nome)
    {
        //Lista auxiliar
        ArrayList<Cliente> retorno = new ArrayList<Cliente>();
        try
        {
        	retorno = new ClienteDAO().buscarPorNome(nome);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	
	public ArrayList<Cliente> buscarTodos()
    {
        //Lista auxiliar
        ArrayList<Cliente> retorno = new ArrayList<Cliente>();
        try
        {
        	retorno = new ClienteDAO().buscarTodos();        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }	
	
	public Cliente buscarPorCodigo(int codigo)
    {
        //Lista auxiliar
        Cliente retorno = new Cliente();
        try
        {
        	retorno = new ClienteDAO().buscarPorCodigo(codigo);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	
	public void excluir(Cliente cliente)
	{
		try
		{
			new ClienteDAO().excluir(cliente);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
