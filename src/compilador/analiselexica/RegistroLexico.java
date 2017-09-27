package compilador.analiselexica;

/**
 * Representa um registro lido no código-fonte
 * 
 * @author Deylon
 *
 */
public class RegistroLexico
{
	private Token token;
	private String lexema;
	private RegistroTabelaSimbolo registroTabelaSimbolo;
	private Tipo tipo;

	public RegistroLexico()
	{
	}

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

	public Tipo getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Tipo tipo)
	{
		this.tipo = tipo;
	}
}
