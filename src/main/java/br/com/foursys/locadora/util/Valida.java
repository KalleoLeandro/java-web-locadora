/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.foursys.locadora.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.caelum.stella.validation.CPFValidator;

/**
 * Classe responsável por armazenar os métodos de validação dos dados
 * @author Kalleo Leandro dos Santos Leal
 * @since 24/04/2021
 * @version 1.0
 */
public class Valida 
{

	
	public static boolean cpfInvalido(String args)
	{
		CPFValidator validador = new CPFValidator();
		try
		{
			args = args.replace(".", "").replace("-", "");
			validador.assertValid(args);
			return false;
		}
		catch(Exception e)
		{
			return true;
		}
	}	
	public static boolean cpfVazio(String args)
	{
		return args.equals("   .   .   -  ");
	}
	
	public static boolean rgVazio(String args)
	{
		return args.equals("  .   .   - ");
	}
    public static boolean isEmptyOrNull(String args)
    {
        return args.trim().equals("") || args == null;
    }
    public static boolean cepVazio(String args)
    {
    	return args.equals("     -   ");
    }
    

    public static boolean isInteger(String args)
    {
        try
        {
            Integer.parseInt(args);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }   
    }
    
    public static boolean isDateNull(Date args)
    {
    	return args ==null;
    }
    

    public static boolean isDouble(String args)
    {
        try
        {
            Double.parseDouble(args);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }   
    }
    

    public static boolean isDoubleZero(double num)
    {
        if(num<=0)
        {
        	return true;
        }
        return false;
    }
    
    public static boolean isIntZero(int num)
    {
        if(num<=0)
        {
        	return true;
        }
        return false;
    }    
    
    
    
    
    
}