package compilador.analisesintatica;

import java.io.IOException;

import compilador.analiselexica.AnalisadorLexico;
import compilador.analiselexica.Classe;
import compilador.analiselexica.ErroLexico;
import compilador.analiselexica.Mensagem;
import compilador.analiselexica.RegistroTabelaSimbolo;
import compilador.analiselexica.TabelaSimbolos;
import compilador.analiselexica.Tipo;
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

	/**
	 * M�todo que faz a chama do m�todo para iniciar a an�lise do c�digo fonte
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
			while (!AnalisadorLexico.getRegistroLexico().getToken().equals(Token.FECHA_CHAVES))
			{
				DECLARA_COMANDOS();
			}
			casaToken(Token.FECHA_CHAVES);
		}

		/**
		 * M�todo que identifica os comandos
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
		 * M�todo que identifica comando de escrita
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
		 * M�todo que identifica comando de escrita para mais de um express�o
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

			verificarIdentificadorNaoFoiDeclarado();

			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica um comando nulo (apenas um ponto e v�rgula)
		 * 
		 * @throws IOException
		 */
		private void COMANDO_NULO() throws IOException
		{
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica c�digo de IF
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
		 * M�todo que identifica o conte�do que vem dentro de um comando de
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
		 * Identifica o c�digo de um while
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
		 * M�todo que identifica o conte�do que vem dentro de um comando de
		 * repeti��o (While)
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
		 * Identifica o comando de atribui��o
		 * 
		 * @throws IOException
		 */
		public void COMANDO_ATRIBUICAO() throws IOException
		{

			verificarIdentificadorNaoFoiDeclarado();
			verificarClasseIdentificador();

			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.IGUAL);
			EXPRESSAO();
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica express�es
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
		 * M�todo que identifica express�es simples
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
		 * M�todo que incorpora a leitura de express�es
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
		 * M�todo que incorpora a leitura de express�es
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

				verificarIdentificadorNaoFoiDeclarado();

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

			verificarIdentificadorJaDeclarado();

			// Definindo a classe do identificador
			RegistroTabelaSimbolo v_registroTabelaSimbolo = obterRegistroTabelaSimbolo();
			definirClasseIdentificador(v_registroTabelaSimbolo, Classe.CONST);

			casaToken(Token.IDENTIFICADOR);
			casaToken(Token.IGUAL);

			boolean v_temSinal = false;
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
			{
				v_temSinal = true;
				casaToken(Token.ADICAO);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
			{
				v_temSinal = true;
				casaToken(Token.SUBTRACAO);
			}
			Tipo v_tipoConstante = AnalisadorLexico.getRegistroLexico().getTipo();
			v_tipoConstante = transformarTipoByteEmInt(v_temSinal, v_tipoConstante);
			if (v_tipoConstante != null)
			{
				v_registroTabelaSimbolo.setTipo(v_tipoConstante);
				AnalisadorLexico.getRegistroLexico().setTipo(v_tipoConstante);
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
			Tipo p_tipo = TIPO();

			// verificando se identificador j� foi declarado
			verificarIdentificadorJaDeclarado();

			// Definindo a classe do identificador
			RegistroTabelaSimbolo v_registroTabelaSimbolo = obterRegistroTabelaSimbolo();
			v_registroTabelaSimbolo.setTipo(p_tipo);
			AnalisadorLexico.getRegistroLexico().setTipo(p_tipo);
			definirClasseIdentificador(v_registroTabelaSimbolo, Classe.VAR);

			casaToken(Token.IDENTIFICADOR);
			FORMA_DECLARACAO(p_tipo);
			casaToken(Token.PONTO_VIRGULA);
		}

		/**
		 * Identifica como ser� feita a declaracao de veri�vel
		 * 
		 * @param p_tipo
		 * 
		 * @throws IOException
		 */
		private void FORMA_DECLARACAO(Tipo p_tipo) throws IOException
		{
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
			{
				LISTA_ID_DECLARACAO(p_tipo);
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.IGUAL))
			{
				casaToken(Token.IGUAL);
				boolean v_temSinal = false;
				if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.ADICAO))
				{
					v_temSinal = true;
					casaToken(Token.ADICAO);
				}
				else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.SUBTRACAO))
				{
					v_temSinal = true;
					casaToken(Token.SUBTRACAO);
				}

				Tipo v_tipoConstante = AnalisadorLexico.getRegistroLexico().getTipo();
				v_tipoConstante = transformarTipoByteEmInt(v_temSinal, v_tipoConstante);
				if (!p_tipo.equals(v_tipoConstante))
				{
					throw new Error("Tipo errado a�");
				}
				casaToken(Token.CONSTANTE);
			}
		}

		/**
		 * Identifica declara��o de lista de vari�veis
		 * 
		 * @param p_tipo
		 * 
		 * @throws IOException
		 */
		private void LISTA_ID_DECLARACAO(Tipo p_tipo) throws IOException
		{
			casaToken(Token.VIRGULA);

			// verificando se identificador j� foi declarado
			verificarIdentificadorJaDeclarado();

			// Definindo a classe do identificador
			RegistroTabelaSimbolo v_registroTabelaSimbolo = obterRegistroTabelaSimbolo();
			v_registroTabelaSimbolo.setTipo(p_tipo);
			AnalisadorLexico.getRegistroLexico().setTipo(p_tipo);
			definirClasseIdentificador(v_registroTabelaSimbolo, Classe.VAR);

			casaToken(Token.IDENTIFICADOR);
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.VIRGULA))
			{
				LISTA_ID_DECLARACAO(p_tipo);
			}
		}

		/**
		 * Identifica qual o tipo da variavel durante a declaracao
		 * 
		 * @return
		 * 
		 * @throws IOException
		 */
		private Tipo TIPO() throws IOException
		{
			Tipo p_tipo = null;
			if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.INT))
			{
				casaToken(Token.INT);
				p_tipo = Tipo.INT;
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.STRING))
			{
				casaToken(Token.STRING);
				p_tipo = Tipo.STRING;
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BOOLEAN))
			{
				casaToken(Token.BOOLEAN);
				p_tipo = Tipo.BOOLEAN;
			}
			else if (AnalisadorLexico.getRegistroLexico().getToken().equals(Token.BYTE))
			{
				casaToken(Token.BYTE);
				p_tipo = Tipo.BYTE;
			}
			return p_tipo;
		}
	}

	/**
	 * Fun��o que define a classe de um determinado identificador
	 * 
	 * @param Classe que ser� atribu�da a um determinado identificador
	 */
	public void definirClasseIdentificador(RegistroTabelaSimbolo p_registro, Classe p_classe)
	{
		AnalisadorLexico.getRegistroLexico().setClasse(p_classe);
		p_registro.setClasse(p_classe);

	}

	/**
	 * M�todo que verifica se um indentificador de classe CONST est� recebendo
	 * algum outro valor.
	 */
	public void verificarClasseIdentificador()
	{
		boolean v_classeConstante = obterRegistroTabelaSimbolo().getClasse() == Classe.CONST;

		if (v_classeConstante)
		{
			throw new ErroLexico(Mensagem.classeIdentificadorIncompativel(AnalisadorLexico.getNumeroLinhaArquivo(),
					AnalisadorLexico.getRegistroLexico().getLexema()));
		}

	}

	/**
	 * M�todo que verifica se o identificador j� foi declarado
	 */
	public void verificarIdentificadorNaoFoiDeclarado()
	{
		boolean v_naoFoiDeclarado = obterRegistroTabelaSimbolo().getTipo() == null;
		if (v_naoFoiDeclarado)
		{
			throw new ErroLexico(Mensagem.identificadorNaoDeclarado(AnalisadorLexico.getNumeroLinhaArquivo(),
					AnalisadorLexico.getRegistroLexico().getLexema()));
		}
	}

	/**
	 * M�todo que verifica se identificador j� foi declarado
	 */
	public void verificarIdentificadorJaDeclarado()
	{
		boolean v_foiDeclarado = obterRegistroTabelaSimbolo().getTipo() != null;
		if (v_foiDeclarado)
		{
			throw new ErroLexico(Mensagem.identificadorJaDeclarado(AnalisadorLexico.getNumeroLinhaArquivo(),
					AnalisadorLexico.getRegistroLexico().getLexema()));
		}

	}

	/**
	 * M�todo que transforma Tipo BYTE em INT.
	 * 
	 * @param p_podeTransformar
	 * 
	 * @param v_tipoConstante
	 * @return
	 */
	public Tipo transformarTipoByteEmInt(boolean p_podeTransformar, Tipo v_tipoConstante)
	{
		if (v_tipoConstante != null && v_tipoConstante.equals(Tipo.BYTE) && p_podeTransformar)
		{
			return Tipo.INT;
		}
		return v_tipoConstante;

	}

	/**
	 * M�todo que obt�m o registro da tabela de s�mbolo correspondente ao
	 * registro l�xico atual
	 * 
	 * @return Registro da tabela de s�mbolo correspondente ao registro l�xico
	 * atual
	 */
	public RegistroTabelaSimbolo obterRegistroTabelaSimbolo()
	{
		return TabelaSimbolos.pesquisarRegistro(AnalisadorLexico.getRegistroLexico().getLexema());
	}

}
