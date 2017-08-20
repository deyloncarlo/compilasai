
package compilador.analiselexica;

public class RegistroTabelaSimbolo
{

	private Token token;

	private String lexema;

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

}
