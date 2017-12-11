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

	public static String tokenNaoEsperado(String p_lexema, Integer p_numeroLinhaArquivo)
	{
		return p_numeroLinhaArquivo + ": " + "token não esperado [" + p_lexema + "]";
	}

	public static String quebraDeLinhaDentroDeString(Integer p_numeroLinhaArquivo)
	{
		return p_numeroLinhaArquivo + ": " + "quebra de linha dentro de string";
	}

	public static String identificadorJaDeclarado(int p_numeroLinhaArquivo, String p_lexema)
	{
		return p_numeroLinhaArquivo + ": " + "identificador já declarado " + "[" + p_lexema + "]";
	}

	public static String identificadorNaoDeclarado(int p_numeroLinhaArquivo, String p_lexema)
	{
		return p_numeroLinhaArquivo + ": " + "identificador não declarado " + "[" + p_lexema + "]";
	}

	public static String classeIdentificadorIncompativel(int p_numeroLinhaArquivo, String p_lexema)
	{
		return p_numeroLinhaArquivo + ": " + "classe de identificador incompatível " + "[" + p_lexema + "]";
	}
}
