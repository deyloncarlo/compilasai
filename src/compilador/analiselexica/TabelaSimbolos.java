
package compilador.analiselexica;

public class TabelaSimbolos
{

	public static RegistroTabelaSimbolo raiz;

	/**
	 * M�todo que insere um novo registro na tabela de s�mbolo
	 * 
	 * @param p_token
	 * @param p_lexema
	 * @return
	 */
	public static RegistroTabelaSimbolo insereNovoRegistro(Token p_token, String p_lexema)
	{
		RegistroTabelaSimbolo v_novoRegistro = new RegistroTabelaSimbolo(p_token, p_lexema);

		if (raiz == null)
		{
			raiz = v_novoRegistro;
		}
		else
		{
			RegistroTabelaSimbolo v_ultimoRegistro = obterUltimo();
			v_ultimoRegistro.setProximo(v_novoRegistro);
		}

		return v_novoRegistro;
	}

	/**
	 * M�todo que obt�m o �ltimo elemento da lista
	 * 
	 * @return
	 */
	public static RegistroTabelaSimbolo obterUltimo()
	{
		if (raiz.getProximo() != null)
		{
			RegistroTabelaSimbolo v_proximoRegistro = raiz.getProximo();
			while (v_proximoRegistro.getProximo() != null)
			{
				v_proximoRegistro = v_proximoRegistro.getProximo();
			}
			return v_proximoRegistro;
		}
		return raiz;
	}

	/**
	 * M�todo que obt�m um registor da tabela de s�mbolo pelo seu lexema
	 * 
	 * @param p_lexema
	 * @return
	 */
	public static RegistroTabelaSimbolo pesquisarRegistro(String p_lexema)
	{
		Boolean v_encontrou = false;
		if (raiz != null)
		{
			if (verificaSeRegistoPossuiLexema(p_lexema, raiz) != null && !verificaSeRegistoPossuiLexema(p_lexema, raiz))
			{
				RegistroTabelaSimbolo v_proximoRegistro = raiz.getProximo();
				v_encontrou = verificaSeRegistoPossuiLexema(p_lexema, v_proximoRegistro);

				while (v_encontrou != null && !v_encontrou)
				{
					v_proximoRegistro = v_proximoRegistro.getProximo();
					v_encontrou = verificaSeRegistoPossuiLexema(p_lexema, v_proximoRegistro);
				}

				if (v_encontrou != null && v_encontrou)
				{
					return v_proximoRegistro;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return raiz;
			}

		}
		return null;

	}

	/**
	 * M�todo que exibe os registros existentes
	 */
	public static void exibeRegistros()
	{
		if (raiz == null)
		{
			System.out.println("nenhum registro encontrado");
		}
		else
		{
			RegistroTabelaSimbolo aux = raiz.getProximo();
			System.out.println(raiz.getToken().toString() + " \"" + raiz.getLexema() + "\"");
			System.out.println(aux.getToken().toString() + " \"" + aux.getLexema() + " \"");
			while (aux.getProximo() != null)
			{
				System.out
						.println(aux.getProximo().getToken().toString() + " \"" + aux.getProximo().getLexema() + "\"");
				aux = aux.getProximo();
			}
		}

	}

	/**
	 * M�todo que veifica se um registro da tabela de s�mbolos possui um
	 * determinado lexema
	 * 
	 * @param p_lexema
	 * @param p_registro
	 * @return
	 */
	public static Boolean verificaSeRegistoPossuiLexema(String p_lexema, RegistroTabelaSimbolo p_registro)
	{
		if (p_registro == null)
		{
			return null;
		}
		return p_registro.getLexema().equals(p_lexema);
	}

}
