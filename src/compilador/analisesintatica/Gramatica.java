package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.ErroLexico;
import compilador.analiselexica.Mensagem;
import compilador.analiselexica.Token;

public class Gramatica
{

	/**
	 * Método que irá chamar o método casaToken do AnalisadorSintatico
	 * 
	 * @param p_token
	 * @throws IOException
	 */
	public void casaToken(Token p_token) throws IOException
	{
		AnalisadorSintatico.casaToken(p_token);
	}

	/**
	 * Método que faz a chama do método para iniciar a análise do código fonte
	 * 
	 * @throws IOException
	 */
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
	 * Classe responsável por gerenciar a parte de declaração de blocos
	 * 
	 * @author Deylon
	 *
	 */
	private class DeclaracaoBlocos
	{
		/**
		 * Parte responsável por identificar todo o escopo de blocos do código
		 * 
		 * @throws IOException
		 */
		public void PARTE_DECLARACAO_BLOCO() throws IOException
		{
			casaToken(Token.ABRE_CHAVES);
			while (!AnalisadorLexico.getRegistroLexico().getToken().equals(Token.FECHA_CHAVES))
			{
				DECLARA_COMANDOS();
			}
			casaToken(Token.FECHA_CHAVES);
		}

		/**
		 * Método que identifica os comandos
		 * 
		 * @throws IOException
		 */
		public void DECLARA_COMANDOS() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IDENTIFICADOR))
			{
				COMANDO_ATRIBUICAO();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.WHILE))
			{
				COMANDO_REPETICAO();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IF))
			{
				COMANDO_TESTE();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.PONTO_VIRGULA))
			{
				COMANDO_NULO();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.READLN))
			{
				COMANDO_LEITURA();
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.WRITE)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.WRITELN))
			{
				COMANDO_ESCRITA();
			}
			else
			{
				throw new ErroLexico(Mensagem.tokenNaoEsperado(AnalisadorLexico.getRegistroLexico().getLexema(),
						AnalisadorLexico.getNumeroLinhaArquivo()));
			}
		}

		/**
		 * Método que identifica comando de escrita
		 * 
		 * @throws IOException
		 */
		private void COMANDO_ESCRITA() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.WRITE))
			{
				casaToken(Token.WRITE);
			}
			else
			{
				casaToken(Token.WRITELN);
			}

			casaToken(Token.VIRGULA);

			COMANDO_ESCRITA_LISTA_EXPRESSAO();

		}

		/**
		 * Método que identifica comando de escrita para mais de um expressão
		 * 
		 * @throws IOException
		 */
		private void COMANDO_ESCRITA_LISTA_EXPRESSAO() throws IOException
		{
			EXPRESSAO();
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
			{
				casaToken(Token.VIRGULA);
				COMANDO_ESCRITA_LISTA_EXPRESSAO();
			}
		}

		/**
		 * Identificando o comando de leitura
		 * 
		 * @throws IOException
		 */
		private void COMANDO_LEITURA() throws IOException
		{
			casaToken(Token.READLN);
			casaToken(Token.VIRGULA);
			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica um comando nulo (apenas um ponto e vírgula)
		 * 
		 * @throws IOException
		 */
		private void COMANDO_NULO() throws IOException
		{
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica código de IF
		 * 
		 * @throws IOException
		 */
		private void COMANDO_TESTE() throws IOException
		{
			casaToken(Token.IF);
			casaToken(Token.ABRE_PARENTESES);
			EXPRESSAO();
			casaToken(Token.FECHA_PARENTESES);
			CORPO_TESTE();
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ELSE))
			{
				casaToken(Token.ELSE);
				CORPO_TESTE();
			}

		}

		/**
		 * Método que identifica o conteúdo que vem dentro de um comando de
		 * teste (IF)
		 * 
		 * @throws IOException
		 */
		private void CORPO_TESTE() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ABRE_CHAVES))
			{
				PARTE_DECLARACAO_BLOCO();
			}
			else
			{
				DECLARA_COMANDOS();
			}
		}

		/**
		 * Identifica o código de um while
		 * 
		 * @throws IOException
		 */
		private void COMANDO_REPETICAO() throws IOException
		{
			casaToken(Token.WHILE);
			casaToken(Token.ABRE_PARENTESES);
			EXPRESSAO();
			casaToken(Token.FECHA_PARENTESES);
			COMANDO_REPETICAO_CORPO();

		}

		/**
		 * Método que identifica o conteúdo que vem dentro de um comando de
		 * repetição (While)
		 * 
		 * @throws IOException
		 */
		private void COMANDO_REPETICAO_CORPO() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ABRE_CHAVES))
			{
				PARTE_DECLARACAO_BLOCO();
			}
			else
			{
				DECLARA_COMANDOS();
			}
		}

		/**
		 * Identifica o comando de atribuição
		 * 
		 * @throws IOException
		 */
		public void COMANDO_ATRIBUICAO() throws IOException
		{
			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.IGUAL);
			EXPRESSAO();
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica expressões
		 * 
		 * @throws IOException
		 */
		public void EXPRESSAO() throws IOException
		{
			EXPRESSAO_SIMPLES();
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IGUAL_IGUAL)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.DIFERENTE)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MAIOR)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MENOR)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MAIOR_IGUAL)
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MENOR_IGUAL))
			{
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IGUAL_IGUAL))
				{
					casaToken(Token.IGUAL_IGUAL);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.DIFERENTE))
				{
					casaToken(Token.DIFERENTE);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MAIOR))
				{
					casaToken(Token.MAIOR);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MENOR))
				{
					casaToken(Token.MENOR);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.MAIOR_IGUAL))
				{
					casaToken(Token.MAIOR_IGUAL);
				}
				else
				{
					casaToken(Token.MENOR_IGUAL);
				}
				EXPRESSAO_SIMPLES();
			}
		}

		/**
		 * Método que identifica expressões simples
		 * 
		 * @throws IOException
		 */
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
					|| AnalisadorLexico.getRegistroLexico().getToken().equals(Token.OR))
			{
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
				{
					casaToken(Token.ADICAO);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
				{
					casaToken(Token.SUBTRACAO);
				}
				else
				{
					casaToken(Token.OR);
				}
				T();
			}

		}

		/**
		 * Método que incorpora a leitura de expressões
		 * 
		 * @throws IOException
		 */
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

		/**
		 * Método que incorpora a leitura de expressões
		 * 
		 * @throws IOException
		 */
		public void F() throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.CONSTANTE))
			{
				casaToken(Token.CONSTANTE);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IDENTIFICADOR))
			{
				casaToken(Token.IDENTIFICADOR);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.NOT))
			{
				casaToken(Token.NOT);
				F();
			}
			else
			{
				casaToken(Token.ABRE_PARENTESES);
				EXPRESSAO();
				casaToken(Token.FECHA_PARENTESES);
			}
		}

	}

	/**
	 * Classe responsável por gerenciar parte de declaração de variáveis
	 * 
	 * @author Deylon
	 *
	 */
	private class DeclaracaoVariaveis
	{
		/**
		 * Parte responsável por identificar todas as declaraçoes de variaveis
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
		 * Reconhece as declaraçoes de variaveis
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
		 * Identifica como será feita a declaracao de veriável
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
		 * Identifica declaração de lista de variáveis
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
