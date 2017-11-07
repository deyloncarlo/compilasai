package compilador.teste;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.TabelaSimbolos;
import compilador.analiselexica.Token;
import compilador.analisesintatica.AnalisadorSintatico;

public class TesteCompilasai
{

	/**
	 * Deylon Carlo Fidelis Couto, Laura Ester
	 * 
	 */
	public static void main(String[] p_args)
	{
		try
		{
			if (p_args.length > 0)
			{
				if (p_args[0] != null)
				{
					startCompilaSai(p_args[0]);
				}
				else
				{
					throw new Error("Código fonte não identificado.");
				}
			}
			else
			{
				throw new Error("Código fonte não identificado.");
			}
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

		// TabelaSimbolos.exibeRegistros();
	}

	public static void lendoArquivoCodigoFonte(String p_nomeArquivo) throws IOException
	{
		insercaoPalavrasReservadas();
		AnalisadorLexico.abrirArquivo(p_nomeArquivo);
		AnalisadorLexico.lerArquivo();
	}

	public static void startCompilaSai(String p_nomeArquivo) throws IOException
	{
		insercaoPalavrasReservadas();
		AnalisadorLexico.abrirArquivo(p_nomeArquivo);
		AnalisadorLexico.iniciarRegistroLexico();
		AnalisadorLexico.lerProximoLexema();
		AnalisadorSintatico.executarGramatica();
	}
}
