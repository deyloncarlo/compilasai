package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.Token;

public class Gramatica
{

	/**
	 * M�todo que ir� chamar o m�todo casaToken do AnalisadorSintatico
	 * 
	 * @param p_token
	 * @throws IOException
	 */
	public void casaToken(Token p_token) throws IOException
	{
		AnalisadorSintatico.casaToken(p_token);
	}

	public void DECLARACAO_VARIAVEIS() throws IOException
	{
		TIPO();
		casaToken(Token.IDENTIFICADOR);
		FORMA_DECLARACAO();
		casaToken(Token.PONTO_VIRGULA);
	}

	/**
	 * Identifica como ser� feita a declaracao de veri�vel
	 * 
	 * @throws IOException
	 */
	private void FORMA_DECLARACAO() throws IOException
	{
		if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
		{
			LISTA_ID_DECLARACAO();
		}
		else
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
			{
				casaToken(Token.ADICAO);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
			{
				casaToken(Token.SUBTRACAO);
			}

			casaToken(Token.CONSTANTE);
		}
	}

	/**
	 * Identifica declara��o de lista de vari�veis
	 * 
	 * @throws IOException
	 */
	private void LISTA_ID_DECLARACAO() throws IOException
	{
		casaToken(Token.VIRGULA);
		casaToken(Token.IDENTIFICADOR);
		if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
		{
			LISTA_ID_DECLARACAO();
		}
	}

	private void TIPO() throws IOException
	{
		if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.INT))
		{
			casaToken(Token.INT);
		}
		else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.STRING))
		{
			casaToken(Token.STRING);
		}
		else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BOOLEAN))
		{
			casaToken(Token.BOOLEAN);
		}
		else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BYTE))
		{
			casaToken(Token.BYTE);
		}
	}

}
