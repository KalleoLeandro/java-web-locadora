package br.com.foursys.locadora.controller;

import java.util.ArrayList;

import br.com.foursys.locadora.bean.Jogo;
import br.com.foursys.locadora.dao.JogoDAO;

/**
 * Classe respons�vel por acessar o objeto DAO e efetuar altera��es e consulta na base de dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 27/04/2021
 * @version 1.0
 */

public class JogoController {
	public void salvar(Jogo jogo)
	{
		try
		{
			new JogoDAO().salvar(jogo);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Jogo> buscarPorNome(String nome)
    {
        //Lista auxiliar
        ArrayList<Jogo> retorno = new ArrayList<Jogo>();
        try
        {
        	retorno = new JogoDAO().buscarPorNome(nome);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
	}
	
	public ArrayList<Jogo> buscarTodos()
    {
        //Lista auxiliar
        ArrayList<Jogo> retorno = new ArrayList<Jogo>();
        try
        {
        	retorno = new JogoDAO().buscarTodos();        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
	}
	
	public ArrayList<Jogo> buscarDisponiveis(String disponivel)
    {
        //Lista auxiliar
        ArrayList<Jogo> retorno = new ArrayList<Jogo>();
        try
        {
        	retorno = new JogoDAO().buscarDisponiveis(disponivel);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	public ArrayList<Jogo> buscarPorFiltros(String nome, String plataforma, String genero, String disponivel, String promocao)
    {
        ArrayList<Jogo> retorno = new ArrayList<Jogo>();
        try
        {
        	retorno = new JogoDAO().buscarPorFiltros(nome, plataforma, genero, disponivel, promocao);        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	public ArrayList<String> buscarPlataformas()
    {
        ArrayList<String> retorno = new ArrayList<String>();
        try
        {
        	retorno = new JogoDAO().buscarPlataformas();        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return retorno;
    }
	
	public void excluir(Jogo jogo)
	{
		try
		{
			new JogoDAO().excluir(jogo);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

