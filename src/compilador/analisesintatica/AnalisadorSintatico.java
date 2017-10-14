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
		if (!p_token.equals(AnalisadorLexico.getRegistroLexico().getToken()))
		{
			throw new ErroLexico(Mensagem.tokenNaoEsperado(AnalisadorLexico.getRegistroLexico().getLexema(),
					AnalisadorLexico.getNumeroLinhaArquivo()));
		}
		else
		{
			AnalisadorLexico.lerProximoLexema();
		}
	}
}
