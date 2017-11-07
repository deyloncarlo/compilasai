
package compilador.analiselexica;

public enum Token
{
	FINAL("final", 1),

	INT("int", 2),

	BYTE("byte", 3),

	BOOLEAN("boolean", 4),

	STRING("string", 5),

	WHILE("while", 6),

	IF("if", 7),

	ELSE("else", 8),

	AND("and", 9),

	OR("or", 10),

	NOT("not", 11),

	IGUAL("=", 12),

	IGUAL_IGUAL("==", 13),

	ABRE_PARENTESES("(", 14),

	FECHA_PARENTESES(")", 15),

	MAIOR(">", 16),

	MENOR("<", 17),

	DIFERENTE("!=", 18),

	MAIOR_IGUAL(">=", 19),

	MENOR_IGUAL("<=", 20),

	VIRGULA(",", 21),

	ADICAO("+", 22),

	SUBTRACAO("-", 23),

	ASTERISCO("*", 24),

	BARRA_DIREITA("/", 25),

	BARRA_ESQUERDA("\\", 26),

	PONTO_VIRGULA(";", 27),

	READLN("readln", 28),

	WRITE("write", 29),

	WRITELN("writeln", 30),

	TRUE("true", 31),

	FALSE("false", 32),

	IDENTIFICADOR("", 33),

	CONSTANTE("", 34),

	ABRE_CHAVES("{", 35),

	FECHA_CHAVES("}", 36);

	private String lexema;

	private Byte valorByte;

	Token(String p_lexema, int p_valorByte)
	{
		setLexema(p_lexema);
		setValorByte((byte) p_valorByte);
	}

	public String getLexema()
	{
		return this.lexema;
	}

	public void setLexema(String p_lexema)
	{
		this.lexema = p_lexema;
	}

	public Byte getValorByte()
	{
		return this.valorByte;
	}

	public void setValorByte(Byte valorByte)
	{
		this.valorByte = valorByte;
	}
}
