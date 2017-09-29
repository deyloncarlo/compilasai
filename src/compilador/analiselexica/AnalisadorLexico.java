package compilador.analiselexica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico
{
	private static RegistroLexico registroLexico = new RegistroLexico();

	/** caminho do arquivo de c�digo fonte */
	private static String nomeArquivo = "exemplo10.L";

	/** Leitor respons�vel por ler o arquivo fonte */
	private static BufferedReader leitor;

	/** Boolean que indica se j� � fim de arquivo */
	private static boolean fimArquivo;

	private static Integer numeroLinhaArquivo = 1;

	private static final String operadoresOuComparadores = "=!()<>+-*/;";

	private static final String padraoFormacaoString = "[\"][\\w]*[\"]";

	private static final String padraoFormacaoInt = "[0]|[1-9][0-9]*";

	private static final String padraoFormacaoByte = "([0]|[1-9][0-9]{0,2})|0x([1-9A-F][0-9A-F]{0,1}|[0])";

	private static final String padraoFormacaoIdentificador = "[_|a-zA-Z][_|0-9a-zA-Z]*";

	public void getProximoToken()
	{

	}

	/**
	 * M�todo que l� caracter por caracter e identifica a ocorr�ncia de um
	 * lexema
	 * 
	 * @throws IOException
	 */
	public static void lerProximoLexema() throws IOException
	{
		String v_lexema = ""; // Lexema
		Boolean podeContinuarLendo = true;
		char v_char; // Cada caracter lido do arquivo
		int v_code; // Codigo em ASCII dos caracteres lidos
		int v_codigoEspaco = 32;
		int v_codigoEnter = 13;
		int v_codigoLinhaNova = 10;
		int v_codigoTab = 9;
		v_code = leitor.read();
		v_char = (char) v_code;

		while (v_code != v_codigoEspaco && v_code != v_codigoTab && v_code != v_codigoEnter
				&& v_code != v_codigoLinhaNova && v_code != -1 && podeContinuarLendo != null && podeContinuarLendo)
		{
			v_char = (char) v_code;
			podeContinuarLendo = identificarLexema(v_char, v_lexema);
			if (podeContinuarLendo != null && podeContinuarLendo)
			{
				leitor.mark(0);
				v_lexema += v_char;
				v_code = leitor.read();
			}
		}

		if (v_code == v_codigoLinhaNova)
		{
			numeroLinhaArquivo++;
		}

		// Quer dizer que estava lendo um coment�rio
		if (podeContinuarLendo == null)
		{
			v_lexema = "";
		}

		if (v_code == -1)
		{
			setFimArquivo(true);
		}

		if (!v_lexema.isEmpty())
		{
			System.out.println(v_lexema);
			identificarLexema(v_lexema);
		}
	}

	/**
	 * M�todo que identifica qual o lexema preenche o registro l�ximo
	 * 
	 * @param v_lexema
	 */
	private static void identificarLexema(String v_lexema)
	{
		if (v_lexema.matches(padraoFormacaoString))
		{
			preencherRegistroLeximo(v_lexema, Tipo.STRING, Token.CONSTANTE, null);
		}
		else if (v_lexema.matches(padraoFormacaoInt))
		{
			preencherRegistroLeximo(v_lexema, Tipo.INT, Token.CONSTANTE, null);
		}
		else if (v_lexema.matches(padraoFormacaoByte))
		{
			preencherRegistroLeximo(v_lexema, Tipo.BYTE, Token.CONSTANTE, null);
		}
		else
		{
			RegistroTabelaSimbolo v_resitroTabelaSimbolo = TabelaSimbolos.pesquisarRegistro(v_lexema);
			if ((v_resitroTabelaSimbolo == null && v_lexema.matches(padraoFormacaoIdentificador))
					|| v_resitroTabelaSimbolo != null)
			{
				if (v_resitroTabelaSimbolo == null)
				{
					v_resitroTabelaSimbolo = TabelaSimbolos.insereNovoRegistro(Token.IDENTIFICADOR, v_lexema);
				}
				preencherRegistroLeximo(v_lexema, null, v_resitroTabelaSimbolo.getToken(), v_resitroTabelaSimbolo);
			}
			else
			{
				throw new ErroLexico(v_lexema, numeroLinhaArquivo);
			}
		}
	}

	public static Boolean identificarLexema(char p_char, String p_lexema) throws IOException
	{
		if (p_lexema.equals("/") && p_char == '*')
		{
			return lerConteudoComentario();
		}

		if (p_lexema.length() == 1 && p_lexema.matches(";|,|[(]|[)]|[+]|[*]|/|-|[{]|[}]"))
		{
			leitor.reset();
			return false;
		}
		else if (p_lexema.length() > 0 && (p_char == ';' || p_char == ',' || p_char == '(' || p_char == ')'
				|| p_char == '+' || p_char == '*' || p_char == '/' || p_char == '-' || p_char == '{' || p_char == '}'
				|| p_char == '>' || p_char == '<' || p_char == '!'))
		{
			leitor.reset();
			return false;
		}
		else if ((p_lexema.equals(">") || p_lexema.equals("<") || p_lexema.equals("!") || p_lexema.equals("="))
				&& p_char != '=')
		{
			leitor.reset();
			return false;
		}

		if (p_lexema.length() > 0 & p_char == '=')
		{
			if (!contemAlgumOperador(p_lexema) || p_lexema.length() > 1)
			{
				leitor.reset();
				return false;
			}
		}

		if (p_lexema.length() > 1 && contemAlgumOperador(p_lexema))
		{
			leitor.reset();
			return false;
		}

		return true;
	}

	/**
	 * M�todo que l� todo o conte�do dentro de um coment�rio
	 */
	private static Boolean lerConteudoComentario() throws IOException
	{
		String v_fimComentario = "";
		boolean v_continuar = true;
		int v_code = leitor.read();
		v_fimComentario += (char) v_code;

		while (v_continuar && v_code != -1)
		{
			v_code = leitor.read();
			v_fimComentario += (char) v_code;
			if (v_fimComentario.length() > 1 && v_fimComentario.substring(v_fimComentario.length() - 2).equals("*/"))
			{
				v_continuar = false;
			}
		}
		return null;
	}

	/**
	 * M�todo que preenche o registro l�xicom de acordo com o lexema lido
	 * 
	 * @param p_lexema
	 * @param p_tipo
	 * @param p_token
	 * @param p_registroTabelaSimbolo
	 */
	public static void preencherRegistroLeximo(String p_lexema, Tipo p_tipo, Token p_token,
			RegistroTabelaSimbolo p_registroTabelaSimbolo)
	{
		getRegistroLexico().setLexema(p_lexema);
		getRegistroLexico().setTipo(p_tipo);
		getRegistroLexico().setToken(Token.CONSTANTE);
		if (p_registroTabelaSimbolo != null)
		{
			getRegistroLexico().setRegistroTabelaSimbolo(p_registroTabelaSimbolo);
		}
	}

	public static boolean contemAlgumOperador(String p_lexema)
	{
		String v_stringChar;
		if (!p_lexema.isEmpty())
		{
			for (int indice = 0; indice < operadoresOuComparadores.length(); indice++)
			{
				v_stringChar = operadoresOuComparadores.charAt(indice) + "";
				if (p_lexema.contains(v_stringChar))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static void lerArquivo() throws IOException
	{
		registroLexico = new RegistroLexico();
		while (!isFimArquivo())
		{
			lerProximoLexema();
		}
	}

	public static void abrirArquivo()
	{
		try
		{
			leitor = new BufferedReader(new FileReader(nomeArquivo));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static RegistroLexico getRegistroLexico()
	{
		return registroLexico;
	}

	public static void setRegistroLexico(RegistroLexico registroLexico)
	{
		AnalisadorLexico.registroLexico = registroLexico;
	}

	public static String getNomeArquivo()
	{
		return nomeArquivo;
	}

	public static void setNomeArquivo(String nomeArquivo)
	{
		AnalisadorLexico.nomeArquivo = nomeArquivo;
	}

	public static BufferedReader getLeitor()
	{
		return leitor;
	}

	public static void setLeitor(BufferedReader leitor)
	{
		AnalisadorLexico.leitor = leitor;
	}

	public static boolean isFimArquivo()
	{
		return fimArquivo;
	}

	public static void setFimArquivo(boolean fimArquivo)
	{
		AnalisadorLexico.fimArquivo = fimArquivo;
	}

}
