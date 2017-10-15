package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.Token;

public class Gramatica
{

	/**
	 * M�todo que ir� chamar o m�todo casaToken do AnalisadorSintatico
	 * 
	 * @param p_token
	 * @throws IOException
	 */
	public void casaToken(Token p_token) throws IOException
	{
		AnalisadorSintatico.casaToken(p_token);
	}

	public void executarGramatica() throws IOException
	{
		DeclaracaoVariaveis v_declaraVariveis = new DeclaracaoVariaveis();
		DeclaracaoBlocos v_declaracaoBlocos = new DeclaracaoBlocos();
		while (!AnalisadorLexico.isFimArquivo()
				&& !AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ABRE_CHAVES))
		{
			v_declaraVariveis.PARTE_DECLARACAO_VARIAVEIS();
		}
		if (!AnalisadorLexico.isFimArquivo())
		{
			v_declaracaoBlocos.PARTE_DECLARACAO_BLOCO();
		}

	}

	/**
	 * Classe respons�vel por gerenciar a parte de declara��o de blocos
	 * 
	 * @author Deylon
	 *
	 */
	private class DeclaracaoBlocos
	{
		/**
		 * Parte respons�vel por identificar todo o escopo de blocos do c�digo
		 * 
		 * @throws IOException
		 */
		public void PARTE_DECLARACAO_BLOCO() throws IOException
		{
			casaToken(Token.ABRE_CHAVES);
			DECLARA_COMANDOS();
			casaToken(Token.FECHA_CHAVES);
		}

		public void DECLARA_COMANDOS() throws IOException
		{
			COMANDO_ATRIBUICAO();
		}

		public void COMANDO_ATRIBUICAO() throws IOException
		{
			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.IGUAL);

		}

		public void EXPRESSAO() throws IOException
		{
			EXPRESSAO_SIMPLES();
		}

		public void EXPRESSAO_SIMPLES() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
			{
				casaToken(Token.ADICAO);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
			{
				casaToken(Token.SUBTRACAO);
			}

			T();

			while (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.AND))
			{
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ASTERISCO))
				{
					casaToken(Token.ASTERISCO);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BARRA_DIREITA))
				{
					casaToken(Token.BARRA_DIREITA);
				}
				else
				{
					casaToken(Token.AND);
				}
				F();
			}

		}

		public void T() throws IOException
		{
			F();
			while (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ASTERISCO)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BARRA_DIREITA)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.AND))
			{
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ASTERISCO))
				{
					casaToken(Token.ASTERISCO);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BARRA_DIREITA))
				{
					casaToken(Token.BARRA_DIREITA);
				}
				else
				{
					casaToken(Token.AND);
				}
				F();
			}
		}

		public void F() throws IOException
		{
			switch (AnalisadorLexico.getRegistroLexico().getToken())
			{
				case CONSTANTE:
					casaToken(Token.CONSTANTE);
					break;

				case IDENTIFICADOR:
					casaToken(Token.IDENTIFICADOR);
					break;

				case NOT:
					casaToken(Token.NOT);
					F();
					break;

				case ABRE_PARENTESES:
					casaToken(Token.ABRE_PARENTESES);
					EXPRESSAO();
					casaToken(Token.FECHA_PARENTESES);
					break;
				default:
					break;
			}
		}

	}

	/**
	 * Classe respons�vel por gerenciar parte de declara��o de vari�veis
	 * 
	 * @author Deylon
	 *
	 */
	private class DeclaracaoVariaveis
	{
		/**
		 * Parte respons�vel por identificar todas as declara�oes de variaveis
		 * 
		 * @throws IOException
		 */
		public void PARTE_DECLARACAO_VARIAVEIS() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.FINAL))
			{
				DECLARACAO_FINAL();
			}
			else
			{
				DECLARACAO_VARIAVEIS();
			}
		}

		/**
		 * Identifica a declaracao de constantes (FINAL)
		 */
		private void DECLARACAO_FINAL() throws IOException
		{
			casaToken(Token.FINAL);
			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.IGUAL);

			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
			{
				casaToken(Token.ADICAO);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
			{
				casaToken(Token.SUBTRACAO);
			}
			casaToken(Token.CONSTANTE);
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Reconhece as declara�oes de variaveis
		 * 
		 * @throws IOException
		 */
		public void DECLARACAO_VARIAVEIS() throws IOException
		{
			TIPO();
			casaToken(Token.IDENTIFICADOR);
			FORMA_DECLARACAO();
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica como ser� feita a declaracao de veri�vel
		 * 
		 * @throws IOException
		 */
		private void FORMA_DECLARACAO() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
			{
				LISTA_ID_DECLARACAO();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IGUAL))
			{
				casaToken(Token.IGUAL);
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
				{
					casaToken(Token.ADICAO);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
				{
					casaToken(Token.SUBTRACAO);
				}

				casaToken(Token.CONSTANTE);
			}
		}

		/**
		 * Identifica declara��o de lista de vari�veis
		 * 
		 * @throws IOException
		 */
		private void LISTA_ID_DECLARACAO() throws IOException
		{
			casaToken(Token.VIRGULA);
			casaToken(Token.IDENTIFICADOR);
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
			{
				LISTA_ID_DECLARACAO();
			}
		}

		/**
		 * Identifica qual o tipo da variavel durante a declaracao
		 * 
		 * @throws IOException
		 */
		private void TIPO() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.INT))
			{
				casaToken(Token.INT);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.STRING))
			{
				casaToken(Token.STRING);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BOOLEAN))
			{
				casaToken(Token.BOOLEAN);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BYTE))
			{
				casaToken(Token.BYTE);
			}
		}
	}

}
