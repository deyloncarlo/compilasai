
package compilador.analiselexica;

public enum Token
{
	FINAL("final"),

	INT("int"),

	BYTE("byte"),

	BOOLEAN("boolean"),

	STRING("string"),

	WHILE("while"),

	IF("if"),

	ELSE("else"),

	AND("and"),

	OR("or"),

	NOT("not"),

	IGUAL("="),

	IGUAL_IGUAL("=="),

	ABRE_PARENTESES("("),

	FECHA_PARENTESES(")"),

	MAIOR(">"),

	MENOR("<"),

	DIFERENTE("!="),

	MAIOR_IGUAL(">="),

	MENOR_IGUAL("<="),

	VIRGULA(","),

	ADICAO("+"),

	SUBTRACAO("-"),

	ASTERISCO("*"),

	BARRA("/"),

	PONTO_VIRGULA(";"),

	READLN("readln"),

	WRITE("write"),

	WRITELN("writeln"),

	TRUE("true"),

	FALSE("false"),

	IDENTIFICADOR("");

	private String lexema;

	Token(String p_lexema)
	{
		setLexema(p_lexema);
	}

	public String getLexema()
	{
		return this.lexema;
	}

	public void setLexema(String p_lexema)
	{
		this.lexema = p_lexema;
	}
}
