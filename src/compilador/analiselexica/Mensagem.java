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
}
