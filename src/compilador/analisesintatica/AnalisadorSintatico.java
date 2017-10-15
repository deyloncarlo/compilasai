package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.ErroLexico;
import compilador.analiselexica.Mensagem;
import compilador.analiselexica.Token;

public class AnalisadorSintatico
{

	/**
	 * M�todo que ir� verificar se o lexema lido no c�digo fonte condiz com o
	 * token esperado pela gram�tica.
	 * 
	 * @param p_token
	 * @throws IOException
	 */
	public static void casaToken(Token p_token) throws IOException
	{
		if (AnalisadorLexico.isFimArquivo())
		{
			throw new ErroLexico(Mensagem.fimArquivoNaoEsperado(AnalisadorLexico.getNumeroLinhaArquivo()));
		}
		else if (!p_token.equals(AnalisadorLexico.getRegistroLexico().getToken()))
		{
			throw new ErroLexico(Mensagem.tokenNaoEsperado(AnalisadorLexico.getRegistroLexico().getLexema(),
					AnalisadorLexico.getNumeroLinhaArquivo()));
		}
		else
		{
			AnalisadorLexico.lerProximoLexema();
		}
	}

	/**
	 * M�todo que chama a execu��o da gram�tica
	 * 
	 * @throws IOException
	 */
	public static void executarGramatica() throws IOException
	{
		Gramatica v_gramatica = new Gramatica();
		v_gramatica.executarGramatica();
	}

}
