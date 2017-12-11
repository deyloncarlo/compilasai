
package compilador.analiselexica;

public class RegistroTabelaSimbolo
{

	private Token token;

	private String lexema;

	private RegistroTabelaSimbolo proximo;

	private Tipo tipo;

	private Classe classe;

	public RegistroTabelaSimbolo(Token p_token, String p_lexama)
	{
		setToken(p_token);
		setLexema(p_lexama);
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

	public RegistroTabelaSimbolo getProximo()
	{
		return this.proximo;
	}

	public void setProximo(RegistroTabelaSimbolo proximo)
	{
		this.proximo = proximo;
	}

	public Tipo getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Tipo tipo)
	{
		this.tipo = tipo;
	}

	public Classe getClasse()
	{
		return this.classe;
	}

	public void setClasse(Classe classe)
	{
		this.classe = classe;
	}

}
