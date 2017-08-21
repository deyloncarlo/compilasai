
package compilador.analiselexica;

public class TabelaSimbolos
{

	public static RegistroTabelaSimbolo raiz;

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
	 * Método que obtém o último elemento da lista
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

	public RegistroTabelaSimbolo pesquisarRegistro(String p_lexema)
	{
		boolean v_encontrou = false;
		if (raiz != null)
		{
			if (!verificaSeRegistoPossuiLexema(p_lexema, raiz))
			{
				RegistroTabelaSimbolo v_proximoRegistro = raiz.getProximo();
				v_encontrou = verificaSeRegistoPossuiLexema(p_lexema, v_proximoRegistro);

				while (!v_encontrou)
				{
					v_proximoRegistro = v_proximoRegistro.getProximo();
					v_encontrou = verificaSeRegistoPossuiLexema(p_lexema, v_proximoRegistro);
				}

				if (v_encontrou)
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
				return this.raiz;
			}

		}
		return null;

	}

	public static void exibeRegistros()
	{
		if (raiz == null)
		{
			System.out.println("nenhum registro encontrado");
		}
		else
		{
			RegistroTabelaSimbolo aux = raiz.getProximo();
			System.out.println(raiz.getToken().toString() + raiz.getLexema());
			System.out.println(aux.getToken().toString() + aux.getLexema());
			while (aux.getProximo() != null)
			{
				System.out.println(aux.getProximo().getToken().toString() + aux.getProximo().getLexema());
				aux = aux.getProximo();
			}
		}

	}

	public boolean verificaSeRegistoPossuiLexema(String p_lexema, RegistroTabelaSimbolo p_registro)
	{
		return p_registro.getLexema().equals(p_lexema);
	}

}
