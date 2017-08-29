package compilador.analiselexica;

public class Main
{

	public static void main(String[] args)
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

}
