package compilador.analiselexica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico
{
	private static RegistroLexico registroLexico;

	/** caminho do arquivo de código fonte */
	private static String nomeArquivo = "codigo.txt";

	/** Leitor responsável por ler o arquivo fonte */
	private static BufferedReader leitor;

	/** Boolean que indica se já é fim de arquivo */
	private static boolean fimArquivo;

	private static final String operadoresOuComparadores = "=!()<>+-*/;";

	private static final String padraoFormacaoString = "[\"][\\w]*[\"]";

	private static final String padraoFormacaoInt = "[0]|[1-9][0-9]*";

	private static final String padraoFormacaoByte = "([0]|[1-9][0-9]{0,2})|0x([1-9A-F][0-9A-F]{0,1}|[0])";

	public void getProximoToken()
	{

	}

	/**
	 * Método que lê caracter por caracter e identifica a ocorrência de um
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
		v_code = leitor.read();
		v_char = (char) v_code;

		while (v_code != v_codigoEspaco && v_code != v_codigoEnter && v_code != v_codigoLinhaNova && v_code != -1
				&& podeContinuarLendo != null && podeContinuarLendo)
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

		// Quer dizer que estava lendo um comentário
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
		}

		if (v_lexema.matches(padraoFormacaoString))
		{
			System.out.println("é uma string");
		}
		if (v_lexema.matches(padraoFormacaoInt))
		{
			System.out.println("é um int");
		}
		if (v_lexema.matches(padraoFormacaoByte))
		{
			System.out.println("é um byte");
		}
	}

	public static Boolean identificarLexema(char p_char, String p_lexema) throws IOException
	{
		if (p_lexema.equals("/") && p_char == '*')
		{
			return lerConteudoComentario();
		}

		if (p_lexema.length() == 1 && p_lexema.matches(";|[(]|[)]|[+]|[*]|/|-|[{]|[}]"))
		{
			leitor.reset();
			return false;
		}
		else if (p_lexema.length() > 0 && (p_char == ';' || p_char == '(' || p_char == ')' || p_char == '+'
				|| p_char == '*' || p_char == '/' || p_char == '-' || p_char == '{' || p_char == '}' || p_char == '>'
				|| p_char == '<' || p_char == '!'))
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
	 * Método que lê todo o conteúdo dentro de um comentário
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

	/**
	 * Representa um registro lido no código-fonte
	 * 
	 * @author Deylon
	 *
	 */
	private class RegistroLexico
	{
		private Token token;
		private String lexema;
		private RegistroTabelaSimbolo registroTabelaSimbolo;

		public RegistroLexico(Token token, String lexema, RegistroTabelaSimbolo registroTabelaSimbolo)
		{
			super();
			this.token = token;
			this.lexema = lexema;
			this.registroTabelaSimbolo = registroTabelaSimbolo;
		}

		public Token getToken()
		{
			return this.token;
		}

		public void setToken(Token token)
		{
			this.token = token;
		}

		public String getLexema()
		{
			return this.lexema;
		}

		public void setLexema(String lexema)
		{
			this.lexema = lexema;
		}

		public RegistroTabelaSimbolo getRegistroTabelaSimbolo()
		{
			return this.registroTabelaSimbolo;
		}

		public void setRegistroTabelaSimbolo(RegistroTabelaSimbolo registroTabelaSimbolo)
		{
			this.registroTabelaSimbolo = registroTabelaSimbolo;
		}

	}
}
