package compilador.teste;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.TabelaSimbolos;
import compilador.analiselexica.Token;

public class TesteCompilasai
{

	public static void main(String[] args)
	{
		try
		{
			lendoArquivoCodigoFonte();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Método que insere as palavras reservadas na Tabela de Simbolos
	 */
	public static void insercaoPalavrasReservadas()
	{
		Token[] v_vetorTokens = Token.values();
		for (int indice = 0; indice < v_vetorTokens.length; indice++)
		{
			if (!v_vetorTokens[indice].getLexema().equals(""))
			{
				TabelaSimbolos.insereNovoRegistro(v_vetorTokens[indice], v_vetorTokens[indice].getLexema());
			}
		}

		TabelaSimbolos.exibeRegistros();
	}

	public static void lendoArquivoCodigoFonte() throws IOException
	{
		AnalisadorLexico.abrirArquivo();
		AnalisadorLexico.lerArquivo();
	}
}
