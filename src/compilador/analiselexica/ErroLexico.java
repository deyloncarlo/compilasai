package compilador.analiselexica;

public class ErroLexico extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private String mensagemErro;

	public ErroLexico(String p_mensagem)
	{
		this.mensagemErro = p_mensagem;
	}

	@Override
	public String getMessage()
	{
		return this.mensagemErro;
	}
}
