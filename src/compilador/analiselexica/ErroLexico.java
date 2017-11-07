package compilador.analiselexica;

public class ErroLexico extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private String mensagemErro;

	/**
	 * M�todo respons�vel por lan�ar a mensagem de erro
	 * 
	 * @param p_mensagem
	 */
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
