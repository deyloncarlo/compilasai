package compilador.analiselexica;

public class Mensagem
{
	public static String caracterInvalido(int p_numeroLinha)
	{
		return p_numeroLinha + ": " + "caracter inv�lido";
	}

	public static String lexemaNaoIdentificado(int p_numeroLinha, String p_lexema)
	{
		return p_numeroLinha + ": " + "lexema n�o identificado" + "[" + p_lexema + "]";
	}

	public static String fimArquivoNaoEsperado(int p_nuemroLinha)
	{
		return p_nuemroLinha + ": " + "fim arquivo n�o esperado";
	}

	public static String tokenNaoEsperado(String p_lexema, Integer p_numeroLinhaArquivo)
	{
		return p_numeroLinhaArquivo + ": " + "token n�o esperado [" + p_lexema + "]";
	}

	public static String quebraDeLinhaDentroDeString(Integer p_numeroLinhaArquivo)
	{
		return p_numeroLinhaArquivo + ": " + "quebra de linha dentro de string";
	}
}
