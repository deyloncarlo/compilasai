package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.ErroLexico;
import compilador.analiselexica.Mensagem;
import compilador.analiselexica.Token;

public class AnalisadorSintatico
{

	/**
	 * Método que irá verificar se o lexema lido no código fonte condiz com o
	 * token esperado pela gramática.
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
	 * Método que chama a execução da gramática
	 * 
	 * @throws IOException
	 */
	public static void executarGramatica() throws IOException
	{
		Gramatica v_gramatica = new Gramatica();
		v_gramatica.executarGramatica();
	}

}
