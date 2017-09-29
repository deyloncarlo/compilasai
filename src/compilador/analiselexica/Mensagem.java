package compilador.analiselexica;

public class Mensagem
{
	public static String caracterInvalido(int p_numeroLinha)
	{
		return p_numeroLinha + ": " + "caracter inválido";
	}

	public static String lexemaNaoIdentificado(int p_numeroLinha, String p_lexema)
	{
		return p_numeroLinha + ": " + "lexema não identificado" + "[" + p_lexema + "]";
	}

	public static String fimArquivoNaoEsperado(int p_nuemroLinha)
	{
		return p_nuemroLinha + ": " + "fim arquivo não esperado";
	}
}
