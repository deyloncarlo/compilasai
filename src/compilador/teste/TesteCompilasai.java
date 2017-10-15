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
	public static void main(String[] args)
	{
		try
		{
			startCompilaSai();
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
		insercaoPalavrasReservadas();
		AnalisadorLexico.abrirArquivo();
		AnalisadorLexico.lerArquivo();
	}

	public static void startCompilaSai() throws IOException
	{
		insercaoPalavrasReservadas();
		AnalisadorLexico.abrirArquivo();
		AnalisadorLexico.iniciarRegistroLexico();
		AnalisadorLexico.lerProximoLexema();
		AnalisadorSintatico.executarGramatica();
	}
}
