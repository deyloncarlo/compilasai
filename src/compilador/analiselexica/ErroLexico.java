package compilador.analiselexica;

public class ErroLexico extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private String mensagemErro;

	public ErroLexico(String p_lexema, int p_numeroLinha)
	{
		this.mensagemErro = p_numeroLinha + ": " + p_lexema;
	}

	@Override
	public String getMessage()
	{
		return this.mensagemErro;
	}
}
