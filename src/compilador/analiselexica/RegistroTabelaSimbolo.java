/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.analiselexica;

public class RegistroTabelaSimbolo
{

	private Token token;

	private String lexema;

	public RegistroTabelaSimbolo(Token p_token, String p_lexama)
	{
		setToken(p_token);
		setLexema(p_lexama);
	}

	public Token getToken()
	{
		return this.token;
	}

	public void setToken(Token token)
	{
		this.token = token;
	}

	public String getLexema()
	{
		return this.lexema;
	}

	public void setLexema(String lexema)
	{
		this.lexema = lexema;
	}

}
